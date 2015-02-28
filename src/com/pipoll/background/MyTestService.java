package com.pipoll.background;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MyTestService extends IntentService {
	public static final String ACTION = "com.codepath.example.servicesdemo.MyTestService";

	// Must create a default constructor
	public MyTestService() {
		// Used to name the worker thread, important only for debugging.
		super("test-service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// Fetch data passed into the intent on start
		String val = intent.getStringExtra("foo");
		// Construct an Intent tying it to the ACTION (arbitrary event namespace)
		Intent in = new Intent(ACTION);
		// Put extras into the intent as usual
		in.putExtra("resultCode", Activity.RESULT_OK);
		in.putExtra("resultValue", "My Result Value. Passed in: " + val);
		// Fire the broadcast with intent packaged
		LocalBroadcastManager.getInstance(this).sendBroadcast(in);
		// or sendBroadcast(in) for a normal broadcast;
	}
}