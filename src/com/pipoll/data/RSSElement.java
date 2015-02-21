/**
 * 
 */
package com.pipoll.data;

import java.io.Serializable;

/**
 * 
 * @author moderngox
 * 
 *         An element related to a RSS feed node
 * 
 */
public class RSSElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
