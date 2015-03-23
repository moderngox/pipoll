package com.pipoll.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.pipoll.app.AppController;
import com.pipoll.data.Category;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.data.TrendNews;
import com.pipoll.data.parcelable.ParcelablePoll;

public class PollLooper extends HandlerThread {
	private static final String TAG = "PollFactory";
	private static final int MESSAGE_DOWNLOAD = 0;

	Handler mHandler;
	Map<Trend, Trend> requestMap = Collections.synchronizedMap(new HashMap<Trend, Trend>());
	Handler mResponseHandler;
	Listener mListener;

	public interface Listener {
		void onPollsCreated(Trend handle, ParcelablePoll ppoll);
	}

	public void setListener(Listener listener) {
		mListener = listener;
	}

	public PollLooper(Handler responseHandler) {
		super(TAG);
		mResponseHandler = responseHandler;
	}

	@SuppressLint("HandlerLeak")
	@Override
	protected void onLooperPrepared() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == MESSAGE_DOWNLOAD) {
					@SuppressWarnings("unchecked")
					Trend handle = (Trend) msg.obj;
					Log.i(TAG, "Got a request for url: " + requestMap.get(handle));
					handleRequest(handle);
				}
			}
		};
	}

	private void handleRequest(final Trend handle) {
		final Trend trend = requestMap.get(handle);
		if (trend == null)
			return;

		// long task here
		// byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
		final ParcelablePoll ppoll = createGPoll(trend);

		mResponseHandler.post(new Runnable() {
			public void run() {
				if (requestMap.get(handle) != trend)
					return;

				requestMap.remove(handle);
				mListener.onPollsCreated(handle, ppoll);
			}
		});
	}

	public void queuePoll(Trend handle, Trend trend) {
		requestMap.put(handle, trend);

		mHandler.obtainMessage(MESSAGE_DOWNLOAD, handle).sendToTarget();
	}

	public void clearQueue() {
		mHandler.removeMessages(MESSAGE_DOWNLOAD);
		requestMap.clear();
	}

	public ParcelablePoll createGPoll(Trend trend) {
		ParcelablePoll res = null;

		final DefaultHttpClient httpclient = new DefaultHttpClient();

		String request;
		try {
			request = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
					+ URLEncoder.encode(trend.getName(), AppController.UTF_8) + "&userip="
					+ InetAddress.getLocalHost().toString();
			HttpGet httpgetreq = new HttpGet(request);
			httpgetreq.setHeader("Content-type", "application/json");
			String responseText = null;
			HttpResponse httpresponse = httpclient.execute(httpgetreq);
			responseText = EntityUtils.toString(httpresponse.getEntity());
			Log.d("Response: ", responseText);
			JSONObject jsonResponse = new JSONObject(responseText);
			JSONObject responseData = jsonResponse.isNull("responseData") ? null
					: jsonResponse.getJSONObject("responseData");
			if (responseData != null) {
				String imgURL = responseData.getJSONArray("results").getJSONObject(0)
						.getString("url");
				Poll poll = new Poll();
				long date = new Date().getTime();
				poll.setId(String.valueOf(date));
				poll.setCreatedAt(date);
				poll.setUpdatedAt(date);
				poll.setTheme(trend.getName());
				poll.setImage(imgURL);
				poll.setCategory(new Category());
				poll.setTrendNews(new ArrayList<TrendNews>());

				res = new ParcelablePoll(poll);
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
		return res;
	}

}
