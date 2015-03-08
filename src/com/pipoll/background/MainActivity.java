package com.pipoll.background;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.pipoll.R;

/**
 * @author Bulbi
 * 
 *         https://guides.codepath.com/android/Starting-Background-Services
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onStartService(View v) {
		// Construct our Intent specifying the Service
		Intent i = new Intent(this, MyTestService.class);
		// Add extras to the bundle
		i.putExtra("foo", "bar");
		// Start the service
		startService(i);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Register for the particular broadcast based on ACTION string
		IntentFilter filter = new IntentFilter(MyTestService.ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(testReceiver, filter);
		// or `registerReceiver(testReceiver, filter)` for a normal broadcast
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener when the application is paused
		LocalBroadcastManager.getInstance(this).unregisterReceiver(testReceiver);
		// or `unregisterReceiver(testReceiver)` for a normal broadcast
	}

	// Define the callback for what to do when data is received
	private BroadcastReceiver testReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
			if (resultCode == RESULT_OK) {
				String resultValue = intent.getStringExtra("resultValue");
				Toast.makeText(MainActivity.this, resultValue, Toast.LENGTH_SHORT).show();
			}
		}
	};

	// Alarm fonctions
	public void scheduleAlarm(View v) {
		// Construct an intent that will execute the AlarmReceiver
		Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
		// Create a PendingIntent to be triggered when the alarm goes off
		final PendingIntent pIntent = PendingIntent.getBroadcast(this,
				MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Setup periodic alarm every 5 seconds
		long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
		int intervalMillis = 5000; // 5 seconds
		AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis,
				pIntent);
	}

	public void cancelAlarm(View v) {
		Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
		final PendingIntent pIntent = PendingIntent.getBroadcast(this,
				MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(pIntent);
	}
}