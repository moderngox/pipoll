package com.pipoll.background;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Bulbi
 * 
 *         Process the notification.
 *         Get the Notification from the Broadcast and display it.
 */
public class NotificationReceiver extends BroadcastReceiver {
	private static final String TAG = "NotificationReceiver";

	@Override
	public void onReceive(Context c, Intent i) {
		Log.e(TAG, TAG + " received result: " + getResultCode());
	
		// Don't need these anymore since we are using abortBroadcast() in VisibleFragment
		if (getResultCode() != Activity.RESULT_OK)
			// a foreground activity cancelled the broadcast
			return;

		int requestCode = i.getIntExtra(PipollService.EXTRA_REQUEST_CODE, 0);
		Notification notification = (Notification) i.getParcelableExtra(PipollService.EXTRA_NOTIFICATION);

		NotificationManager notificationManager = (NotificationManager) c
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(requestCode, notification);
	}
}
