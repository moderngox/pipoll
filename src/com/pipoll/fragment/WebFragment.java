package com.pipoll.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pipoll.R;

public class WebFragment extends Fragment {

	public static final String TAG = "WebFragment";
	public static final String EXTRA_URL = "WebFragment.URL";

	private String mUrl;
	private WebView mWebView;

	public static WebFragment newInstance(String url) {
		Bundle args = new Bundle();
		args.putString(EXTRA_URL, url);

		WebFragment fragment = new WebFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mUrl = getArguments().getString(EXTRA_URL);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		//Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();

		View v = inflater.inflate(R.layout.fragment_web, parent, false);

		mWebView = (WebView) v.findViewById(R.id.webViewMain);

		// restore if a rotation occurs for example
		if (savedInstanceState != null) {
			//Toast.makeText(getActivity(), "savedInstanceState != null", Toast.LENGTH_SHORT).show();

			mWebView.restoreState(savedInstanceState);
		} else {
			//Toast.makeText(getActivity(), "savedInstanceState == null", Toast.LENGTH_SHORT).show();

			startWebView(mUrl);
		}

		return v;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void startWebView(String url) {
		// Create new webview Client to show progress dialog
		// When opening a url or click on link

		mWebView.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog;

			// url links open open in webView
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				//super.onPageStarted(view, url, favicon);
				if (progressDialog == null) {

					progressDialog = ProgressDialog.show(getActivity(), "title", "loading " + mUrl);
					progressDialog.show();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				try {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog = null;
					}
				} catch (Exception e) {
					Log.e(TAG, "Error in onPageFinished.", e);
				}
			}

		});

		//mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		// Javascript not enabled for performance issue
		//mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings()
				.setUserAgentString(
						"Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
		mWebView.loadUrl(mUrl);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
//		Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPause() {
		super.onPause();
//		Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		mWebView.saveState(outState);

//		Toast.makeText(getActivity(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();
	}
}
