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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.pipoll.app.AppController;
import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.data.google.ResponseData;
import com.pipoll.data.google.Result;
import com.pipoll.interfaces.ITrend;
import com.pipoll.interfaces.TaskCallback;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @Trend
 * 
 */
public class TrendService implements ITrend {

	@SuppressWarnings("unused")
	private Activity activity;

	public TrendService(Activity activity) {
		this.activity = activity;
	}

	public TrendService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ITrend#getTrends(com.pipoll.interfaces.TaskCallback)
	 */
	@Override
	public List<Trend> getTrends(final TaskCallback taskcallback) {
		final List<Trend> trends = new ArrayList<Trend>();
		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... arg0) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				try {
					String request = AppController.TRENDS_ENDPOINT;
					HttpGet httpgetreq = new HttpGet(request);
					httpgetreq.setHeader("Content-type", "application/json");
					String responseText = null;
					HttpResponse httpresponse = httpclient.execute(httpgetreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);

					if (!responseText.isEmpty()) {
						JSONObject jsonResponse = new JSONObject(responseText);
						Iterator<String> keysIt = jsonResponse.keys();
						List<String> keysList = IteratorUtils.toList(keysIt);
						for (String key : keysList) {
							JSONArray jsonArray = jsonResponse.getJSONArray(key);
							for (int i = 0; i < jsonArray.length(); i++) {
								Trend trend = new Trend();
								trend.setTrendname(jsonArray.getString(i));
								trends.add(trend);
							}
						}

					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				httpclient.getConnectionManager().shutdown();
				return trends;
			}

			@Override
			protected void onPostExecute(List<Trend> result) {
				super.onPostExecute(result);
				if (!trends.isEmpty()) {
					taskcallback.onSuccess();
				}
			}

		}.execute();
		return trends;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ITrend#getTrends4Like(com.pipoll.data.Like)
	 */
	@Override
	public List<Trend> getTrends4Like(Like userLike) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNews(List<Trend> trends, final TaskCallback taskcallback) {
		new AsyncTask<Trend[], Void, Void>() {

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if (result == null)
					taskcallback.onSuccess();
			}

			@Override
			protected Void doInBackground(Trend[]... params) {
				for (Trend trend : params[0]) {
					for (int i = 0; i < 20; i = i + 4) {
						String address = AppController.GOOGLE_ENDPOINT + i + "&q=";

						String charset = AppController.UTF_8;

						URL url;
						try {
							url = new URL(address
									+ URLEncoder.encode(trend.getTrendname(), charset));
							Reader reader = new InputStreamReader(url.openStream(), charset);
							GoogleResult googleResult = new Gson().fromJson(reader,
									GoogleResult.class);
							if (googleResult != null) {
								ResponseData responseData = googleResult.getResponseData();
								if (responseData != null) {
									List<Result> results = responseData.getResults();
									for (Result result : results) {
										trend.getTrendNews().add(result.getUrl());
									}
								}
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return null;
			}
		}.execute(trends.toArray(new Trend[trends.size()]));

	}

}
