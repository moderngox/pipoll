package com.pipoll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pipoll.R;

public class WebFragment extends Fragment {

	TextView mTextView;

	public static WebFragment newInstance() {
		Bundle args = new Bundle();

		WebFragment fragment = new WebFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_web, parent, false);

		WebView webView = (WebView) v.findViewById(R.id.webViewMain);
		
		
		webView.setWebViewClient(new WebViewClient());
		
		//webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.google.com");
		
		
		return v;
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
}
