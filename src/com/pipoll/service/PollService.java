/**
 * 
 */
package com.pipoll.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.java.frej.fuzzy.Fuzzy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.pipoll.app.AppController;
import com.pipoll.data.Category;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelablePoll;
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
	public void createMatchPoll(final Like userLike, final List<Trend> trends4like,
			final ServiceCallback serviceCallback) {
		// TODO create a poll if a match exists between the userLike and the list of trends
		// (trends4like)
		final Poll poll = new Poll();
		String userLikename = userLike.getName();
		String userLikeCat = userLike.getCategory().getName();
		final GoogleService googleService = new GoogleService();
		for (final Trend trend : trends4like) {
			// if we got an approximative match
			if (Fuzzy.equals(userLikename, trend.getName())
					|| Fuzzy.equals(userLikeCat, trend.getName())) {
				// fill the poll theme with the category and the trend name
				poll.setTheme(trend.getName());
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
								googleService.getDataFromGoogle(trend.getName(),
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
					getImgCallback.onImageRetrieved(jsonResp.getJSONObject("data").getString(
							"url"));

				} catch (JSONException e) {
					e.printStackTrace();
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
		params.putInt("limit", 1);
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
							likes.add(result);
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
	public void createPollFromTrend(final Trend trend, final ServiceCallback serviceCallback) {

		final Session session = Session.getActiveSession();
		final Poll poll = new Poll();
		long date = new Date().getTime();
		poll.setId(String.valueOf(date));
		poll.setCreatedAt(date);
		poll.setUpdatedAt(date);
		poll.setTheme(trend.getName());

		GoogleService googleService = new GoogleService(activity);
		// get the news list for the trend
		googleService.getDataFromGoogle(trend.getName(), new TrendNewsCallback() {

			@Override
			public void onNewsRetrieved(List<TrendNews> trendsnews) {
				trend.setTrendNews(new ArrayList<TrendNews>(trendsnews));
				// then populate the poll with this trend, the
				// created/updated date
				poll.setTrend(trend);
				// finally get the image URL for the trend
				getLike(session, trend.getName(), new ServiceCallback() {

					@Override
					public void onServiceDone(Object response) {
						if (response != null) {
							getLikeAvatar(session, ((Like) response).getId(),
									new GetImgCallback() {

										@Override
										public void onImageRetrieved(String imgURL) {
											poll.setImage(imgURL);
											serviceCallback.onServiceDone(poll);
										}
									});
						}
					}
				});

			}
		});

	}

	@Override
	public void createPolls(final List<Trend> trends, final int start, int end,
			final ServiceCallback serviceCallback) {
		final List<Like> likes = new ArrayList<Like>();
		end = end > trends.size() ? trends.size() : end;
		List<Trend> trendSubList = trends.subList(start, end);
		final String accessToken = Session.getActiveSession().getAccessToken();
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		final AsyncTask<Like, Void, List<ParcelablePoll>> getPollsTask = new AsyncTask<Like, Void, List<ParcelablePoll>>() {

			@Override
			protected List<ParcelablePoll> doInBackground(Like... likes) {
				List<ParcelablePoll> polls = new ArrayList<ParcelablePoll>();
				try {

					String imgURL = null;
					for (Like like : likes) { // that means this a fake like
						if (like.getId() != null) {
							String request = "https://graph.facebook.com/" + like.getId()
									+ "/picture?type=large&redirect=false&access_token="
									+ accessToken;
							HttpGet httpgetreq = new HttpGet(request);
							httpgetreq.setHeader("Content-type", "application/json");
							String responseText = null;
							HttpResponse httpresponse = httpclient.execute(httpgetreq);
							responseText = EntityUtils.toString(httpresponse.getEntity());
							Log.d("Response: ", responseText);
							if (responseText != null) { // if image URL present (normally it's
														// always present)
								JSONObject jsonResp = new JSONObject(responseText);
								imgURL = jsonResp.getJSONObject("data").getString("url");
							}

						} else {
							imgURL = like.getImage();
						}
						Poll poll = new Poll();
						long date = new Date().getTime();
						poll.setId(String.valueOf(date));
						poll.setCreatedAt(date);
						poll.setUpdatedAt(date);
						poll.setTheme(like.getName());
						poll.setImage(imgURL);
						poll.setCategory(like.getCategory());
						Trend trend = new Trend();
						trend.setTrendNews(new ArrayList<TrendNews>());
						poll.setTrend(trend);
						polls.add(new ParcelablePoll(poll));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return polls;
			}

			@Override
			protected void onPostExecute(List<ParcelablePoll> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					serviceCallback.onServiceDone(result);
				}
			}

		};

		new AsyncTask<Trend, Void, List<Like>>() {

			@Override
			protected List<Like> doInBackground(Trend... trends) {

				try {
					List<String> trendNames = new ArrayList<String>();
					for (Trend trend : trends) {

						if (!trendNames.contains(trend.getName())) {
							trendNames.add(trend.getName());
							String request = "https://graph.facebook.com/v1.0/search?q="
									+ URLEncoder.encode(trend.getName(), AppController.UTF_8)
									+ "&type=page&limit=1&access_token=" + accessToken;
							HttpGet httpgetreq = new HttpGet(request);
							httpgetreq.setHeader("Content-type", "application/json");
							String responseText = null;
							HttpResponse httpresponse = httpclient.execute(httpgetreq);
							responseText = EntityUtils.toString(httpresponse.getEntity());
							Log.d("Response: ", responseText);
							if (responseText != null && !"{\"data\":[]}".equals(responseText)) {// if
								// Like

								JSONArray jsonArray = new JSONObject(responseText)
										.getJSONArray("data");
								JSONObject jsonLike = jsonArray.getJSONObject(0);
								Like like = ObjectMapper.mapLike(jsonLike);
								likes.add(like);
							} else { // fake like (dirty hack)
								Like like = new Like();
								like.setImage(AppController.NO_IMAGE_URL);
								Category category = new Category();
								category.setName(AppController.NO_CATEGORY_TAG);
								like.setCategory(category);
								like.setName(trend.getName());
								likes.add(like);
							}
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return likes;
			}

			@Override
			protected void onPostExecute(List<Like> likes) {
				super.onPostExecute(likes);
				if (likes.isEmpty()) {
					serviceCallback.onServiceDone(null);
				} else {
					getPollsTask.execute(likes.toArray(new Like[likes.size()]));
				}
			}
		}.execute(trendSubList.toArray(new Trend[trendSubList.size()]));

	}

	@Override
	public void createGPolls(final List<Trend> trends, int start, int end,
			final ServiceCallback serviceCallback) {
		List<Trend> trendSubList = trends.subList(start, end);
		final DefaultHttpClient httpclient = new DefaultHttpClient();

		new AsyncTask<Trend, Void, List<ParcelablePoll>>() {

			@Override
			protected List<ParcelablePoll> doInBackground(Trend... arg0) {
				List<ParcelablePoll> polls = new ArrayList<ParcelablePoll>();

				for (Trend trend : trends) {
					String request;
					try {
						request = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
								+ URLEncoder.encode(trend.getName(), AppController.UTF_8);
						HttpGet httpgetreq = new HttpGet(request);
						httpgetreq.setHeader("Content-type", "application/json");
						String responseText = null;
						HttpResponse httpresponse = httpclient.execute(httpgetreq);
						responseText = EntityUtils.toString(httpresponse.getEntity());
						Log.d("Response: ", responseText);
						JSONObject jsonResponse = new JSONObject(responseText);
						JSONObject responseData = jsonResponse.isNull("responseData") ? null
								: jsonResponse.getJSONObject("responseData");
						if (responseData != null) {
							String imgURL = responseData.getJSONArray("results")
									.getJSONObject(0).getString("url");
							Poll poll = new Poll();
							long date = new Date().getTime();
							poll.setId(String.valueOf(date));
							poll.setCreatedAt(date);
							poll.setUpdatedAt(date);
							poll.setTheme(trend.getName());
							poll.setImage(imgURL);
							poll.setCategory(new Category());
							trend.setTrendNews(new ArrayList<TrendNews>());
							poll.setTrend(trend);
							polls.add(new ParcelablePoll(poll));
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
				return polls;
			}

			@Override
			protected void onPostExecute(List<ParcelablePoll> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					serviceCallback.onServiceDone(result);
				}
			}

		}.execute(trendSubList.toArray(new Trend[trendSubList.size()]));

	}
}
