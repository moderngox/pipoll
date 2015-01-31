/**
 * 
 */
package com.pipoll.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.pipoll.app.AppController;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.data.User;

/**
 * @author moderngox
 * 
 *         This class contains all the necessary object mapping
 */
public class ObjectMapper {

	public static User mapUser(JSONObject jsonUser) {
		User user = new User();

		return user;
	}

	public static Poll mapPoll(JSONObject jsonPoll) {
		Poll poll = new Poll();

		return poll;
	}

	public static Category mapCategory(JSONObject jsonCategory) {

		Category category = new Category();

		return category;
	}

	public static FBCategory mapFBCategory(JSONObject jsonFBCategory) {

		FBCategory fbCategory = new FBCategory();

		return fbCategory;
	}

	public static Trend mapTrend(JSONObject jsonTrend) {

		Trend trend = new Trend();

		return trend;
	}

	public static Like mapLike(JSONObject jsonLike) throws JSONException {

		Like like = new Like();
		like.setId(jsonLike.getString(AppController.ID_TAG));
		Category category = new Category();
		category.setName(jsonLike.getString(AppController.CATEGORY_TAG));
		like.setCategory(category);
		like.setName(jsonLike.getString(AppController.NAME_TAG));

		return like;
	}
}
