/**
 * 
 */
package com.pipoll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.java.frej.fuzzy.Fuzzy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.data.TrendNews;
import com.pipoll.interfaces.IPoll;
import com.pipoll.interfaces.callback.GetImgCallback;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.interfaces.callback.TrendNewsCallback;
import com.pipoll.utils.ObjectMapper;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @Poll
 */
public class PollService implements IPoll {

	private Activity activity;

	public PollService(Activity activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.IPoll#createPoll(com.pipoll.data.Like, java.util.List)
	 */
	@Override
	public void createPoll(final Like userLike, final List<Trend> trends4like,
			final ServiceCallback serviceCallback) {
		// TODO create a poll if a match exists between the userLike and the list of trends
		// (trends4like)
		final Poll poll = new Poll();
		String userLikename = userLike.getName();
		String userLikeCat = userLike.getCategory().getName();
		final GoogleService googleService = new GoogleService();
		for (final Trend trend : trends4like) {
			// if we got an approximative match
			if (Fuzzy.equals(userLikename, trend.getname())
					|| Fuzzy.equals(userLikeCat, trend.getname())) {
				// fill the poll theme with the category and the trend name
				poll.setTheme(trend.getname());
				poll.setCategory(userLike.getCategory());
				long date = new Date().getTime();
				poll.setCreatedAt(date);
				poll.setUpdatedAt(date);
				getLikeAvatar(Session.getActiveSession(), userLike.getId(),
						new GetImgCallback() {

							@Override
							public void onImageRetrieved(String ImgURL) {
								// fill the poll with an avatar URL
								poll.setImage(ImgURL);
								// We gotta complete the current Trend with a list of trends
								// news
								googleService.getDataFromGoogle(trend.getname(),
										new TrendNewsCallback() {

											@Override
											public void onNewsRetrieved(
													List<TrendNews> trendsnews) {
												trend.setTrendNews(trendsnews);
												// then populate the poll with this trend, the
												// created/updated date
												poll.setTrend(trend);
												// the poll is ready to be sent
												serviceCallback.onServiceDone(poll);
											}
										});

							}
						});

				// finally we go out of the loop
				break;
			}

		}
	}

	@Override
	public String getLikeAvatar(final Session fbSession, final String likeID,
			final GetImgCallback getImgCallback) {
		final List<String> pagePicture = new ArrayList<String>();
		String pageRequest = "/" + likeID + "/picture";
		Bundle params = new Bundle();
		params.putString("type", "large");
		params.putBoolean("redirect", false);
		Request.Callback callback = new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				try {
					JSONObject jsonResp = new JSONObject(response.getRawResponse());
					pagePicture.add(jsonResp.getJSONObject("data").getString("url"));
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Parsing error. Please retry", Toast.LENGTH_SHORT)
							.show();
				}
				if (getImgCallback != null && !pagePicture.isEmpty()) {
					getImgCallback.onImageRetrieved(pagePicture.get(0));
				}
			}

		};
		Request request = new Request(fbSession, pageRequest, params, HttpMethod.GET, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return !pagePicture.isEmpty() ? pagePicture.get(0) : null;
	}

	@Override
	public Like getLike(final Session fbSession, final String query,
			final ServiceCallback serviceCallback) {
		final List<Like> likes = new ArrayList<Like>();
		String likeRequest = "/v1.0/search";
		Bundle params = new Bundle();
		params.putString("q", query);
		params.putString("type", "page");
		// params.putString("access_token", "{" + AppController.FB_ACCESS_TOKEN + "}");
		params.putInt("limit", 100);
		Request.Callback callback = new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				try {
					Like result = null;
					if (response != null) {
						JSONArray jsonArray = new JSONObject(response.getRawResponse())
								.getJSONArray("data");
						if (jsonArray.length() != 0) {
							JSONObject jsonLike = jsonArray.getJSONObject(0);
							result = ObjectMapper.mapLike(jsonLike);
						}
					}
					serviceCallback.onServiceDone(result);
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Parsing error. Please retry", Toast.LENGTH_SHORT)
							.show();
				}
			}

		};
		Request request = new Request(fbSession, likeRequest, params, HttpMethod.GET, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return !likes.isEmpty() ? likes.get(0) : null;
	}

	@Override
	public void createPollFromTrends(List<Trend> trends, final int pollNb,
			final ServiceCallback serviceCallback) {
		final List<Poll> polls = new ArrayList<Poll>();
		final Session session = Session.getActiveSession();
		for (int i = 0; i < pollNb; i++) {
			Trend trend = trends.get(i);
			final Poll poll = new Poll();
			long date = new Date().getTime();
			poll.setCreatedAt(date);
			poll.setUpdatedAt(date);
			poll.setTheme(trend.getname());
			poll.setTrend(trend);

			getLike(session, trend.getname(), new ServiceCallback() {

				@Override
				public void onServiceDone(Object response) {
					if (response != null) {
						getLikeAvatar(session, ((Like) response).getId(),
								new GetImgCallback() {

									@Override
									public void onImageRetrieved(String imgURL) {
										poll.setImage(imgURL);
									}
								});
					}
				}
			});
			polls.add(poll);
		} // end for
		serviceCallback.onServiceDone(polls);
	}
}
