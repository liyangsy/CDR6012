package android.hardware;

/** {@hide} */
interface ILedManager
{ 
	boolean setLedOnService(int led, int color);
	boolean setLedOffService(int led);
	boolean setFlashingService(int led, int color, int onMS, int offMS);
}
