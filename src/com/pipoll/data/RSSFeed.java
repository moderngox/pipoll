/**
 * 
 */
package com.pipoll.data;

/**
 * @author moderngox
 * 
 *         RSS feed which consists in a feed entry for a category
 */
public class RSSFeed {

	private int category;

	private String feedEntry;

	public RSSFeed(int category, String feedEntry) {
		this.category = category;
		this.feedEntry = feedEntry;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getFeedEntry() {
		return feedEntry;
	}

	public void setFeedEntry(String feedEntries) {
		this.feedEntry = feedEntries;
	}
}
