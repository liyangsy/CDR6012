package android.hardware;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ServiceManager;
import java.io.IOException;
import android.os.IBinder;
import java.util.Objects;
import java.util.List;

/**
 * the following class in android.hardware package to control LED device
 * 
 */
public class LedManager implements Parcelable {
	private static final String TAG = "LedManager";
	// The ID number of the upper LED.
	public static final int LIGHT_ID_UPPER = 1;
	// The ID number of the bottom LED.
	public static final int LIGHT_ID_BOTTOM = 2;

	public static final int COLOR_GREEN = 1;
	public static final int COLOR_RED = 2;
	public static final int COLOR_ORANGE = 3;

	private ILedManager mService;

	/** @hide */
	public LedManager(ILedManager service) {
		mService = service;
	}

	/** @hide */
	LedManager(Parcel source) {
		ILedManager.Stub.asInterface(source.readStrongBinder());
	}

	/** @hide */
	public LedManager() {
		mService = ILedManager.Stub.asInterface(ServiceManager
				.getService(Context.LED_SERVICE));
	}

	/**
	 * It turns on the LED of the specified LED ID with the specified color .
	 * @param led the ID of the LED which is be turned on.
	 * @param color the selected color of the LED which is be turned on..
	 * @return true if success else reture false.
	 */
	public boolean setLedOn(int led, int color) {
		Log.v(TAG, "setLedOn : ledID =" + led + ", color = " + color);
		try {
			if (led > LIGHT_ID_BOTTOM || led < LIGHT_ID_UPPER) {
				Log.e(TAG, "Light ID is not correct");
				return false;
			} else if (color > COLOR_ORANGE || color < COLOR_GREEN) {
				Log.e(TAG, "Color ID is not correct");
				return false;
			} else if (led == LIGHT_ID_BOTTOM && color != COLOR_RED) {
				Log.e(TAG, "LIGHT_ID_BOTTOM only has  red light");
				return false;
			}
			mService.setLedOnService(led, color);
			return true;
		} catch (RemoteException ex) {
			Log.e(TAG, "setLedOn error =" + ex);
			return false; 
		}
	}

	/**
	 * It turns off the LED of the specified LED ID.
	 * @param led the ID of the LED which is be turned off.
	 * @return true if success else reture false.
	 */
	public boolean setLedOff(int led) {
		Log.v(TAG, " setLedOff : ledID =" + led);
		try {
			if (led > LIGHT_ID_BOTTOM || led < LIGHT_ID_UPPER) {
				Log.e(TAG, "Light ID is not correct");
				return false;
			}
			mService.setLedOffService(led);
			return true;
		} catch (RemoteException ex) {
			Log.e(TAG, "setLedOff error =" + ex);
			return false;
		}
	}

	/**
	 * It flashes the LED of the specified LED ID with the specified color .
	 * @param led the ID of the LED which is be flashed.
	 * @param color the selected color of the LED which is be flashed.
	 * @param onMS the interval of LED on. Unit is milliseconds.
	 * @param offMS the interval of LED off. Unit is milliseconds.
	 * @return true if success else reture false.
	 */
	public boolean setFlashing(int led, int color, int onMS, int offMS) {
		Log.v(TAG, "setFlashing : ledID =" + led + ", color = " + color
				+ ", onMS = " + onMS + ", offMS = " + offMS);
		try {
			if (led > LIGHT_ID_BOTTOM || led < LIGHT_ID_UPPER) {
				Log.e(TAG, "Light ID is not correct");
				return false;
			} else if (color > COLOR_ORANGE || color < COLOR_GREEN) {
				Log.e(TAG, "Color ID is not correct");
				return false;
			} else if (led == LIGHT_ID_BOTTOM && color != COLOR_RED) {
				Log.e(TAG, "LIGHT_ID_BOTTOM only has  red light");
				return false;
			}
			mService.setFlashingService(led, color, onMS, offMS);
			return true;
		} catch (RemoteException ex) {
			Log.e(TAG, "setFlashing error =" + ex);
			return false;
		}
	}

	/** @hide */
	public static final Parcelable.Creator<LedManager> CREATOR = new Parcelable.Creator<LedManager>() {
		/** @hide */
		public LedManager createFromParcel(Parcel source) {
			return new LedManager(source);
		}

		/** @hide */
		public LedManager[] newArray(int size) {
			return new LedManager[size];
		}
	};

	/** @hide */
	public void writeToParcel(Parcel dest, int flags) {
	}

	/** @hide */
	public int describeContents() {
		return 0;
	}
}
