/**
 * 
 */
package com.pipoll.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.api.services.customsearch.model.Result;
import com.pipoll.R;
import com.pipoll.app.AppController;
import com.pipoll.data.FBCategory;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.service.CategoryService;
import com.pipoll.service.PollService;
import com.pipoll.service.TrendService;
import com.pipoll.service.UserService;

/**
 * @author moderngox
 * 
 *         Trash & test code
 * 
 */
public class StartupActivity extends Activity {
	private List<Trend> mTrends;
	protected List<Result> searchResults;
	private Session session;
	private List<FBCategory> fbCategories;
	private List<Like> userLikes;
	private AppController application = AppController.getInstance();
	private StartupActivity activity;
	protected List<String> trendNews;
	private List<Poll> mPolls;
	private static final String TAG = "StartupActivity";
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	private UiLifecycleHelper uiHelper;

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		session = Session.getActiveSession();

		if (session != null) {
			// if (!session.isOpened()) {
			// session = Session.openActiveSession(this, true,
			// Arrays.asList("user_likes", "user_status"), callback);
			// }
			// hide actionBar. To make it properly //TODO define (in themes) and use a
			// NoBarTheme in Manifest
			getActionBar().hide();
			setContentView(R.layout.progress);
			activity = this;
			final TrendService trendService = new TrendService(activity);
			UserService userService = new UserService(activity);
			CategoryService catService = new CategoryService(activity);
			fbCategories = catService.getFBCategories(session);
			userLikes = userService.getUserLikes(session, new TaskCallback() {

				@Override
				public void onSuccess() {

					Toast.makeText(
							getApplicationContext(),
							String.valueOf(userLikes.size())
									+ " FB likes collected for this user", Toast.LENGTH_SHORT)
							.show();
					Toast.makeText(getApplicationContext(),
							String.valueOf(fbCategories.size()) + " categories collected",
							Toast.LENGTH_SHORT).show();
				}

			});

			mTrends = trendService.getTrends(new TaskCallback() {

				@Override
				public void onSuccess() {
					Toast.makeText(getApplicationContext(),
							String.valueOf(mTrends.size()) + " hot trends available",
							Toast.LENGTH_SHORT).show();
					PollService pollService = new PollService(activity);
					// final List<Poll> polls = new ArrayList<Poll>();
					// for (Like like : userLikes) {
					// pollService.createPoll(like, mTrends, new ServiceCallback() {
					//
					// @Override
					// public void onServiceDone(Object response) {
					// Poll poll = (Poll) response;
					// polls.add(poll);
					// Toast.makeText(
					// getApplicationContext(),
					// poll.getTheme() + " with imageURL:" + poll.getImage()
					// + " for trend: " + poll.getTrend().getname(),
					// Toast.LENGTH_SHORT).show();
					// }
					// });
					//
					// }
					pollService.createPollFromTrends(mTrends, 10, new ServiceCallback() {

						@SuppressWarnings("unchecked")
						@Override
						public void onServiceDone(Object response) {
							mPolls = (List<Poll>) response;
							for (Poll poll : mPolls) {
								Toast.makeText(
										getApplicationContext(),
										poll.getTheme() + " with imageURL:" + poll.getImage()
												+ " for trend: " + poll.getTrend().getname(),
										Toast.LENGTH_SHORT).show();
							}
							application.goToActivity(activity, TabActivity.class, null, true);
						}
					});

				}
			});

		} else {
			application.goToActivity(activity, MainActivity.class, null, true);
		}
	}
}
