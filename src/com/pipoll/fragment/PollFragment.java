package com.pipoll.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pipoll.R;
import com.pipoll.data.Poll;
import com.pipoll.data.PollLab;
import com.pipoll.data.Trend;

/**
 * @author Bulbi
 * 
 */
public class PollFragment extends Fragment {

	public static final String KEY_POLL_ID = "keyPollId";

	TextView mTvTitle;
	TextView mTvDescription;
	TextView mTvDescription2;
	TextView mTvDescription3;
	ImageButton mImgBtnYes;
	ImageButton mImgBtnNo;

	Poll mPoll;

	public static PollFragment newInstance(String pollId) {
		Bundle args = new Bundle();
		args.putString(KEY_POLL_ID, pollId);

		PollFragment fragment = new PollFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String pollId = (String) getArguments().getSerializable(KEY_POLL_ID);
		mPoll = PollLab.get(getActivity()).getPoll(pollId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_poll, parent, false);

		mTvTitle = (TextView) v.findViewById(R.id.text_view_title);
		mTvDescription = (TextView) v.findViewById(R.id.text_view_description);
		mTvDescription2 = (TextView) v.findViewById(R.id.text_view_description_2);
		mTvDescription3 = (TextView) v.findViewById(R.id.text_view_description_3);

		mTvTitle.setText(mPoll.getId());

		// get one Trend for the Poll
		Trend trend = mPoll.getTrend();
		String trendName = mPoll.getTrend().getTrendname();

		mTvDescription.setText(trend.getTrendname());

		String link1 = trend.getTrendNews().get(0);
		String link2 = trend.getTrendNews().get(1);

		Resources r = getActivity().getResources();
		String moreInfos = r.getString(R.string.more_infos);

		mTvDescription2.setText(Html.fromHtml("<a href=\"" + link1 + "\">" + moreInfos
				+ "</a>"));
		mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());

		mTvDescription3.setText(Html.fromHtml("<a href=\"" + link2 + "\">" + moreInfos
				+ "</a>"));
		mTvDescription3.setMovementMethod(LinkMovementMethod.getInstance());

		mImgBtnYes = (ImageButton) v.findViewById(R.id.image_button_yes);

		mImgBtnYes.setOnClickListener(new VoteClickListener(getActivity(), r
				.getString(R.string.like) + " " + trendName));

		mImgBtnNo = (ImageButton) v.findViewById(R.id.image_button_no);
		mImgBtnNo.setOnClickListener(new VoteClickListener(getActivity(), r
				.getString(R.string.dislike) + " " + trendName));

		return v;
	}

	private class VoteClickListener implements View.OnClickListener {
		private Activity mActivity;
		private String mMessage;

		public VoteClickListener(Activity activity, String message) {
			mActivity = activity;
			mMessage = message;
		}

		@Override
		public void onClick(View v) {
			Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
			ViewPager vp = (ViewPager) mActivity.findViewById(R.id.poll_view_pager);

			if (vp.getCurrentItem() < vp.getAdapter().getCount() - 1) {
				vp.setCurrentItem(vp.getCurrentItem() + 1);
			}
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
