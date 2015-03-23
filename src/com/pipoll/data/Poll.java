/**
 * 
 */
package com.pipoll.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author moderngox
 * 
 *         A poll created by the system and potentially voted by an user
 */
public class Poll implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id, theme, image;
	private int upvotes, downvotes;
	private List<TrendNews> trendNews; // recent and relevant trend about the theme
	private Category category;
	private long createdAt, updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the trendNews
	 */
	public List<TrendNews> getTrendNews() {
		return trendNews;
	}

	/**
	 * @param trendNews
	 *            the trendNews to set
	 */
	public void setTrendNews(List<TrendNews> trendNews) {
		this.trendNews = trendNews;
	}

}
