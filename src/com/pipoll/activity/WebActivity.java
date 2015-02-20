package com.pipoll.activity;

import android.support.v4.app.Fragment;

import com.pipoll.fragment.WebFragment;

public class WebActivity extends SingleFragmentActivity {
	
	@Override
	protected Fragment createFragment() {	
		String url = getIntent().getStringExtra(WebFragment.EXTRA_URL);
		return WebFragment.newInstance(url);
	}

}
