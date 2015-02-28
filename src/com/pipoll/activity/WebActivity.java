package com.pipoll.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.pipoll.R;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelableTrendNews;
import com.pipoll.fragment.WebFragment;

public class WebActivity extends FragmentActivity {
	public final static String EXTRA_TREND_NEWS = "WebPagerActivity.EXTRA_TREND_NEWS";
	public final static String EXTRA_TREND_NEWS_INDEX = "WebPagerActivity.EXTRA_TREND_NEWS_INDEX";
	
	private ArrayList<TrendNews> mTrendNews;
	
	// TODO changer ça
	protected Fragment createFragment(String url) {	
		return WebFragment.newInstance(url);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        
        ArrayList<ParcelableTrendNews> trendNewsParcelable = getIntent().getParcelableArrayListExtra(EXTRA_TREND_NEWS);
		if (trendNewsParcelable != null) {
			mTrendNews = ParcelableTrendNews.toTrendNewsList(trendNewsParcelable);
		}
        
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

//        if (fragment == null) {
//            fragment = createFragment();
//            manager.beginTransaction()
//                .add(R.id.fragmentContainer, fragment)
//                .commit();
//            manager.beginTransaction().re
//        }
        
        
        ImageButton imgBtnPrevious = (ImageButton) findViewById(R.id.imgBtnPrevious);
		imgBtnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (mViewPager.getCurrentItem() > 0) {
//					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
//				}
			}
		});

		ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
		imgBtnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (mViewPager.getCurrentItem() < mViewPager.getAdapter().getCount() - 1) {
//					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//				}
			}
		});
    }

}
