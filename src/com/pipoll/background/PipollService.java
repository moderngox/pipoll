package com.pipoll.background;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pipoll.R;
import com.pipoll.activity.TabActivity;

public class PipollService extends IntentService {
	private static final String TAG = "PipollService";

	// private static final int POLL_INTERVAL = 1000 * 60 * 60 * 24; // once a day
	private static final int POLL_INTERVAL = 1000 * 10; // 15 seconds for testing
	public static final String PREF_IS_ALARM_ON = "isAlarmOn";

	public static final String ACTION_SHOW_NOTIFICATION = "com.pipoll.SHOW_NOTIFICATION";

	public static final String PERM_PRIVATE = "com.pipoll.PRIVATE";

	public static final String EXTRA_NOTIFICATION = "NOTIFICATION";
	public static final String EXTRA_REQUEST_CODE = "REQUEST_CODE";

	public PipollService() {
		super(TAG);
	}

	@Override
	public void onHandleIntent(Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
		if (!isNetworkAvailable)
			return;

		// TODO : request server API for new Polls
		String msg = "PipollService onHandleIntent()";
		Log.e(TAG, msg);
		// On peut pas toaster normalement dans IntentService
//		Handler mHandler = new Handler(getMainLooper());
//	    mHandler.post(new Runnable() {
//	        @Override
//	        public void run() {
//	            Toast.makeText(getApplicationContext(), "Toast from IntentService", Toast.LENGTH_SHORT).show();
//	        }
//	    });

		// TODO : check if new results or other stuff before sending notification
		Resources r = getResources();
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, TabActivity.class), 0);

		Notification notification = new NotificationCompat.Builder(this)
				.setTicker(r.getString(R.string.notification_new_polls_title))
				.setSmallIcon(android.R.drawable.ic_menu_report_image)
				.setContentTitle(r.getString(R.string.notification_new_polls_text))
				.setContentText(r.getString(R.string.notification_new_polls_text)).setContentIntent(pi).setAutoCancel(true)
				.build();

		// TODO : juste pour que l'application ne crashe pas. En attente de correction de bug.
		showBackgroundNotification(0, notification);

	}

	void showBackgroundNotification(int requestCode, Notification notification) {
		Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
		i.putExtra(EXTRA_REQUEST_CODE, requestCode);
		i.putExtra(EXTRA_NOTIFICATION, notification);

		sendOrderedBroadcast(i, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
	}
	
	public static void setServiceAlarm(Context context, boolean isOn) {
		Intent i = new Intent(context, PipollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (isOn) {
			alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
		} else {
			alarmManager.cancel(pi);
			pi.cancel();
		}

		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PipollService.PREF_IS_ALARM_ON, isOn)
				.commit();
	}

	public static boolean isServiceAlarmOn(Context context) {
		Intent i = new Intent(context, PipollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
		return pi != null;
	}


}
