package com.pipoll.fragment;

import java.util.ArrayList;
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
import com.pipoll.activity.WebPagerActivity;
import com.pipoll.app.AppController;
import com.pipoll.data.Poll;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.data.parcelable.ParcelableTrendNews;
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
	private static final int TRENDNEWS_COUNT = 3;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
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
		String trendName = mPoll.getTheme();
		mTvTitle.setText(trendName);

		// CategoryService catService = new CategoryService(getActivity());
		// catService.getFBStringCategory(trendName, new ServiceCallback() {
		//
		// @Override
		// public void onServiceDone(Object response) {
		//
		// mTvCategory.setText((String) response);
		// }
		// });

		mTvDescription.setText(mPoll.getTheme());

		mTvCategory.setText(mPoll.getCategory().getName());

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		imageLoader.displayImage(mPoll.getImage(), mImage);

		Resources r = getActivity().getResources();
		// final String moreInfos = r.getString(R.string.more_infos);

		// Trendnews from the RSS FEED filled in the poll creation
		TrendNews firstTrendnews = mPoll.getTrendNews().get(0);
		mTvDescription.setText(Html.fromHtml("<a href=\"" + firstTrendnews.getUrl() + "\">"
				+ firstTrendnews.getTitle() + "</a>"));
		mTvDescription.setMovementMethod(LinkMovementMethod.getInstance());
		mTvDescription.setVisibility(View.VISIBLE);
		GoogleService googleService = new GoogleService(getActivity());
		googleService.getDataFromGoogleNews(trendName, new TrendNewsCallback() {

			@Override
			public void onNewsRetrieved(final List<TrendNews> trendNewsList) {

				if (trendNewsList != null && !trendNewsList.isEmpty()) {
					// populate the list with other source
					if (trendNewsList.size() > 1) {
						mTvDescription2.setText(Html.fromHtml("<a href=\""
								+ trendNewsList.get(0).getUrl() + "\">"
								+ trendNewsList.get(0).getTitle() + "</a>"));
						mTvDescription2.setMovementMethod(LinkMovementMethod.getInstance());
						mTvDescription2.setVisibility(View.VISIBLE);
					}
					if (trendNewsList.size() > 2) {

						mTvDescription3.setText(Html.fromHtml("<a href=\""
								+ trendNewsList.get(1).getUrl() + "\">"
								+ trendNewsList.get(1).getTitle() + "</a>"));
						mTvDescription3.setMovementMethod(LinkMovementMethod.getInstance());
						mTvDescription3.setVisibility(View.VISIBLE);
					}

					// Start WebView Activity to display TrendNews of the Poll.
					mTvTitle.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							ArrayList<ParcelableTrendNews> data = (ArrayList<ParcelableTrendNews>) ParcelableTrendNews
									.getParcelTrendNewsList(trendNewsList);
							if (data.size() >= TRENDNEWS_COUNT) {
								data = new ArrayList<ParcelableTrendNews>(data.subList(0,
										TRENDNEWS_COUNT));
							}

							// start pager
							Intent i = new Intent(getActivity(), WebPagerActivity.class);
							i.putParcelableArrayListExtra(WebPagerActivity.EXTRA_TREND_NEWS,
									data);

							// start tab
							// Intent i = new Intent(getActivity(), WebTabActivity.class);
							// i.putParcelableArrayListExtra(WebTabActivity.EXTRA_TREND_NEWS,
							// data);

							// start single WebView
							// Intent i = new Intent(getActivity(), WebActivity.class);
							// i.putParcelableArrayListExtra(WebPagerActivity.EXTRA_TREND_NEWS,
							// data);
							// String url = data.get(0).getTrendNews().getUrl();
							// i.putExtra(WebFragment.EXTRA_URL, url);

							startActivity(i);

						}
					});
				}
			}
		});

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
			String commentTitle = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_TITLE);
			String commentDescription = data
					.getStringExtra(CommentDialogFragment.EXTRA_KEY_COMMENT_DESCRIPTION);

			// TODO : replace Toast by a save comment function
			// Toast.makeText(getActivity(), "Thanks for your critic",
			// Toast.LENGTH_SHORT).show();
			Toast.makeText(
					getActivity(),
					mPoll.getTheme() + " liked: " + liked + "\ncommentTitle: " + commentTitle
							+ "\ncommentDescription: " + commentDescription,
					Toast.LENGTH_SHORT).show();
			changePage(getActivity());

		}
	}
}
