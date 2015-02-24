package com.pipoll.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pipoll.R;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelableTrendNews;
import com.pipoll.fragment.NotificationFragment;
import com.pipoll.fragment.WebFragment;

/**
 * @author Bulbi
 * 
 *         This activity displays the news associated to the current Poll in a set of WebView inside a
 *         ViewPager.
 */

public class WebPagerActivity extends FragmentActivity {

	public final static String EXTRA_TREND_NEWS = "WebPagerActivity.EXTRA_TREND_NEWS";
	public final static String EXTRA_TREND_NEWS_INDEX = "WebPagerActivity.EXTRA_TREND_NEWS_INDEX";

	private ViewPager mViewPager;
	private ArrayList<TrendNews> mTrendNews;
	private int mIndex;
	private int currentIndex;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_pager);

		ArrayList<ParcelableTrendNews> trendNewsParcelable = getIntent().getParcelableArrayListExtra(EXTRA_TREND_NEWS);
		if (trendNewsParcelable != null) {
			mTrendNews = ParcelableTrendNews.toTrendNewsList(trendNewsParcelable);
		}

		mViewPager = (ViewPager) findViewById(R.id.view_pager_tab);
		// mViewPager.setOffscreenPageLimit(mTrendNews.size() - 1);

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				return mTrendNews.size();
			}

			@Override
			public Fragment getItem(int pos) {
				//if (pos != currentIndex) {
				if (pos == mViewPager.getCurrentItem()) {
					
					Toast.makeText(WebPagerActivity.this, "getItem web fragment", Toast.LENGTH_SHORT).show();
					
					return WebFragment.newInstance(mTrendNews.get(pos).getUrl());
				} else {
					Toast.makeText(WebPagerActivity.this, "getItem empty fragment", Toast.LENGTH_SHORT).show();
					return NotificationFragment.newInstance();
				}

				// return WebFragment.newInstance(mTrendNews.get(pos).getUrl());
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return mTrendNews.get(position).getTitle().toString();
			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				Toast.makeText(WebPagerActivity.this, "onPageSelected " + position, Toast.LENGTH_SHORT).show();

				//currentIndex = position;
				
				getActionBar().setTitle(mTrendNews.get(position).getTitle().toString());
			}
		});

		mIndex = getIntent().getIntExtra(EXTRA_TREND_NEWS_INDEX, 0);
		mViewPager.setCurrentItem(mIndex);

		ImageButton imgBtnPrevious = (ImageButton) findViewById(R.id.imgBtnPrevious);
		imgBtnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mViewPager.getCurrentItem() > 0) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				}
			}
		});

		ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
		imgBtnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mViewPager.getCurrentItem() < mViewPager.getAdapter().getCount() - 1) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
				}
			}
		});
		// final ActionBar actionBar = getActionBar();
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

}
