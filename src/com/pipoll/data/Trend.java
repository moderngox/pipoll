/**
 * 
 */
package com.pipoll.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moderngox
 * 
 *         allow us to be up to date about what's hype and trendy
 * 
 */
public class Trend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id, name, image;
	private List<TrendNews> trendNews = new ArrayList<TrendNews>(); // relevant and recent
																	// articles
	// about the trend
	private Category category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<TrendNews> getTrendNews() {
		return trendNews;
	}

	public void setTrendNews(List<TrendNews> trendNews) {
		this.trendNews = trendNews;
	}
}
