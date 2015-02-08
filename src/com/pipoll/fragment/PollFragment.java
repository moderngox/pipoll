package com.pipoll.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
	private static final String DIALOG_COMMENT = "comment";
	private static final int REQUEST_COMMENT = 0;

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
		String trendName = mPoll.getTrend().getname();

		mTvDescription.setText(trend.getname());

		String link1 = trend.getTrendNews().get(0).getUrl();
		String link2 = trend.getTrendNews().get(1).getUrl();

		String link3 = "dummy text";

		Resources r = getActivity().getResources();
		String moreInfos = r.getString(R.string.more_infos);

		mTvDescription.setText(Html
				.fromHtml("<a href=\"" + link1 + "\">" + moreInfos + "</a>"));
		mTvDescription.setMovementMethod(LinkMovementMethod.getInstance());

		mTvDescription2.setText(Html.fromHtml("<a href=\"" + link2 + "\">" + moreInfos
				+ "</a>"));
		mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());

		mTvDescription3.setText(Html.fromHtml("<a href=\"" + link3 + "\">" + moreInfos
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
//			ViewPager vp = (ViewPager) mActivity.findViewById(R.id.poll_view_pager);
//
//			if (vp.getCurrentItem() < vp.getAdapter().getCount() - 1) {
//				vp.setCurrentItem(vp.getCurrentItem() + 1);
//			}
			//changePage(mActivity);
			
			// start CommentDialog
			FragmentManager fm = getActivity().getSupportFragmentManager();
			CommentDialogFragment dialog = CommentDialogFragment.newInstance(true);
			dialog.setTargetFragment(PollFragment.this, REQUEST_COMMENT);
			dialog.show(fm, DIALOG_COMMENT);
		}
	}

	private void changePage(Activity activity) {
		ViewPager vp = (ViewPager) activity.findViewById(R.id.poll_view_pager);

		if (vp.getCurrentItem() < vp.getAdapter().getCount() - 1) {
			vp.setCurrentItem(vp.getCurrentItem() + 1);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_COMMENT) {
			changePage(getActivity());
			
			boolean liked = data.getBooleanExtra(CommentDialogFragment.EXTRA_KEY_LIKED, true);
			String commentTitle = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_TITLE);
			String commentDescription = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_DESCRIPTION);

			// TODO : save comment here
			Toast.makeText(getActivity(), "" + liked, Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), commentTitle, Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), commentDescription, Toast.LENGTH_SHORT).show();
		}
	}
}
