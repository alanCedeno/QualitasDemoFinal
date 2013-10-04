package com.planetmedia.qualitas.util;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public final class CommonUtilities {
	
	// give your server registration url here
    //public static final String SERVER_URL = "http://192.168.1.101/gcm_server_php/register.php";
	public static final String SERVER_URL = "http://hermes.planetmedia.es/device_token_push.php";
//	public static final String SERVER_URL = "http://localhost/gcm_server_php/device_token_push.php";


    // Google project id
    public static final String SENDER_ID = "699404857819"; 

    /**
     * Tag used on log messages.
     */
    public static final String TAG = "Push Notification GCM";

    public static final String DISPLAY_MESSAGE_ACTION =
            //"com.planetmedia.sanborns.DISPLAY_MESSAGE";
    		"com.planetmedia.qualitas.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    
    public static String obtenMacAddress(Context contexto){
    	WifiManager manager = (WifiManager) contexto.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String macAddress = info.getMacAddress();
		return macAddress;
    }
}