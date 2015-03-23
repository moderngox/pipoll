/**
 * 
 */
package com.pipoll.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pipoll.app.AppController;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.RSSNode;
import com.pipoll.data.Trend;
import com.pipoll.data.TrendNews;
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

	public static RSSNode mapRSSNode(com.pipoll.entity.rssnodeendpoint.model.RSSNode rssNode) {

		RSSNode result = new RSSNode(rssNode.getTitle(), rssNode.getDescription(),
				rssNode.getLink(), rssNode.getCategory());

		return result;
	}

	public static Poll mapPoll(com.pipoll.entity.pollendpoint.model.Poll bePoll) {
		Poll poll = new Poll();
		poll.setId(bePoll.getId());
		poll.setDownvotes(bePoll.getDownvotes());
		poll.setUpvotes(bePoll.getUpvotes());
		poll.setTheme(bePoll.getTheme());
		poll.setCreatedAt(bePoll.getCreatedAt());
		poll.setUpdatedAt(bePoll.getUpdatedAt());
		Category cat = new Category();
		if (bePoll.getCategory() != null) {
			cat.setName(bePoll.getCategory().getName());
			cat.setId(String.valueOf(bePoll.getCategory().getId()));
			poll.setCategory(cat);
		}
		poll.setImage(bePoll.getImage());
		TrendNews trendNews = new TrendNews();
		if (bePoll.getTrendNews() != null && bePoll.getTrendNews().get(0) != null) {
			trendNews.setTitle(bePoll.getTrendNews().get(0).getTitle());
			trendNews.setUrl(bePoll.getTrendNews().get(0).getUrl());
		}
		List<TrendNews> listTrendNews = new ArrayList<TrendNews>();
		listTrendNews.add(trendNews);
		poll.setTrendNews(listTrendNews);

		return poll;
	}

	public static com.pipoll.entity.rssnodeendpoint.model.RSSNode mapRSSNode(RSSNode rssNode) {

		com.pipoll.entity.rssnodeendpoint.model.RSSNode beRSSNode = new com.pipoll.entity.rssnodeendpoint.model.RSSNode();
		beRSSNode.setDescription(rssNode.getDescription());
		beRSSNode.setTitle(rssNode.getTitle());
		beRSSNode.setTopic(rssNode.getTopic());
		beRSSNode.setLink(rssNode.getLink());
		beRSSNode.setCategory(rssNode.getCategory());

		return beRSSNode;
	}
}
