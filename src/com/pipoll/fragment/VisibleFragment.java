package com.pipoll.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.pipoll.background.PipollService;

/**
 * @author Bulbi
 * 
 *         Fragment inherited from VisibleFragment will cancel the broadcast for Notification
 *         when they are visible. Every visible fragment of the application shouldbe derived
 *         from this class. For fragments in TabActivy, since there is a ViewPager with
 *         (OffscreenPageLimit == TAB_COUNT - 1), only one Fragment will inherit from
 *         VisibleFragment, so that the BroadcastReceiver is not register/unregistered multiple
 *         times.
 * 
 */
public abstract class VisibleFragment extends Fragment {
	public static final String TAG = "VisibleFragment";
	public static final int NOTIFICATION_PRIORITY = 0;

	private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Cancel the notification
			Log.e(TAG, TAG + " received result: " + getResultCode());

			//abortBroadcast();
			setResultCode(Activity.RESULT_CANCELED);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(PipollService.ACTION_SHOW_NOTIFICATION);
		filter.setPriority(NOTIFICATION_PRIORITY);

		Log.e(TAG, TAG + " : Register receiver - onResume");

		getActivity().registerReceiver(mOnShowNotification, filter,
				PipollService.PERM_PRIVATE, null);
	}

	@Override
	public void onPause() {
		super.onPause();

		Log.e(TAG, TAG + " : Unregister receiver - onPause");

		getActivity().unregisterReceiver(mOnShowNotification);
	}
}
