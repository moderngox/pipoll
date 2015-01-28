package com.pipoll.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipoll.R;
import com.pipoll.customview.PollListViewPager;
import com.pipoll.data.Poll;
import com.pipoll.data.PollLab;

public class PollListFragment extends Fragment {

	TextView mTextView;
	// ViewPager mPollViewPager;
	PollListViewPager mPollViewPager;

	// getActivity() returns null at this state
	// private ArrayList<Poll> mPolls = PollLab.get(getActivity()).getPolls();

	private ArrayList<Poll> mPolls;

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

		mPolls = PollLab.get(getActivity()).getPolls();
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
		mPollViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				return mPolls.size();
			}

			@Override
			public Fragment getItem(int position) {
				// Bundle args = new Bundle();
				// args.putInt(NotificationFragment.POSITION_KEY, position);
				// return NotificationFragment.newInstance(args);
				String pollId = mPolls.get(position).getId();
				return PollFragment.newInstance(pollId);
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return "Sondage #" + position;
			}
		});

		// UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		// for (int i = 0; i < crimes.size(); i++) {
		// if (crimes.get(i).getId().equals(crimeId)) {
		// mViewPager.setCurrentItem(i);
		// break;
		// }
		// }

	}

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
