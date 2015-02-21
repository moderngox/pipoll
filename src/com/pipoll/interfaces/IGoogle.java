/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.google.api.services.customsearch.model.Result;
import com.pipoll.data.Poll;
import com.pipoll.data.TrendNews;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.interfaces.callback.TrendNewsCallback;

/**
 * @author moderngox
 * 
 */
public interface IGoogle {

	/**
	 * A Google search with the customSearch API
	 * 
	 * @param keyword
	 * @param taskCallback
	 * @return
	 */
	@Deprecated
	List<Result> getCustomSearchResult(final String keyword, final TaskCallback taskCallback);

	/**
	 * Crawl and get a @TrendNews list from google search via Jsoup
	 * 
	 * @param query
	 * @param trendNewsCallback
	 * @return
	 */
	List<TrendNews> getDataFromGoogle(final String query,
			final TrendNewsCallback trendNewsCallback);

	/**
	 * Crawl and get a @TrendNews list from google news via Jsoup
	 * 
	 * @param query
	 * @param trendNewsCallback
	 * @return
	 */
	List<TrendNews> getDataFromGoogleNews(final String query,
			final TrendNewsCallback trendNewsCallback);

	/**
	 * Crawl an image list from google images and insert the first image into the poll
	 * corresponding to the poll theme
	 * 
	 * @param query
	 * @param poll
	 * @param serviceCallback
	 * @return
	 */
	String setGoogleImage(final Poll poll);
}
