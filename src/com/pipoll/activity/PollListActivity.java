package com.pipoll.activity;

import android.support.v4.app.Fragment;

import com.pipoll.fragment.PollListFragment;

public class PollListActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
//        UUID crimeId = (UUID)getIntent()
//            .getSerializableExtra(PollListFragment.EXTRA_CRIME_ID);
//        return PollListFragment.newInstance(crimeId);
		
	    return PollListFragment.newInstance();
    }
}
