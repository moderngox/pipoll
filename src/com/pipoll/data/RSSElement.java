/**
 * 
 */
package com.pipoll.data;

/**
 * 
 * @author moderngox
 * 
 *         An element related to a RSS feed node
 * 
 */
public class RSSElement {
	private String title;
	private String description;
	private String link;

	public RSSElement(String title, String description, String link) {
		this.title = title;
		this.description = description;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}
}
