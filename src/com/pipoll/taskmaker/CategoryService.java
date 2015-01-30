/**
 * 
 */
package com.pipoll.taskmaker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.interfaces.ICategory;
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
	 * @see com.pipoll.interfaces.ICategory#getAppCategories()
	 */
	@Override
	public List<Category> getAppCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ICategory#getFBCategories(com.facebook.Session)
	 */
	@Override
	public List<FBCategory> getFBCategories(Session session) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ICategory#createCategory(com.pipoll.data.Category)
	 */
	@Override
	public String createCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategory(String categoryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
