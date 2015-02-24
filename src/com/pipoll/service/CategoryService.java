/**
 * 
 */
package com.pipoll.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.pipoll.app.AppController;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.interfaces.ICategory;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.utils.ObjectMapper;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to
 * @Category/ @FBCategory
 * 
 */
public class CategoryService implements ICategory {

	@SuppressWarnings("unused")
	private Activity activity;

	public CategoryService() {

	}

	public CategoryService(Activity activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pipoll.interfaces.ICategory#getAppCategories(com.pipoll.interfaces.TaskCallback)
	 */
	@Override
	public List<Category> getAppCategories(final TaskCallback taskCallBack) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ICategory#getFBCategories(com.facebook.Session)
	 */
	@Override
	public List<FBCategory> getFBCategories(final Session session) {
		final List<FBCategory> categories = new ArrayList<FBCategory>();
		RequestAsyncTask task = null;
		String q;
		for (int j = 0; j < 26; j++) {
			char twoChars[] = new char[2];
			twoChars[0] = (char) (97 + j);
			q = new String(twoChars);
			Bundle reqParam = new Bundle();
			reqParam.putString("q", q);
			reqParam.putString("type", "page");
			reqParam.putInt("limit", 100);
			Request.Callback callback = new Request.Callback() {

				@Override
				public void onCompleted(Response response) {
					JSONArray data;
					try {
						data = new JSONObject(response.getRawResponse()).getJSONArray("data");
						for (int r = 0; r < data.length(); r++) {
							JSONObject jsonCat = data.getJSONObject(r);
							FBCategory category = ObjectMapper.mapFBCategory(jsonCat);
							if (!categories.contains(category)) {
								categories.add(category);
							}

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			};
			Request request = new Request(session, "search", reqParam, HttpMethod.GET,
					callback);
			task = new RequestAsyncTask(request);

		}

		task.execute();
		return categories;
	}

	@Override
	public String createCategory(Category category, TaskCallback taskCallBack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategory(String categoryID, TaskCallback taskCallBack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getFBStringCategory(String query, final ServiceCallback serviceCallback) {
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... queries) {
				String category = null;
				String request;
				try {
					request = "https://graph.facebook.com/v1.0/search?q="
							+ URLEncoder.encode(queries[0], AppController.UTF_8)
							+ "&type=page&limit=1&access_token="
							+ Session.getActiveSession().getAccessToken();
					HttpGet httpgetreq = new HttpGet(request);
					httpgetreq.setHeader("Content-type", "application/json");
					String responseText = null;
					HttpResponse httpresponse = httpclient.execute(httpgetreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					if (responseText != null && !"{\"data\":[]}".equals(responseText)) {// if
						// Like

						JSONArray jsonArray = new JSONObject(responseText)
								.getJSONArray("data");
						JSONObject jsonLike = jsonArray.getJSONObject(0);
						category = jsonLike.getString(AppController.CATEGORY_TAG);
					} else {
						category = AppController.NO_CATEGORY_TAG;
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

				return category;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				serviceCallback.onServiceDone(result);
			}
		}.execute(query);
	}

}
