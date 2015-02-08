package com.pipoll.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.pipoll.R;
import com.pipoll.customview.CustomViewPager;
import com.pipoll.customview.FixedSpeedScroller;
import com.pipoll.data.Poll;
import com.pipoll.data.parcelable.ParcelablePoll;

/**
 * @author Bulbi
 * 
 *         Fragment managing a list of Polls. Uses a Custom ViewPager.
 */
public class PollListFragment extends Fragment {

	public static final String KEY_FRAGMENT_TITLE = "keyFragmentTitle";

	TextView mTextView;
	// ViewPager mPollViewPager;
	CustomViewPager mPollViewPager;

	// getActivity() returns null at this state
	// private ArrayList<Poll> mPolls = PollLab.get(getActivity()).getPolls();

	private static ArrayList<Poll> mPolls;
	private static ArrayList<ParcelablePoll> mParcelPolls;

	public static PollListFragment newInstance(ArrayList<ParcelablePoll> parcelablePolls) {
		Bundle args = new Bundle();

		PollListFragment fragment = new PollListFragment();
		fragment.setArguments(args);

		// retrieve the parcelable polls
		mParcelPolls = parcelablePolls;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// mPolls = PollLab.get(getActivity()).getPolls();

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
		mPollViewPager = (CustomViewPager) view.findViewById(R.id.poll_view_pager);
		mPollViewPager.setPagingEnabled(false);
		mPollViewPager.setIsVertical(true);

		mPollViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				// return mPolls.size() + 1;
				return mParcelPolls.size() + 1;
			}

			@Override
			public Fragment getItem(int position) {
				if (position < getCount() - 1) {
					// Bundle args = new Bundle();
					// args.putInt(PollFragment.KEY_POLL_ID, position);
					// return PollFragment.newInstance(args);
					// String pollId = mPolls.get(position).getId();
					// return PollFragment.newInstance(pollId);
					// return PollFragment.newInstance(mPolls.get(position));
					return PollFragment.newInstance(mParcelPolls.get(position));
				} else {
					return PollEndFragment.newInstance();
				}
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return null;
			}
		});

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);

			Interpolator sInterpolator = new AccelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(mPollViewPager.getContext(),
					sInterpolator);
			scroller.setFixedDuration(500);
			mScroller.set(mPollViewPager, scroller);
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

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
