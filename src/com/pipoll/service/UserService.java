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
import com.pipoll.data.User;
import com.pipoll.interfaces.IUser;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.utils.ObjectMapper;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @User
 */
public class UserService implements IUser {

	private Activity activity;

	public UserService(Activity activity) {
		this.activity = activity;
	}

	@Override
	public User getUser(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Like> getUserLikes(final Session fbSession, final TaskCallback taskCallback) {
		final List<Like> userLikes = new ArrayList<Like>();
		Bundle param = new Bundle();
		param.putInt("limit", 1000);
		Request.Callback callback = new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				try {
					JSONArray jsonLikes = new JSONObject(response.getRawResponse())
							.getJSONArray("data");
					for (int i = 0; i < jsonLikes.length(); i++) {

						JSONObject jsonLike = jsonLikes.getJSONObject(i);
						final Like userLike = ObjectMapper.mapLike(jsonLike);
						userLikes.add(userLike);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Parsing error. Please retry", Toast.LENGTH_SHORT)
							.show();
				}
				if (taskCallback != null && !userLikes.isEmpty()) {
					taskCallback.onSuccess();
				}
			}

		};
		Request request = new Request(fbSession, "me/likes", param, HttpMethod.GET, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return userLikes;
	}

	@Override
	public List<Poll> getUserPolls(final String userID) {
		// TODO Auto-generated method stub
		return null;
	}

}
