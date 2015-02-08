/**
 * 
 */
package com.pipoll.data;

import java.io.Serializable;

/**
 * @author moderngox
 * 
 *         An object for a @Trend which consists in a title, and a URL
 */
public class TrendNews implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title, url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
