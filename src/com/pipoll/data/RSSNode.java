/**
 * 
 */
package com.pipoll.data;

import java.io.Serializable;

import com.pipoll.app.AppController;

/**
 * 
 * @author moderngox
 * 
 *         An element related to a RSS feed node
 * 
 */
public class RSSNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String link;
	private String category;
	private String topic;

	public RSSNode(String title, String description, String link, String category) {
		this.title = title;
		this.description = description;
		this.link = link;
		this.setCategory(category);
		topic = AppController.extractTopic(title);
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

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
}
