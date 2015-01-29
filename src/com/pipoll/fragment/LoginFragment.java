/**
 * 
 */
package com.pipoll.fragment;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.pipoll.R;
import com.pipoll.data.FBCategory;
import com.pipoll.data.Like;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.taskmaker.CategoryService;
import com.pipoll.taskmaker.UserService;

/**
 * @author moderngox
 * 
 * 
 */
public class LoginFragment extends Fragment {

	private static final String TAG = "LoginFragment";
	private UiLifecycleHelper uiHelper;
	private TextView likes;
	private List<Like> userLikes;
	private List<FBCategory> fbCategories;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	protected String responseText = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		likes = (TextView) view.findViewById(R.id.likes);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		return view;
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		Session session = Session.getActiveSession();

		UserService userTaskMaker = new UserService(getActivity());
		CategoryService catTaskMaker = new CategoryService(getActivity());
		userLikes = userTaskMaker.getUserLikes(session, new TaskCallback() {

			@Override
			public void onSuccess() {

				for (Like userLike : userLikes) {
					responseText += userLike.getName() + " - " + userLike.getCategory()
							+ " - " + userLike.getImage() + "\n";
				}
				likes.setText(responseText);
				likes.setVisibility(View.VISIBLE);

			}

		});
		fbCategories = catTaskMaker.getFBCategories(session);

		// responseText += category + "\n";
		// likes.setText("categories nb: " + categories.size() + "\n"
		// + responseText);

		// likes.setText(jsonLikes.toString());
		// likes.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

}
