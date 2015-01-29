/**
 * 
 */
package com.pipoll.taskmaker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.ITrend;

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
	 * @see com.pipoll.interfaces.ITrend#getTrends()
	 */
	@Override
	public List<Trend> getTrends() {

		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... arg0) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				try {
					String request = "http://hawttrends.appspot.com/api/terms/";
					HttpGet httpgetreq = new HttpGet(request);
					httpgetreq.setHeader("Content-type", "application/json");
					String responseText = null;
					HttpResponse httpresponse = httpclient.execute(httpgetreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					@SuppressWarnings("unused")
					JSONObject jsonResponse = null;
					if (!responseText.isEmpty()) {
						jsonResponse = new JSONObject(responseText);
						// JSONObject jsonPages = jsonResponse.getJSONObject("query")
						// .getJSONObject("pages");

						// JSONObject jsonNext = (JSONObject) new JSONTokener(
						// jsonPages.toString()).nextValue();
						// JSONObject jsonImgInfo = jsonNext.getJSONArray("imageinfo")
						// .getJSONObject(0);
						// urlResult.add(jsonImgInfo.getString("url"));

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
				return null;
			}

		}.execute();
		return null;
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

}
