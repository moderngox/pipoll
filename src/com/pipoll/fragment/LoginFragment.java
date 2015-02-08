/**
 * 
 */
package com.pipoll.fragment;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.pipoll.R;
import com.pipoll.activity.StartupActivity;
import com.pipoll.app.AppController;

/**
 * @author moderngox
 * 
 * 
 */
public class LoginFragment extends Fragment {

	private static final String TAG = "LoginFragment";
	private UiLifecycleHelper uiHelper;
	private AppController application = AppController.getInstance();
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;

		Session session = Session.getActiveSession();
		if (session == null || session.getState().equals(SessionState.CREATED)) {
			view = inflater.inflate(R.layout.login, container, false);
			LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
			authButton.setFragment(this);
			authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Session.openActiveSession(getActivity(), true,
					Arrays.asList("user_likes", "user_status"), callback);
			getActivity().getActionBar().hide();
			application.goToActivity(getActivity(), StartupActivity.class, null, true);
		}
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

		application.goToActivity(getActivity(), StartupActivity.class, null, true);

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
