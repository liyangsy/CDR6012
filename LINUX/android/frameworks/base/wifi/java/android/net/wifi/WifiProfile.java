package android.net.wifi;

import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.nio.charset.Charset;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
/**
 * Parse Wifi profiles from an XML file on SDCard
 * <wifi>
 *   <ssid></ssid>
 *   <security></security>
 *   <password></password>
 * </wifi>
 */
public class WifiProfile {

    public String ssid = null;
    public int security = 0;
    public String password = null;
    public int failMsg = 0;

    public static final int OPEN = 0;
    public static final int WEP = 1;
    public static final int WPA = 2;

    private static String PROFILE_NAME = "wifi.xml";
    private final String TAG = "WifiProfileParser";
    private boolean DEBUG = true;


    public WifiProfile() {}

    public WifiProfile(Context mContext) {
        this(mContext, PROFILE_NAME);
    }

    public WifiProfile(Context mContext, String profileName) {
        InputStream in = null;
        String SDroot = getExStoragePath(mContext, true);

        if (SDroot==null) {
            failMsg = -2;
            return;
        }

        if (DEBUG) Log.d(TAG, "Try to get RealSD patch:" + SDroot + " File:" + profileName);
        File profileFile = new File(SDroot, profileName);

        try {
             in = new FileInputStream(profileFile);
             wifiProfileParse(in);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "FileNotExist:" + SDroot +"/"+ profileName);
            failMsg = -2;
        } finally {
            if (DEBUG) Log.d(TAG, "finally");
            try {
                if(in != null) in.close();
            } catch(IOException e) {
                failMsg = -2;
                Log.v(TAG,"Failed to close streams");
            }
        }
        if (failMsg == 0 && ssid == null)
            failMsg = -3;

    }

    private String getExStoragePath(Context mContext, boolean is_removale) {
      StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void wifiProfileParse(InputStream in) {
        try {
            SaxForXmlHandler mHandler = new SaxForXmlHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(in, mHandler);
        } catch (SAXException e) {
            Log.e(TAG, "SAXException Parse Wifi profile exception: " + e.toString());
            failMsg = -4;
        } catch (IOException e) {
            Log.e(TAG, "IOException Parse Wifi profile exception: " + e.toString());
            failMsg = -2;
        } catch (ParserConfigurationException e) {
            Log.e(TAG, "ParserConfigurationException Parse Wifi profile exception: " + e.toString());
            failMsg = -4;
        } finally {
            //
        }
    }

    class SaxForXmlHandler extends DefaultHandler {
        boolean tSsid;
        boolean tSecurity;
        boolean tPassword;

        @Override
        public void startElement(String uri, String localName, String tagName,
                Attributes attributes) throws SAXException {
            if (DEBUG) Log.d(TAG, "startElement");
            if (tagName.equalsIgnoreCase("ssid")) {
                tSsid = true;
            }
            if (tagName.equalsIgnoreCase("security")) {
                tSecurity = true;
            }
            if (tagName.equalsIgnoreCase("password")) {
                tPassword = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String tagName) throws SAXException {
            if (tagName.equalsIgnoreCase("wifi")) {
                if (DEBUG) Log.d(TAG, "endElement");
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            String strValue = new String(ch, start, length);
            if (tSsid) {
                if (DEBUG) Log.d(TAG, "ssid:" + strValue);
                if (strValue.equals("\n") || strValue.equals("") || strValue.length() == 0)
                    throw new SAXException("null or not set ssid");

                //FIx bug 32byte of SSID length. 03272017 Scottj @ Askey
                if (DEBUG) Log.d(TAG, "UTF8 length:" + Charset.forName("UTF-8").encode(strValue).limit());
                if (Charset.forName("UTF-8").encode(strValue).limit() > 32)
                    throw new SAXException("Over SSID Elements");

                ssid = strValue;
                tSsid = false;
            }
            if (tSecurity) {
                if (DEBUG) Log.d(TAG, "securityType:" + strValue);
                int t = getSecurityType(strValue);
                if (t < 0) {
                    throw new SAXException("not a valid type");
                } else {
                    security = t;
                }
                tSecurity = false;
            }
            if (tPassword) {
                if (DEBUG) Log.d(TAG, "password:" + strValue);

                if (security != OPEN &&(strValue.equals("\n") || strValue.equals("") || strValue.length() == 0))
                   throw new SAXException("null or not set passwd");

                if (security == WPA && strValue.length() < 8)
                   throw new SAXException("WPA passwd at least 8 char");

                password = strValue;
                tPassword = false;
            }
        }

        private int getSecurityType(String type) {
            if (type.equals("\n") || type.equals("") || type == null || type.length() == 0 || type.equalsIgnoreCase("OPEN")) {
                return OPEN;
            } else if (type.equalsIgnoreCase("WEP")) {
                return WEP;
            } else if (type.equalsIgnoreCase("WPA") || type.equalsIgnoreCase("WPA2")) {
                return WPA;
            } else {
                Log.v(TAG, "Invalid type: " + type + " ");
                return -1;
            }
        }
    }
}
