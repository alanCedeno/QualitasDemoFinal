package com.planetmedia.qualitas.pushNotification;

import static com.planetmedia.qualitas.util.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.planetmedia.qualitas.util.CommonUtilities.EXTRA_MESSAGE;
import static com.planetmedia.qualitas.util.CommonUtilities.SENDER_ID;
import static com.planetmedia.qualitas.util.CommonUtilities.TAG;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.planetmedia.qualitas.util.CommonUtilities;
import com.planetmedia.qualitas.util.ServerUtilities;
import com.planetmedia.qualitas.util.WakeLocker;

public class PushNotificationRegister {

	// Asyntask
	static AsyncTask<Void, Void, Void> mRegisterTask;

	public static void registra(final Context contexto) {
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(contexto);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(contexto);

		contexto.registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(contexto);

		Log.i(TAG, "regId = (" + regId + ")");

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(contexto, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(contexto)) {
				// Skips registration.
//				Toast.makeText(contexto, "Already registered with GCM",
//						Toast.LENGTH_LONG).show();
			}
//			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						
						String macAddress = CommonUtilities.obtenMacAddress(contexto);
						
						ServerUtilities.register(contexto, macAddress, regId);
						
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
//			}
		}
	}

	public static void unregister(Context contexto) {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			contexto.unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(contexto);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
	}

	/**
	 * Receiving push messages
	 * */
	private final static BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(context);

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			// lblMessage.append(newMessage + "\n");
			// Log.i("PUSH", newMessage);
//			Toast.makeText(context, "New Message: " + newMessage,
//					Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

}
