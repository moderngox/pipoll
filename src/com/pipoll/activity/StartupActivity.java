/**
 * 
 */
package com.pipoll.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.Session;
import com.pipoll.R;
import com.pipoll.app.AppController;
import com.pipoll.data.RSSFeed;
import com.pipoll.data.parcelable.ParcelableRSSNode;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.service.PollService;
import com.pipoll.service.TrendService;

/**
 * @author moderngox
 * 
 *         Trash & test code
 * 
 */
public class StartupActivity extends Activity {
	// private List<Trend> mTrends;
	private List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> mRssNodes;
	private List<RSSFeed> mRssFeeds = new ArrayList<RSSFeed>();
	private Session session;
	private AppController application = AppController.getInstance();
	private StartupActivity activity;
	private ProgressBar progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		session = Session.getActiveSession();

		if (session != null) {
			// hide actionBar. To make it properly //TODO define (in themes) and use a
			// NoBarTheme in Manifest
			getActionBar().hide();

			// TODO clear polls at every startup. Got to be managed differently
			application.clearLocalPolls(this);
			setContentView(R.layout.progress);
			progress = (ProgressBar) findViewById(R.id.progressBar1);
			activity = this;
			final TrendService trendService = new TrendService(activity);

			mRssFeeds.add(new RSSFeed(AppController.FASHION_CAT_ID,
					AppController.FASHION_WEEKLY_FEED));
			mRssFeeds
					.add(new RSSFeed(AppController.GLOBAL_CAT_ID, AppController.REUTERS_FEED));
			mRssFeeds.add(new RSSFeed(AppController.AFP_CAT_ID, AppController.AFP_FEED));
			mRssFeeds.add(new RSSFeed(AppController.FOOTBALL_CAT_ID,
					AppController.F365_PLEAGUE_FEED));

			mRssFeeds.add(new RSSFeed(AppController.FASHION_CAT_ID,
					AppController.FASHION_WEEKLY_FEED));
			mRssFeeds.add(new RSSFeed(AppController.BUSINESS_CAT_ID,
					AppController.ECONOMIST_BUSINESS_FEED));
			mRssFeeds.add(new RSSFeed(AppController.TECH_CAT_ID, AppController.WSJ_TECH_FEED));
			// mRssFeeds.add(new RSSFeed(AppController.FOOTBALL_CAT,
			// AppController.F365_PLEAGUE_FEED));

			mRssNodes = trendService.getBackendNodes(mRssFeeds, new TaskCallback() {

				@Override
				public void onSuccess() {
					final PollService pollService = new PollService(activity);

					pollService.listBackendPolls(mRssNodes, 0, 3, new ServiceCallback() {

						@SuppressWarnings("unchecked")
						@Override
						public void onServiceDone(Object response) {
							Bundle extras = new Bundle();
							ArrayList<? extends Parcelable> Parcelresp = (ArrayList<? extends Parcelable>) response;
							extras.putParcelableArrayList(AppController.POLLS_TAG, Parcelresp);
							extras.putParcelableArrayList(AppController.RSS_ELEMS_TAG,
									(ArrayList<? extends Parcelable>) ParcelableRSSNode
											.getParcelNodesFromBE(mRssNodes));
							// extras.putParcelableArrayList(AppController.TRENDS_TAG,
							// (ArrayList<? extends Parcelable>) ParcelableTrend
							// .getParcelTrends(mTrends));
							Toast.makeText(activity,
									String.valueOf(Parcelresp.size()) + " polls loaded",
									Toast.LENGTH_SHORT).show();
							progress.clearAnimation();
							application
									.goToActivity(activity, TabActivity.class, extras, true);

						}

					});
				}
			});
			// mTrends = trendService.getMonthlyTopTrends("p16", "fr", new TaskCallback() {
			// @Override
			// public void onSuccess() {
			// Toast.makeText(getApplicationContext(),
			// String.valueOf(mTrends.size()) + " hot trends available",
			// Toast.LENGTH_SHORT).show();
			//
			// final PollService pollService = new PollService(activity);
			// pollService.createGPolls(mTrends, 0, 30, new ServiceCallback() {
			//
			// @SuppressWarnings("unchecked")
			// @Override
			// public void onServiceDone(Object response) {
			// Bundle extras = new Bundle();
			// ArrayList<? extends Parcelable> Parcelresp = (ArrayList<? extends Parcelable>)
			// response;
			// extras.putParcelableArrayList(AppController.POLLS_TAG, Parcelresp);
			// extras.putParcelableArrayList(AppController.TRENDS_TAG,
			// (ArrayList<? extends Parcelable>) ParcelableTrend
			// .getParcelTrends(mTrends));
			// Toast.makeText(activity,
			// String.valueOf(Parcelresp.size()) + " polls loaded",
			// Toast.LENGTH_SHORT).show();
			// progress.clearAnimation();
			// application
			// .goToActivity(activity, TabActivity.class, extras, true);
			// }
			// });
			//
			// }
			// });

		} else {
			application.goToActivity(activity, MainActivity.class, null, true);
		}
	}
}
