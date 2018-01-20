/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.bluetooth.client.pbap;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.SdpPseRecord;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

class BluetoothPbapSession implements Callback {
    private static final String TAG = "android.bluetooth.client.pbap.BluetoothPbapSession";

    /* local use only */
    private static final int SOCKET_CONNECTED = 1;
    private static final int SOCKET_CONNECT_FAILED = 2;

    /* to BluetoothPbapClient */
    public static final int REQUEST_COMPLETED = 3;
    public static final int REQUEST_FAILED = 4;
    public static final int SESSION_CONNECTING = 5;
    public static final int SESSION_CONNECTED = 6;
    public static final int SESSION_DISCONNECTED = 7;
    public static final int AUTH_REQUESTED = 8;
    public static final int AUTH_TIMEOUT = 9;

    public static final int ACTION_LISTING = 14;
    public static final int ACTION_VCARD = 15;
    public static final int ACTION_PHONEBOOK_SIZE = 16;
    private static final int L2CAP_INVALID_PSM = -1;

    private static final String PBAP_UUID =
            "0000112f-0000-1000-8000-00805f9b34fb";

    private final BluetoothAdapter mAdapter;
    private final BluetoothDevice mDevice;

    private final Handler mParentHandler;

    private final HandlerThread mHandlerThread;
    private final Handler mSessionHandler;

    private SocketConnectThread mConnectThread;
    private BluetoothPbapObexTransport mTransport;
    private SdpPseRecord mPse;

    private BluetoothPbapObexSession mObexSession;

    private BluetoothPbapRequest mPendingRequest = null;

    public BluetoothPbapSession(BluetoothDevice device, Handler handler) {

        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            throw new NullPointerException("No Bluetooth adapter in the system");
        }

        mDevice = device;
        mParentHandler = handler;
        mConnectThread = null;
        mTransport = null;
        mObexSession = null;

        mHandlerThread = new HandlerThread("PBAP session handler",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mSessionHandler = new Handler(mHandlerThread.getLooper(), this);
    }

    public BluetoothPbapSession(BluetoothDevice device, Handler handler, SdpPseRecord pse) {

        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            throw new NullPointerException("No Bluetooth adapter in the system");
        }

        mDevice = device;
        mParentHandler = handler;
        mConnectThread = null;
        mTransport = null;
        mObexSession = null;
        mPse = pse;

        mHandlerThread = new HandlerThread("PBAP session handler",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mSessionHandler = new Handler(mHandlerThread.getLooper(), this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "Handler: msg: " + msg.what);

        switch (msg.what) {
            case SOCKET_CONNECT_FAILED:
                mConnectThread = null;

                mParentHandler.obtainMessage(SESSION_DISCONNECTED).sendToTarget();

                if (mPendingRequest != null) {
                    mParentHandler.obtainMessage(REQUEST_FAILED, mPendingRequest).sendToTarget();
                    mPendingRequest = null;
                }
                break;

            case SOCKET_CONNECTED:
                mConnectThread = null;
                mTransport = (BluetoothPbapObexTransport) msg.obj;
                startObexSession();
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_FAILED:
                stopObexSession();

                mParentHandler.obtainMessage(SESSION_DISCONNECTED).sendToTarget();

                if (mPendingRequest != null) {
                    mParentHandler.obtainMessage(REQUEST_FAILED, mPendingRequest).sendToTarget();
                    mPendingRequest = null;
                }
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_CONNECTED:
                mParentHandler.obtainMessage(SESSION_CONNECTED).sendToTarget();

                if (mPendingRequest != null) {
                    mObexSession.schedule(mPendingRequest);
                    mPendingRequest = null;
                }
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_DISCONNECTED:
                mParentHandler.obtainMessage(SESSION_DISCONNECTED).sendToTarget();
                stopSocketConnect();
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_REQUEST_COMPLETED:
                /* send to parent, process there */
                mParentHandler.obtainMessage(REQUEST_COMPLETED, msg.obj).sendToTarget();
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_REQUEST_FAILED:
                /* send to parent, process there */
                mParentHandler.obtainMessage(REQUEST_FAILED, msg.obj).sendToTarget();
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_AUTHENTICATION_REQUEST:
                /* send to parent, process there */
                mParentHandler.obtainMessage(AUTH_REQUESTED).sendToTarget();

                mSessionHandler
                        .sendMessageDelayed(
                                mSessionHandler
                                        .obtainMessage(BluetoothPbapObexSession.OBEX_SESSION_AUTHENTICATION_TIMEOUT),
                                30000);
                break;

            case BluetoothPbapObexSession.OBEX_SESSION_AUTHENTICATION_TIMEOUT:
                /* stop authentication */
                setAuthResponse(null);

                mParentHandler.obtainMessage(AUTH_TIMEOUT).sendToTarget();
                break;

            default:
                return false;
        }

        return true;
    }

    public void start() {
        Log.d(TAG, "start");

        startSocketConnect();
    }

    public void stop() {
        Log.d(TAG, "Stop");

        stopObexSession();
    }

