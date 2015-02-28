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

	private String category;

	private String feedEntry;

	public RSSFeed(String category, String feedEntry) {
		this.category = category;
		this.feedEntry = feedEntry;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFeedEntry() {
		return feedEntry;
	}

	public void setFeedEntry(String feedEntries) {
		this.feedEntry = feedEntries;
	}
}
