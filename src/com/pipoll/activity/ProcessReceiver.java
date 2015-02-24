package com.pipoll.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ProcessReceiver extends BroadcastReceiver {
	Listener mListener;
	String mResult; // dummy data

	public void setListener(Listener listener) {
		mListener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.

		// Callback when task commpleted ?
		mListener.onReceiveCompleted(mResult);

		Toast.makeText(context, "ProcessReceiver Received something", Toast.LENGTH_SHORT)
				.show();
	}

	public interface Listener {
		void onReceiveCompleted(String result);
	}
}
