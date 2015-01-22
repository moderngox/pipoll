/**
 * 
 */
package com.pipoll.taskmaker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import com.pipoll.utils.ObjectMapper;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @User
 */
public class UserTaskMaker implements IUser {

	public interface TaskCallback {

		public void onSuccess();
	}

	private TaskCallback mCallback;

	public void setCallBack(TaskCallback taskCallback) {
		mCallback = taskCallback;
	}

	private Activity activity;

	public UserTaskMaker(Activity activity) {
		this.activity = activity;
	}

	@Override
	public User getUser(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Like> getUserLikes(Session fbSession, TaskCallback taskCallback) {
		mCallback = taskCallback;
		final List<Like> userLikes = new ArrayList<Like>();
		Request.Callback callback = new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				try {
					JSONArray jsonLikes = new JSONObject(response.getRawResponse())
							.getJSONArray("data");
					for (int i = 0; i < jsonLikes.length(); i++) {

						JSONObject jsonLike = jsonLikes.getJSONObject(i);
						Like userLike = ObjectMapper.mapLike(jsonLike);
						userLikes.add(userLike);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Parsing error. Please retry", Toast.LENGTH_SHORT)
							.show();
				}
				if (mCallback != null && !userLikes.isEmpty()) {
					mCallback.onSuccess();
				}
			}

		};
		Request request = new Request(fbSession, "me/likes", null, HttpMethod.GET, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return userLikes;
	}

	@Override
	public List<Poll> getUserPolls(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

}
