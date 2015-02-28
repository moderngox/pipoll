package com.pipoll.service;

import java.util.ArrayList;

import com.pipoll.activity.TabActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.pipoll.R;

public class DummyService extends IntentService {
	private static final String TAG = "DummyService";

	// private static final int POLL_INTERVAL = 1000 * 60 * 5; // 5 minutes
	private static final int POLL_INTERVAL = 1000 * 15; // N seconds
	public static final String PREF_IS_ALARM_ON = "isAlarmOn";

	public static final String ACTION_SHOW_NOTIFICATION = "com.bignerdranch.android.photogallery.SHOW_NOTIFICATION";

	public static final String PERM_PRIVATE = "com.bignerdranch.android.photogallery.PRIVATE";

	public DummyService() {
		super(TAG);
	}

	@Override
	public void onHandleIntent(Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
		Log.i(TAG, "In service! network available: " + isNetworkAvailable);
		if (!isNetworkAvailable)
			return;

		Log.i(TAG, "Service onHandleIntent()");

		Resources r = getResources();
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, TabActivity.class), 0);

//		Notification notification = new NotificationCompat.Builder(this)
//				.setTicker(r.getString(R.string.new_pictures_title))
//				.setSmallIcon(android.R.drawable.ic_menu_report_image)
//				.setContentTitle(r.getString(R.string.new_pictures_title))
//				.setContentText(r.getString(R.string.new_pictures_text)).setContentIntent(pi).setAutoCancel(true)
//				.build();

		Notification notification = new NotificationCompat.Builder(this)
				.setTicker("ticker")
				.setSmallIcon(android.R.drawable.ic_menu_report_image)
				.setContentTitle("content")
				.setContentText("contentText").setContentIntent(pi).setAutoCancel(true)
				.build();

		showBackgroundNotification(0, notification);
	}

	public static void setServiceAlarm(Context context, boolean isOn) {
		Intent i = new Intent(context, DummyService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (isOn) {
			alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
		} else {
			alarmManager.cancel(pi);
			pi.cancel();
		}

		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(DummyService.PREF_IS_ALARM_ON, isOn)
				.commit();
	}

	public static boolean isServiceAlarmOn(Context context) {
		Intent i = new Intent(context, DummyService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
		return pi != null;
	}

	void showBackgroundNotification(int requestCode, Notification notification) {
		Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
		i.putExtra("REQUEST_CODE", requestCode);
		i.putExtra("NOTIFICATION", notification);

		sendOrderedBroadcast(i, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
	}
}
