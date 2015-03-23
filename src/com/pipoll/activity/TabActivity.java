package com.pipoll.activity;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.pipoll.R;
import com.pipoll.fragment.NotificationFragment;
import com.pipoll.fragment.PollListFragment;
import com.pipoll.fragment.ProfileFragment;
import com.pipoll.fragment.TopTrendsFragment;

/**
 * @author Bulbi
 * 
 */
@SuppressWarnings("deprecation")
public class TabActivity extends FragmentActivity {
	private static final int TAB_COUNT = 4;
	private ViewPager mViewPager;

	// Alarm Test
	private void processAlarm() {
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				Toast.makeText(context, "!!!!!!!", Toast.LENGTH_SHORT).show();
			}
		};
		IntentFilter filter = new IntentFilter();
		this.registerReceiver(receiver, filter);

		PendingIntent receiverPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				this, receiver.getClass()), 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 2);

		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				10 * 1000, receiverPendingIntent);

		// ProcessReceiver.Listener listener = new ProcessReceiver.Listener() {
		//
		// @Override
		// public void onReceiveCompleted(String result) {
		// Toast.makeText(context, "ProcessReceiver Received something",
		// Toast.LENGTH_SHORT).show();
		// }
		// };
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab);

		// alarm test
		// processAlarm();

		mViewPager = (ViewPager) findViewById(R.id.view_pager_tab);
		mViewPager.setOffscreenPageLimit(TAB_COUNT - 1);

		FragmentManager fm = getSupportFragmentManager();
		// mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return TAB_COUNT;
			}

			@Override
			public Fragment getItem(int pos) {
				// UUID crimeId = crimes.get(pos).getId();
				// return ProfileFragment.newInstance(crimeId);
				Bundle extras = getIntent().getExtras();

				switch (pos) {
				case 0:
					return PollListFragment.newInstance(extras);
				case 1:
					return TopTrendsFragment.newInstance();
				case 2:
					return NotificationFragment.newInstance();
				case 3:
					return ProfileFragment.newInstance();
				default:
					return PollListFragment.newInstance(extras);
				}
			}

			@Override
			public CharSequence getPageTitle(int position) {
				Resources r = getResources();

				switch (position) {
				case 0:
					return r.getString(R.string.tab_poll_list_short_name);
				case 1:
					return r.getString(R.string.tab_top_trends_short_name);
				case 2:
					return r.getString(R.string.tab_notification_short_name);
				case 3:
					return r.getString(R.string.tab_profile_short_name);
				default:
					return r.getString(R.string.tab_poll_list_short_name);
				}
			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);

				getActionBar().setTitle(getActionBarTitleResource(position));

			}
		});

		final ActionBar actionBar = getActionBar();

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		@SuppressWarnings("unused")
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@SuppressWarnings("unused")
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// show the given tab
			}

			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// hide the given tab
			}

			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// probably ignore this event
			}

			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				// When the tab is selected, switch to the
				// corresponding page in the ViewPager.
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				// Nothing to do
			}

			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				// Nothing to do
			}
		};

		// Add the tabs, specifying the tab's text and TabListener
		for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mViewPager.getAdapter().getPageTitle(i))
					.setTabListener(tabListener));
		}

	}

	private int getActionBarTitleResource(int position) {
		switch (position) {
		case 0:
			return R.string.tab_poll_list_name;
		case 1:
			return R.string.tab_top_trends_name;
		case 2:
			return R.string.tab_notification_name;
		case 3:
			return R.string.tab_profile_name;
		default:
			return R.string.tab_poll_list_name;
		}
	}

}
