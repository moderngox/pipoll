/**
 * 
 */
package com.pipoll.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moderngox
 * 
 *         allow us to be up to date about what's hype and trendy
 * 
 */
public class Trend {

	private String id, trendname, image;
	private List<String> trendNews = new ArrayList<String>(); // relevant and recent articles
																// about the trend
	private Category category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrendname() {
		return trendname;
	}

	public void setTrendname(String trendname) {
		this.trendname = trendname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getTrendNews() {
		return trendNews;
	}

	public void setTrendNews(List<String> trendNews) {
		this.trendNews = trendNews;
	}
}
