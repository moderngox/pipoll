/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.pipoll.data.RSSFeed;
import com.pipoll.data.RSSNode;
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

	/***
	 * Read @RSSFeed and returns @RSSNode
	 * 
	 * @param rssFeeds
	 * @param taskcallback
	 * @return
	 */
	List<RSSNode> getRSSNode(List<RSSFeed> rssFeeds, final TaskCallback taskcallback);

	/**
	 * 
	 * @param rssFeeds
	 * @param taskcallback
	 * @return
	 */
	List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> getBackendNodes(
			List<RSSFeed> rssFeeds, final TaskCallback taskcallback);
}
