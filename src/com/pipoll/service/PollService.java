/**
 * 
 */
package com.pipoll.service;

import java.util.ArrayList;
import java.util.List;

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
import com.pipoll.interfaces.GetImgCallback;
import com.pipoll.interfaces.IPoll;
import com.pipoll.interfaces.TaskCallback;
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
	public Poll createPoll(final Like userLike, final List<Trend> trends4like) {
		// TODO create a poll if a match exists between the userLike and the list of trends
		// (trends4like)
		Poll poll = new Poll();
		// String userLikename = userLike.getName();
		// for (Trend trend : trends4like) {
		//
		// }
		return poll;
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
			final TaskCallback taskCallback) {
		final List<Like> likes = new ArrayList<Like>();
		String likeRequest = "search";
		Bundle params = new Bundle();
		params.putString("q", query);
		Request.Callback callback = new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				try {
					JSONArray jsonLikes = new JSONObject(response.getRawResponse())
							.getJSONArray("data");

					JSONObject jsonLike = jsonLikes.getJSONObject(0);
					likes.add(ObjectMapper.mapLike(jsonLike));

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Parsing error. Please retry", Toast.LENGTH_SHORT)
							.show();
				}
				if (taskCallback != null && !likes.isEmpty()) {
					taskCallback.onSuccess();
				}
			}

		};
		Request request = new Request(fbSession, likeRequest, params, HttpMethod.GET, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return !likes.isEmpty() ? likes.get(0) : null;
	}
}
