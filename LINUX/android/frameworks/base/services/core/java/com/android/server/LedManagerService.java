package com.android.server;

import android.hardware.ILedManager;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.lights.Light;
import com.android.server.lights.LightsManager;

class LedManagerService   extends SystemService {
	private static final String TAG = "LedManagerService";
	private static final int Green_Light = 0x0000ff00;
	private static final int Red_Light = 0x00ff0000;
	private static final int Orange_Light = 0x00ffff00;

	// The ID number of the upper LED.
	private static final int LIGHT_ID_UPPER = 1;
	// The ID number of the bottom LED.
	private static final int LIGHT_ID_BOTTOM = 2;

	private static final int COLOR_GREEN = 1;
	private static final int COLOR_RED = 2;
	private static final int COLOR_ORANGE = 3;

	private Light mLight;
	private Context mContext;

	public LedManagerService(Context context) {
		super(context);
	}

	public void onStart() {
		publishBinderService(Context.LED_SERVICE, mService);
	}

	private final IBinder mService = new ILedManager.Stub() {

		public boolean setLedOnService(int led, int color) {
			final LightsManager lights = getLocalService(LightsManager.class);
			switch (led) {
			case LIGHT_ID_UPPER:
				mLight = lights.getLight(LightsManager.LIGHT_ID_NOTIFICATIONS);
				break;
			case LIGHT_ID_BOTTOM:
				mLight = lights.getLight(LightsManager.LIGHT_ID_PHONECALL);
				break;
			default:
				break;
			}
			mLight.turnOff();
			switch (color) {
			case COLOR_GREEN:
				mLight.setColor(Green_Light);
				break;
			case COLOR_RED:
				mLight.setColor(Red_Light);
				break;
			case COLOR_ORANGE:
				mLight.setColor(Orange_Light);
				break;
			default:
				break;
			}
			return true;
		}

		public boolean setLedOffService(int led) {
			final LightsManager lights = getLocalService(LightsManager.class);
			switch (led) {
			case LIGHT_ID_UPPER:
				mLight = lights.getLight(LightsManager.LIGHT_ID_NOTIFICATIONS);
				break;
			case LIGHT_ID_BOTTOM:
				mLight = lights.getLight(LightsManager.LIGHT_ID_PHONECALL);
				break;
			default:
				break;
			}
			mLight.turnOff();
			return true;
		}

		public boolean setFlashingService(int led, int color, int onMS,
				int offMS) {
			final LightsManager lights = getLocalService(LightsManager.class);

			int Light_Color = 0;
			switch (led) {
			case LIGHT_ID_UPPER:
				mLight = lights.getLight(LightsManager.LIGHT_ID_NOTIFICATIONS);
				break;
			case LIGHT_ID_BOTTOM:
				mLight = lights.getLight(LightsManager.LIGHT_ID_PHONECALL);
				break;
			default:
				break;
			}

			switch (color) {
			case COLOR_GREEN:
				Light_Color = Green_Light;
				break;
			case COLOR_RED:
				Light_Color = Red_Light;
				break;
			case COLOR_ORANGE:
				Light_Color = Orange_Light;
				break;
			default:
				break;
			}
			mLight.setFlashing(Light_Color, Light.LIGHT_FLASH_TIMED, onMS,
					offMS);
			return true;
		}
	};
}
