package com.pipoll.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

import com.pipoll.R;
import com.pipoll.customview.CustomViewPager;
import com.pipoll.customview.FixedSpeedScroller;
import com.pipoll.data.Poll;
import com.pipoll.data.PollLab;
import com.pipoll.data.Trend;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.taskmaker.GoogleService;
import com.pipoll.taskmaker.TrendService;

/**
 * @author Bulbi
 * 
 */
public class PollListFragment extends Fragment {

	TextView mTextView;
	// ViewPager mPollViewPager;
	CustomViewPager mPollViewPager;

	// getActivity() returns null at this state
	// private ArrayList<Poll> mPolls = PollLab.get(getActivity()).getPolls();

	private ArrayList<Poll> mPolls;
	private List<Trend> mTrends;

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
		final TrendService trendService = new TrendService(getActivity());
		mTrends = trendService.getTrends(new TaskCallback() {
			final GoogleService googleTaskMaker = new GoogleService(getActivity());
			private List<GoogleResult> googleSearch;

			@Override
			public void onSuccess() {
				Toast.makeText(getActivity(), String.valueOf(mTrends.size()),
						Toast.LENGTH_SHORT).show();
				// for (final Trend trend : mTrends) {
				// googleSearch = googleTaskMaker.googleSearch(trend.getTrendname(),
				// new TaskCallback() {
				//
				// @Override
				// public void onSuccess() {
				// for (final GoogleResult gr : googleSearch) {
				// trend.getTrendNews().add(gr.toString());
				// Toast.makeText(getActivity(), gr.toString(),
				// Toast.LENGTH_SHORT).show();
				//
				// }
				// }
				// });
				//
				// }
				trendService.setNews(mTrends, new TaskCallback() {

					@Override
					public void onSuccess() {

						for (int i = 0; i < 5; i++) {
							Toast.makeText(getActivity(),
									mTrends.get(i).getTrendNews().get(0), Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
			}
		});

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