    public void abort() {
        Log.d(TAG, "abort");

        /* fail pending request immediately */
        if (mPendingRequest != null) {
            mParentHandler.obtainMessage(REQUEST_FAILED, mPendingRequest).sendToTarget();
            mPendingRequest = null;
        }

        if (mObexSession != null) {
            mObexSession.abort();
        }
    }

    public void setPseRec(SdpPseRecord pse) {
        Log.d(TAG,"setPseRec "+ pse.getL2capPsm());
        mPse = pse;
    }
    public boolean makeRequest(BluetoothPbapRequest request) {
        Log.v(TAG, "makeRequest: " + request.getClass().getSimpleName());

        if (mPendingRequest != null) {
            Log.w(TAG, "makeRequest: request already queued, exiting");
            return false;
        }

        if (mObexSession == null) {
            mPendingRequest = request;

            /*
             * since there is no pending request and no session it's safe to
             * assume that RFCOMM does not exist either and we should start
             * connecting it
             */

            startSocketConnect();

            return true;
        }

        return mObexSession.schedule(request);
    }

    public boolean setAuthResponse(String key) {
        Log.d(TAG, "setAuthResponse key=" + key);

        mSessionHandler
                .removeMessages(BluetoothPbapObexSession.OBEX_SESSION_AUTHENTICATION_TIMEOUT);

        /* does not make sense to set auth response when OBEX session is down */
        if (mObexSession == null) {
            return false;
        }

        return mObexSession.setAuthReply(key);
    }

    private void startSocketConnect() {
        Log.d(TAG, "startSocketConnect");

        if (mConnectThread == null && mObexSession == null) {
            mParentHandler.obtainMessage(SESSION_CONNECTING).sendToTarget();

            mConnectThread = new SocketConnectThread();
            mConnectThread.start();
        }

        /*
         * don't care if mConnectThread is not null - it means RFCOMM is being
         * connected anyway
         */
    }

    private void stopSocketConnect() {
        Log.d(TAG, "stopSocketConnect");

        if (mConnectThread != null) {
            try {
                mConnectThread.join();
            } catch (InterruptedException e) {
            }

            mConnectThread = null;
        }

        if (mTransport != null) {
            try {
                mTransport.close();
            } catch (IOException e) {
            }

            mTransport = null;
        }
    }

    private void startObexSession() {
        Log.d(TAG, "startObexSession");

        mObexSession = new BluetoothPbapObexSession(mTransport, mPse);
        mObexSession.start(mSessionHandler);
    }

    private void stopObexSession() {
        Log.d(TAG, "stopObexSession");

        if (mObexSession != null) {
            mObexSession.stop();
            mObexSession = null;
        }
    }

    private class SocketConnectThread extends Thread {
        private static final String TAG = "SocketConnectThread";

        private BluetoothSocket mSocket;

        public SocketConnectThread() {
            super("SocketConnectThread");
        }

        @Override
        public void run() {
            if (mAdapter.isDiscovering()) {
                mAdapter.cancelDiscovery();
            }

            int btPseTransportType;
            if (connectL2capSocket())
                btPseTransportType = BluetoothSocket.TYPE_L2CAP;
            else if (connectRfcommSocket())
                btPseTransportType = BluetoothSocket.TYPE_RFCOMM;
            else
            {
                closeSocket();
                mSessionHandler.obtainMessage(SOCKET_CONNECT_FAILED).sendToTarget();
                return;
            }

            BluetoothPbapObexTransport transport;
            transport = new BluetoothPbapObexTransport(mSocket,btPseTransportType);
            Log.e(TAG, "posting SOCKET_CONNECTED");
            mSessionHandler.obtainMessage(SOCKET_CONNECTED, transport).sendToTarget();
        }

        private boolean connectL2capSocket() {
            try {
                /* Use BluetoothSocket to connect */
                Log.v(TAG,"connectL2capSocket: PSM: " + mPse.getL2capPsm());
                if (mPse.getL2capPsm() != L2CAP_INVALID_PSM) {
                    mSocket = mDevice.createL2capSocket(mPse.getL2capPsm());
                    mSocket.connect();
                    Log.d(TAG,"l2cap socket connected ");
                } else {
                    return false;
                }

            } catch (IOException e) {
                Log.e(TAG, "Error while connecting L2cap socket", e);
                return false;
            }
            return true;
        }

        private boolean connectRfcommSocket() {
            try {
                /* Use BluetoothSocket to connect */
                Log.v(TAG,"connectRfcommSocket: channel: " + mPse.getRfcommChannelNumber());
                mSocket = mDevice.createRfcommSocket(mPse.getRfcommChannelNumber());
                mSocket.connect();
                Log.v(TAG,"rfcomm socket connected ");
            } catch (IOException e) {
                Log.e(TAG, "Error while connecting rfcomm socket", e);
                closeSocket();
                return false;
            }
            return true;
        }

        private void closeSocket() {
            try {
                if (mSocket != null) {
                    mSocket.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error when closing socket", e);
            }
        }
    }
}
