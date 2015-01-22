/**
 * 
 */
package com.pipoll.data;

import java.util.List;

/**
 * @author moderngox
 * 
 *         A poll created by the system and potentially voted by an user
 */
public class Poll {

	private String id, theme, image;
	private int upvotes, downvotes;
	private List<Trend> trendNews; // recent and relevant trends about the theme
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public List<Trend> getTrendNews() {
		return trendNews;
	}

	public void setTrendNews(List<Trend> trendNews) {
		this.trendNews = trendNews;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
