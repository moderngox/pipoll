package com.pipoll.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.AndroidCharacter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.pipoll.R;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelableTrendNews;
import com.pipoll.fragment.WebFragment;

public class WebTabActivity extends android.app.TabActivity {

	public final static String EXTRA_TREND_NEWS = "WebPagerActivity.EXTRA_TREND_NEWS";
	public final static String EXTRA_TREND_NEWS_INDEX = "WebPagerActivity.EXTRA_TREND_NEWS_INDEX";

	private ArrayList<TrendNews> mTrendNews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_tab);

		ArrayList<ParcelableTrendNews> trendNewsParcelable = getIntent().getParcelableArrayListExtra(EXTRA_TREND_NEWS);
		if (trendNewsParcelable != null) {
			mTrendNews = ParcelableTrendNews.toTrendNewsList(trendNewsParcelable);
		}

		// create the TabHost that will contain the Tabs
		//TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		TabHost tabHost = getTabHost();
		//tabHost.setup(); // marche pas !!
		
		TabSpec tab1 = tabHost.newTabSpec("First Tab");
		TabSpec tab2 = tabHost.newTabSpec("Second Tab");
		TabSpec tab3 = tabHost.newTabSpec("Third tab");

		// Set the Tab name and Activity
		// that will be opened when particular Tab will be selected

		// return WebFragment.newInstance(mTrendNews.get(pos).getUrl());

		tab1.setIndicator("Tab1");
		Intent i1 = new Intent(this, WebActivity.class);
		i1.putExtra(WebFragment.EXTRA_URL, mTrendNews.get(0).getUrl());
		tab1.setContent(i1);

		tab2.setIndicator("Tab2");
		Intent i2 = new Intent(this, WebActivity.class);
		i2.putExtra(WebFragment.EXTRA_URL, mTrendNews.get(1).getUrl());
		tab2.setContent(i2);

		tab3.setIndicator("Tab3");
		Intent i3 = new Intent(this, WebActivity.class);
		i3.putExtra(WebFragment.EXTRA_URL, mTrendNews.get(2).getUrl());
		tab3.setContent(i3);

		/** Add the tabs to the TabHost to display. */
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);

	}
}
