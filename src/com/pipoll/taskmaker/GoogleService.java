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

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.gson.Gson;
import com.pipoll.app.AppController;
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
			protected void onPostExecute(List<GoogleResult> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					taskCallback.onSuccess();
				}
			}

			@Override
			protected List<GoogleResult> doInBackground(Void... params) {
				for (int i = 0; i < 20; i = i + 4) {
					String address = AppController.GOOGLE_ENDPOINT + i + "&q=";

					String charset = AppController.UTF_8;

					URL url;
					try {
						url = new URL(address + URLEncoder.encode(query, charset));
						Reader reader = new InputStreamReader(url.openStream(), charset);
						GoogleResult results = new Gson().fromJson(reader, GoogleResult.class);
						googleResults.add(results);

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return googleResults;
			}
		}.execute();

		return googleResults;
	}

	@Override
	public List<Result> getSearchResult(String keyword) {
		// Set up the HTTP transport and JSON factory
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		// HttpRequestInitializer initializer = (HttpRequestInitializer)new
		// CommonGoogleClientRequestInitializer(API_KEY);
		Customsearch customsearch = new Customsearch(httpTransport, jsonFactory, null);

		List<Result> resultList = null;
		try {
			Customsearch.Cse.List list = customsearch.cse().list(keyword);
			list.setKey(AppController.GCS_API_KEY);
			list.setCx("");
			// num results per page
			// list.setNum(2L);

			// for pagination
			list.setStart(10L);
			// Search results = list.execute();
			// resultList = results.getItems();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

}
