package com.pipoll.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab);
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
					return NotificationFragment.newInstance();
				case 2:
					return TopTrendsFragment.newInstance();
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
					return r.getString(R.string.tab_notification_short_name);
				case 2:
					return r.getString(R.string.tab_top_trends_short_name);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private int getActionBarTitleResource(int position) {
		switch (position) {
		case 0:
			return R.string.tab_poll_list_name;
		case 1:
			return R.string.tab_notification_name;
		case 2:
			return R.string.tab_top_trends_name;
		case 3:
			return R.string.tab_profile_name;
		default:
			return R.string.tab_poll_list_name;
		}
	}

}
