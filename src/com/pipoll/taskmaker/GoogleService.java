/**
 * 
 */
package com.pipoll.taskmaker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.IGoogle;
import com.pipoll.interfaces.TaskCallback;

/**
 * @author moderngox
 * 
 */
public class GoogleService implements IGoogle {

	@SuppressWarnings("unused")
	private Activity activity;
	private TaskCallback mCallback;

	public void setCallBack(TaskCallback taskCallback) {
		mCallback = taskCallback;
	}

	public GoogleService(Activity activity) {
		this.activity = activity;
	}

	public GoogleService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.IGoogle#googleSearch(java.lang.String)
	 */
	@Override
	public List<GoogleResult> googleSearch(final String query, final TaskCallback taskCallback) {
		final List<GoogleResult> googleResults = new ArrayList<GoogleResult>();

		new AsyncTask<Void, Void, List<GoogleResult>>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mCallback = taskCallback;
			}

			@Override
			protected void onPostExecute(List<GoogleResult> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					mCallback.onSuccess();
				}
			}

			@Override
			protected List<GoogleResult> doInBackground(Void... params) {
				for (int i = 0; i < 20; i = i + 4) {
					String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="
							+ i + "&q=";

					String charset = "UTF-8";

					URL url;
					try {
						url = new URL(address + URLEncoder.encode(query, charset));
						Reader reader = new InputStreamReader(url.openStream(), charset);
						GoogleResult results = new Gson().fromJson(reader, GoogleResult.class);
						googleResults.add(results);
						// Show title and URL of each results
						for (int m = 0; m <= 3; m++) {
							System.out
									.println("Title: "
											+ results.getResponseData().getResults().get(m)
													.getTitle());
							System.out.println("URL: "
									+ results.getResponseData().getResults().get(m).getUrl()
									+ "\n");
						}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return googleResults;
			}
		}.execute();

		return googleResults;
	}

}
