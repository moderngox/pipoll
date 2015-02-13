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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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

	private TextView mTvTitle;
	private TextView mTvDescription;
	private TextView mTvDescription2;
	private TextView mTvDescription3;
	private TextView mTvCategory;
	private ImageButton mImgBtnYes;
	private ImageButton mImgBtnNo;
	private Button mBtnNext;

	private Poll mPoll;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_poll, parent, false);

		mTvTitle = (TextView) v.findViewById(R.id.text_view_title);
		mTvCategory = (TextView) v.findViewById(R.id.text_view_category);
		mTvDescription = (TextView) v.findViewById(R.id.text_view_description);
		mTvDescription2 = (TextView) v.findViewById(R.id.text_view_description_2);
		mTvDescription3 = (TextView) v.findViewById(R.id.text_view_description_3);
		mImgBtnYes = (ImageButton) v.findViewById(R.id.image_button_yes);
		mImgBtnNo = (ImageButton) v.findViewById(R.id.image_button_no);
		mImage = (ImageView) v.findViewById(R.id.image);
		mBtnNext = (Button) v.findViewById(R.id.button_next_poll);

		buildPoll();
		return v;
	}

	public void buildPoll() {
		mTvTitle.setText(mPoll.getTheme());

		String trendName = mPoll.getTheme();

		mTvDescription.setText(mPoll.getTheme());
		mTvCategory.setText(mPoll.getCategory().getName());

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		imageLoader.displayImage(mPoll.getImage(), mImage);

		Resources r = getActivity().getResources();
		// final String moreInfos = r.getString(R.string.more_infos);

		GoogleService googleService = new GoogleService(getActivity());
		googleService.getDataFromGoogleNews(mPoll.getTheme(), new TrendNewsCallback() {

			@Override
			public void onNewsRetrieved(List<TrendNews> trendsnews) {

				if (trendsnews != null && !trendsnews.isEmpty()) {
					mTvDescription.setText(Html.fromHtml("<a href=\"" + trendsnews.get(0).getUrl() + "\">"
							+ trendsnews.get(0).getTitle() + "</a>"));
					mTvDescription.setMovementMethod(LinkMovementMethod.getInstance());
					mTvDescription.setVisibility(View.VISIBLE);
					if (trendsnews.size() > 1) {
						mTvDescription2.setText(Html.fromHtml("<a href=\"" + trendsnews.get(1).getUrl() + "\">"
								+ trendsnews.get(1).getTitle() + "</a>"));
						mTvDescription2.setVisibility(View.VISIBLE);
					}
					if (trendsnews.size() > 2) {
						mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());

						mTvDescription3.setText(Html.fromHtml("<a href=\"" + trendsnews.get(2).getUrl() + "\">"
								+ trendsnews.get(2).getTitle() + "</a>"));
						mTvDescription3.setVisibility(View.VISIBLE);
					}
				}
			}
		});

		mTvDescription3.setMovementMethod(LinkMovementMethod.getInstance());

		mImgBtnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCommentDialog(true);
			}
		});

		mImgBtnNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCommentDialog(false);
			}
		});

		mBtnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changePage(getActivity());
			}
		});
	}

	// private class VoteClickListener implements View.OnClickListener {
	// private Activity mActivity;
	// private String mMessage;
	//
	// public VoteClickListener(Activity activity, String message) {
	// mActivity = activity;
	// mMessage = message;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// Toast.makeText(mActivity, mMessage, Toast.LENGTH_SHORT).show();
	//
	// FragmentManager fm = getActivity().getSupportFragmentManager();
	// CommentDialogFragment dialog = CommentDialogFragment.newInstance(true);
	// dialog.setTargetFragment(PollFragment.this, REQUEST_COMMENT);
	// dialog.show(fm, DIALOG_COMMENT);
	// }
	// }

	private void showCommentDialog(boolean liked) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		CommentDialogFragment dialog = CommentDialogFragment.newInstance(liked);
		dialog.setTargetFragment(PollFragment.this, REQUEST_COMMENT);
		dialog.show(fm, DIALOG_COMMENT);
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

			boolean liked = data.getBooleanExtra(CommentDialogFragment.EXTRA_KEY_LIKED, true);
			String commentTitle = data.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_TITLE);
			String commentDescription = data.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_DESCRIPTION);

			// TODO : replace Toast by a save comment function
			Toast.makeText(
					getActivity(),
					mPoll.getTheme() + " liked: " + liked + "\ncommentTitle: " + commentTitle + "\ncommentDescription: "
							+ commentDescription, Toast.LENGTH_SHORT).show();
			changePage(getActivity());
		}
	}
}
