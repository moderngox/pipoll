package com.pipoll.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.pipoll.R;
import com.pipoll.fragment.WebFragment;

/**
 * @author Bulbi
 * 
 */

public class WebPagerActivity extends FragmentActivity {
	private static final int TAB_COUNT = 3;
	private ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_web_pager);
		mViewPager = (ViewPager) findViewById(R.id.view_pager_tab);

		FragmentManager fm = getSupportFragmentManager();

		// mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
		mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
			@Override
			public int getCount() {
				return TAB_COUNT;
			}

			@Override
			public Fragment getItem(int pos) {
				Bundle extras = getIntent().getExtras();

				return WebFragment.newInstance();
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

				getActionBar().setTitle(getActionBarTitleResource(position));

			}
		});

		//final ActionBar actionBar = getActionBar();

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
