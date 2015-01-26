package com.pipoll.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipoll.R;
import com.pipoll.customview.PollListViewPager;

public class PollListFragment extends Fragment {

	TextView mTextView;
	//ViewPager mPollViewPager; 
	PollListViewPager mPollViewPager;

	// public static ProfileFragment newInstance(UUID crimeId) {
	// Bundle args = new Bundle();
	// args.putSerializable(EXTRA_CRIME_ID, crimeId);
	//
	// ProfileFragment fragment = new ProfileFragment();
	// fragment.setArguments(args);
	//
	// return fragment;
	// }
	public static PollListFragment newInstance() {
		Bundle args = new Bundle();

		PollListFragment fragment = new PollListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		// UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		// mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_poll_list, parent, false);

		return v;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		 mPollViewPager = (PollListViewPager) view.findViewById(R.id.poll_view_pager);
		 mPollViewPager.setAdapter(new PollAdapter(getChildFragmentManager()));
	}
	
	public static class PollAdapter extends FragmentPagerAdapter {

        public PollAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
//            Bundle args = new Bundle();
//            args.putInt(NotificationFragment.POSITION_KEY, position);
            //return NotificationFragment.newInstance(args);
        	return NotificationFragment.newInstance();
        }
        
        @Override
		public CharSequence getPageTitle(int position) {
			return "Sondage #" + position;
		}
    }
	
	//
	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// if (resultCode != Activity.RESULT_OK)
	// return;
	// if (requestCode == REQUEST_DATE) {
	// Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
	// mCrime.setDate(date);
	// updateDate();
	// }
	// }
}
