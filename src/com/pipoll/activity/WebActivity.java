package com.pipoll.activity;

import android.support.v4.app.Fragment;

import com.pipoll.fragment.WebFragment;

public class WebActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		WebFragment fragment = WebFragment.newInstance();

		return fragment;
	}

}
