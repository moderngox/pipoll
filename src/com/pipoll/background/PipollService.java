package com.pipoll.background;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PipollService extends IntentService {
	private static final String TAG = "PipollService";

	// private static final int POLL_INTERVAL = 1000 * 60 * 5; // 5 minutes
	private static final int POLL_INTERVAL = 1000 * 5; // few seconds for testing
	public static final String PREF_IS_ALARM_ON = "isAlarmOn";

	public static final String ACTION_SHOW_NOTIFICATION = "com.bignerdranch.android.photogallery.SHOW_NOTIFICATION";

	public static final String PERM_PRIVATE = "com.bignerdranch.android.photogallery.PRIVATE";

	public PipollService() {
		super(TAG);
	}

	@Override
	public void onHandleIntent(Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		boolean isNetworkAvailable = cm.getBackgroundDataSetting()
				&& cm.getActiveNetworkInfo() != null;
		if (!isNetworkAvailable)
			return;

		// TODO : request server API for new Polls
		String msg = "PipollService onHandleIntent()";
		Log.e(TAG, msg);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}

	public static void setServiceAlarm(Context context, boolean isOn) {
		Intent i = new Intent(context, PipollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		if (isOn) {
			alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
					POLL_INTERVAL, pi);
		} else {
			alarmManager.cancel(pi);
			pi.cancel();
		}

		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(PipollService.PREF_IS_ALARM_ON, isOn).commit();
	}

	public static boolean isServiceAlarmOn(Context context) {
		Intent i = new Intent(context, PipollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i,
				PendingIntent.FLAG_NO_CREATE);
		return pi != null;
	}

	private static BroadcastReceiver sNotificationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent i) {
			Log.i(TAG, "received result: " + getResultCode());
			if (getResultCode() != Activity.RESULT_OK)
				// a foreground activity cancelled the broadcast
				return;

			int requestCode = i.getIntExtra("REQUEST_CODE", 0);
			Notification notification = (Notification) i.getParcelableExtra("NOTIFICATION");

			NotificationManager notificationManager = (NotificationManager) c
					.getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(requestCode, notification);
		}
	};

	void showBackgroundNotification(int requestCode, Notification notification) {
		Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
		i.putExtra("REQUEST_CODE", requestCode);
		i.putExtra("NOTIFICATION", notification);

		sendOrderedBroadcast(i, PERM_PRIVATE, sNotificationReceiver, null, Activity.RESULT_OK,
				null, null);
	}
}
