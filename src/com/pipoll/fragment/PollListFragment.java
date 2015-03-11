package com.pipoll.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.pipoll.R;
import com.pipoll.app.AppController;
import com.pipoll.background.PipollService;
import com.pipoll.customview.CustomViewPager;
import com.pipoll.customview.FixedSpeedScroller;
import com.pipoll.data.RSSNode;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.data.parcelable.ParcelableRSSElement;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.service.PollService;

/**
 * @author Bulbi
 * 
 *         Fragment managing a list of Polls. Uses a Custom ViewPager.
 */
public class PollListFragment extends VisibleFragment {

	public static final String KEY_FRAGMENT_TITLE = "keyFragmentTitle";
	public static final int POLLS_COUNT = 50;
	private static final String DIALOG_FILTER = "filter";
	private static final int REQUEST_FILTER = 0;

	TextView mTextView;
	CustomViewPager mPollViewPager;

	private LinkedList<ParcelablePoll> mParcelPolls;
	// private ArrayList<Trend> mTrends;
	private ArrayList<RSSNode> mRssNodes;
	private PollService pollService;

	public static PollListFragment newInstance(Bundle extras) {
		PollListFragment fragment = new PollListFragment();
		fragment.setArguments(extras);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		// TODO : null exception hero when coming back from webView
		Bundle extras = getArguments();
		ArrayList<ParcelablePoll> parcelablePolls = extras
				.getParcelableArrayList(AppController.POLLS_TAG);
		ArrayList<ParcelableRSSElement> pRSSNodes = extras
				.getParcelableArrayList(AppController.RSS_ELEMS_TAG);
		mRssNodes = ParcelableRSSElement.getRSSElements(pRSSNodes);
		// Randomize the RSS feeds :
		// http://stackoverflow.com/questions/4228975/how-to-randomize-arraylist?answertab=votes#tab-top
		long seed = System.nanoTime();
		Collections.shuffle(mRssNodes, new Random(seed));
		// ArrayList<ParcelableTrend> parcelableTrends = extras.getParcelableArrayList(
		// AppController.TRENDS_TAG);
		// retrieve the parcelable polls and trends
		mParcelPolls = new LinkedList<ParcelablePoll>(parcelablePolls);
		// mTrends = new ArrayList<Trend>(ParcelableTrend.getTrends(parcelableTrends));
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
					// if (mTrends.size() > getCount()) {
					// int start = mParcelPolls.size() + position + 2;
					// int end = start + 1;
					// pollService.createPolls(mTrends, start, end, new ServiceCallback() {
					//
					// @Override
					// public void onServiceDone(Object response) {
					// if (response != null) {
					// @SuppressWarnings("unchecked")
					// ArrayList<ParcelablePoll> parcelPolls = (ArrayList<ParcelablePoll>)
					// response;
					// for (ParcelablePoll pPoll : parcelPolls) {
					// mParcelPolls.add(pPoll);
					// // Toast.makeText(getActivity(),
					// // "new Poll added: " + pPoll.getPoll().getTheme(),
					// // Toast.LENGTH_SHORT).show();
					// }
					// }
					// }
					// });
					// }
					if (mRssNodes.size() > getCount()) {
						int start = mParcelPolls.size() + position + 2;
						int end = start + 5;
						pollService.createPollsFromRssNodes(mRssNodes, start, end,
								new ServiceCallback() {

									@Override
									public void onServiceDone(Object response) {
										@SuppressWarnings("unchecked")
										ArrayList<ParcelablePoll> parcelPolls = (ArrayList<ParcelablePoll>) response;
										for (ParcelablePoll pPoll : parcelPolls) {
											mParcelPolls.add(pPoll);
											// Toast.makeText(
											// getActivity(),
											// "new Poll added: "
											// + pPoll.getPoll().getTheme()
											// + "size of list:"
											// + mParcelPolls.size(),
											// Toast.LENGTH_SHORT).show();
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

	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_poll_list, menu);
        
        // TODO : how to put settings menu items everywhere ? -> inheritance
//        MenuItem settings = menu.findItem(R.id.menu_item_setting);
        
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_filter:
                // Show FilterDialogFragment
            	FragmentManager fm = getActivity().getSupportFragmentManager();
        		FilterDialogFragment dialog = FilterDialogFragment.newInstance(true);
        		dialog.setTargetFragment(this, REQUEST_FILTER);
        		dialog.show(fm, DIALOG_FILTER);
        		
                return true;
            case R.id.menu_item_toggle_alarm:
            	// Toggle alarm for background search
                boolean shouldStartAlarm = !PipollService.isServiceAlarmOn(getActivity());
                PipollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    getActivity().invalidateOptionsMenu();

                return true;
            case R.id.menu_item_setting:
            	Toast.makeText(getActivity(), "Not implemented", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_alarm);
        if (PipollService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.menu_stop_alarm);
        } else {
            toggleItem.setTitle(R.string.menu_start_alarm);
        }
    }


}
