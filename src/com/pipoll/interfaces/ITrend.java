/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.pipoll.data.Like;
import com.pipoll.data.RSSElement;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Trend
 */
public interface ITrend {
	/**
	 * Get Hawttrend ==> Wordlwide and unfiltered trends
	 * 
	 * @param taskcallback
	 * @return
	 */
	List<Trend> getHawtTrends(final TaskCallback taskcallback);

	@Deprecated
	List<Trend> getTrends4Like(final Like userLike);

	/**
	 * Get daily top @Trend by location and the chosen date
	 * 
	 * @param location
	 * @param language
	 * @param dateUSFormat
	 * @param taskcallback
	 * @return
	 */
	List<Trend> getDailyTopTrends(final String location, final String language,
			final String dateUSFormat, final TaskCallback taskcallback);

	/**
	 * Get the top @Trend from the last 30 days for a location
	 * 
	 * @param location
	 * @param language
	 * @param taskcallback
	 * @return
	 */
	List<Trend> getMonthlyTopTrends(final String location, final String language,
			final TaskCallback taskcallback);

	/**
	 * Get the last 1-2 hours @Trend by location
	 * 
	 * @param location
	 * @param language
	 * @param taskcallback
	 * @return
	 */
	List<Trend> getHourlyTrends(final String location, final String language,
			final TaskCallback taskcallback);

	@Deprecated
	void setNews(final List<Trend> trends, final TaskCallback taskcallback);

	/***
	 * Read rss Urls and returns rss nodes
	 * 
	 * @param rssUrls
	 * @return
	 */
	List<RSSElement> getRSSNode(List<String> rssUrls);
}
