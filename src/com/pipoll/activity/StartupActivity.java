/**
 * 
 */
package com.pipoll.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.Session;
import com.google.api.services.customsearch.model.Result;
import com.pipoll.R;
import com.pipoll.app.AppController;
import com.pipoll.data.FBCategory;
import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.service.CategoryService;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		session = Session.getActiveSession();
		if (session != null) {
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

					application.goToActivity(activity, TabActivity.class, null);
					// final GoogleService googleTaskMaker = new GoogleService();
					// int i = 0;
					// for (final Trend trend : mTrends) {
					// if (i == 10)
					// break;
					// trendNews = googleTaskMaker.getDataFromGoogle(trend.getTrendname(),
					// new TaskCallback() {
					//
					// @Override
					// public void onSuccess() {
					// for (String trendnews : trendNews) {
					// Toast.makeText(
					// getApplicationContext(),
					// "[trend:" + trend.getTrendname() + "] "
					// + trendnews, Toast.LENGTH_SHORT)
					// .show();
					// }
					// }
					// });
					// i++;
					// }

				}
			});
		} else {
			application.goToActivity(activity, MainActivity.class, null);
		}
	}
}
