package com.pipoll.fragment;

import java.util.List;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pipoll.R;
import com.pipoll.app.AppController;
import com.pipoll.data.Poll;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.interfaces.callback.TrendNewsCallback;
import com.pipoll.service.GoogleService;

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

	static Poll mPoll;
	private ImageView mImage;

	public static PollFragment newInstance(String pollId) {
		Bundle args = new Bundle();
		args.putString(KEY_POLL_ID, pollId);

		PollFragment fragment = new PollFragment();
		fragment.setArguments(args);

		return fragment;
	}

	public static PollFragment newInstance(ParcelablePoll pPoll) {
		Bundle args = new Bundle();
		args.putParcelable(AppController.POLL_TAG, pPoll);

		PollFragment fragment = new PollFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// String pollId = (String) getArguments().getSerializable(KEY_POLL_ID);
		// mPoll = PollLab.get(getActivity()).getPoll(pollId);

		ParcelablePoll pPoll = getArguments().getParcelable(AppController.POLL_TAG);
		mPoll = pPoll.getPoll();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_poll, parent, false);

		mTvTitle = (TextView) v.findViewById(R.id.text_view_title);
		mTvDescription = (TextView) v.findViewById(R.id.text_view_description);
		mTvDescription2 = (TextView) v.findViewById(R.id.text_view_description_2);
		mTvDescription3 = (TextView) v.findViewById(R.id.text_view_description_3);
		mImgBtnYes = (ImageButton) v.findViewById(R.id.image_button_yes);
		mImgBtnNo = (ImageButton) v.findViewById(R.id.image_button_no);
		mImage = (ImageView) v.findViewById(R.id.image);
		buildPoll();
		// mTvTitle.setText(mPoll.getId());
		//
		// // get one Trend for the Poll
		// Trend trend = mPoll.getTrend();
		// String trendName = mPoll.getTrend().getname();
		//
		// mTvDescription.setText(trend.getname());
		//
		// String link1 = trend.getTrendNews().get(0).getUrl();
		// String link2 = trend.getTrendNews().get(1).getUrl();
		//
		// String link3 = "dummy text";
		//
		// Resources r = getActivity().getResources();
		// String moreInfos = r.getString(R.string.more_infos);
		//
		// mTvDescription.setText(Html
		// .fromHtml("<a href=\"" + link1 + "\">" + moreInfos + "</a>"));
		// mTvDescription.setMovementMethod(LinkMovementMethod.getInstance());
		//
		// mTvDescription2.setText(Html.fromHtml("<a href=\"" + link2 + "\">" + moreInfos
		// + "</a>"));
		// mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());
		//
		// mTvDescription3.setText(Html.fromHtml("<a href=\"" + link3 + "\">" + moreInfos
		// + "</a>"));
		// mTvDescription3.setMovementMethod(LinkMovementMethod.getInstance());
		//
		// mImgBtnYes = (ImageButton) v.findViewById(R.id.image_button_yes);
		//
		// mImgBtnYes.setOnClickListener(new VoteClickListener(getActivity(), r
		// .getString(R.string.like) + " " + trendName));
		//
		// mImgBtnNo.setOnClickListener(new VoteClickListener(getActivity(), r
		// .getString(R.string.dislike) + " " + trendName));

		return v;
	}

	public void buildPoll() {
		mTvTitle.setText(mPoll.getTheme());

		String trendName = mPoll.getTheme();

		mTvDescription.setText(mPoll.getTheme());

		Resources r = getActivity().getResources();
		final String moreInfos = r.getString(R.string.more_infos);

		GoogleService googleService = new GoogleService(getActivity());
		googleService.getDataFromGoogle(mPoll.getTheme(), new TrendNewsCallback() {

			@Override
			public void onNewsRetrieved(List<TrendNews> trendsnews) {
				if (!trendsnews.isEmpty()) {
					mTvDescription.setText(Html.fromHtml("<a href=\"" + trendsnews.get(0)
							+ "\">" + moreInfos + "</a>"));
					mTvDescription.setMovementMethod(LinkMovementMethod.getInstance());

					mTvDescription2.setText(Html.fromHtml("<a href=\"" + trendsnews.get(1)
							+ "\">" + moreInfos + "</a>"));
					mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());

					mTvDescription3.setText(Html.fromHtml("<a href=\"" + trendsnews.get(2)
							+ "\">" + moreInfos + "</a>"));
				}
			}

		});

		mTvDescription3.setMovementMethod(LinkMovementMethod.getInstance());

		mImgBtnYes.setOnClickListener(new VoteClickListener(getActivity(), r
				.getString(R.string.like) + " " + trendName));

		mImgBtnNo.setOnClickListener(new VoteClickListener(getActivity(), r
				.getString(R.string.dislike) + " " + trendName));
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		imageLoader.displayImage(mPoll.getImage(), mImage);
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
			// start CommentDialog
			FragmentManager fm = getActivity().getSupportFragmentManager();
			CommentDialogFragment dialog = CommentDialogFragment.newInstance(true);
			dialog.setTargetFragment(PollFragment.this, REQUEST_COMMENT);
			dialog.show(fm, DIALOG_COMMENT);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_COMMENT) {
			boolean liked = data.getBooleanExtra(CommentDialogFragment.EXTRA_KEY_LIKED, true);
			String commentTitle = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_TITLE);
			String commentDescription = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_DESCRIPTION);

			// TODO : save comment here
			// Toast.makeText(getActivity(), "" + liked, Toast.LENGTH_SHORT).show();
			// Toast.makeText(getActivity(), commentTitle, Toast.LENGTH_SHORT).show();
			// Toast.makeText(getActivity(), commentDescription, Toast.LENGTH_SHORT).show();
		}
	}
}
