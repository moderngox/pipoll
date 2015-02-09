package com.pipoll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
		return v;
	}
}
