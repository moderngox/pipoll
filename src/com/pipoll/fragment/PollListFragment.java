package com.pipoll.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

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
import com.pipoll.app.AppController;
import com.pipoll.customview.CustomViewPager;
import com.pipoll.customview.FixedSpeedScroller;
import com.pipoll.data.Trend;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.data.parcelable.ParcelableTrend;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.service.PollService;

/**
 * @author Bulbi
 * 
 *         Fragment managing a list of Polls. Uses a Custom ViewPager.
 */
public class PollListFragment extends Fragment {

	public static final String KEY_FRAGMENT_TITLE = "keyFragmentTitle";
	public static final int POLLS_COUNT = 50;
	TextView mTextView;
	CustomViewPager mPollViewPager;

	private LinkedList<ParcelablePoll> mParcelPolls;
	private ArrayList<Trend> mTrends;
	private PollService pollService;

	public static PollListFragment newInstance(Bundle extras) {
		PollListFragment fragment = new PollListFragment();
		fragment.setArguments(extras);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO : null exception hero when coming back from webView
		ArrayList<ParcelablePoll> parcelablePolls = getArguments().getParcelableArrayList(
				AppController.POLLS_TAG);

		ArrayList<ParcelableTrend> parcelableTrends = getArguments().getParcelableArrayList(
				AppController.TRENDS_TAG);
		// retrieve the parcelable polls and trends
		mParcelPolls = new LinkedList<ParcelablePoll>(parcelablePolls);
		mTrends = new ArrayList<Trend>(ParcelableTrend.getTrends(parcelableTrends));
		pollService = new PollService(getActivity());
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
				return POLLS_COUNT + 1;
			}

			@Override
			public Fragment getItem(int position) {
				if (position < mParcelPolls.size() && position < getCount() - 1) {
					// if the end of the list or the getCount limit is not reached we search
					// for then potentially create a new poll for the current polls list before
					// returning the current poll to the view
					if (mTrends.size() > getCount()) {
						int start = mParcelPolls.size() + position + 2;
						int end = start + 1;
						pollService.createPolls(mTrends, start, end, new ServiceCallback() {

							@Override
							public void onServiceDone(Object response) {
								if (response != null) {
									@SuppressWarnings("unchecked")
									ArrayList<ParcelablePoll> parcelPolls = (ArrayList<ParcelablePoll>) response;
									for (ParcelablePoll pPoll : parcelPolls) {
										mParcelPolls.add(pPoll);
										// Toast.makeText(getActivity(),
										// "new Poll added: " + pPoll.getPoll().getTheme(),
										// Toast.LENGTH_SHORT).show();
									}
								}
							}
						});
					}
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

	}
}
