package com.pipoll.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pipoll.R;

public class WebFragment extends Fragment {

	public static final String TAG = "WebFragment";
	public static final String EXTRA_URL = "WebFragment.URL";

	private String mUrl;
	private WebView mWebView;
	private ProgressBar mProgressBar;

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
		View v = inflater.inflate(R.layout.fragment_web, parent, false);

		mWebView = (WebView) v.findViewById(R.id.webViewMain);
		mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		mProgressBar.setMax(100);

		// restore if a rotation occurs for example
		if (savedInstanceState != null) {
			// Toast.makeText(getActivity(), "savedInstanceState != null", Toast.LENGTH_SHORT).show();
			mWebView.restoreState(savedInstanceState);
		} else {
			// Toast.makeText(getActivity(), "savedInstanceState == null", Toast.LENGTH_SHORT).show();
			startWebView(mUrl);
		}

		return v;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void startWebView(String url) {
		// Create new webview Client to show progress dialog
		// When opening a url or click on link

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// url links open in webView
				// view.loadUrl(url);
				// return true;
				return false;
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					mProgressBar.setVisibility(View.GONE);
				} else {
					mProgressBar.setVisibility(View.VISIBLE);
					mProgressBar.setProgress(newProgress);
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// super.onReceivedTitle(view, title);
				getActivity().getActionBar().setTitle(title);
			}
		});

		// mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// Javascript not enabled for performance issue
		// mWebView.getSettings().setJavaScriptEnabled(true);
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
		// Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPause() {
		super.onPause();
		// Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		mWebView.saveState(outState);
		// Toast.makeText(getActivity(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();
	}
}
