/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.google.api.services.customsearch.model.Result;
import com.pipoll.data.Trend;
import com.pipoll.data.TrendNews;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.interfaces.callback.TrendNewsCallback;

/**
 * @author moderngox
 * 
 */
public interface IGoogle {

	@Deprecated
	List<GoogleResult> googleSearch(final String query, final TaskCallback taskCallback);

	@Deprecated
	List<Result> getSearchResult(final String keyword, final TaskCallback taskCallback);

	void fillTrendWithNews(final List<Trend> trends, final TaskCallback taskCallback);

	List<TrendNews> getDataFromGoogle(final String query,
			final TrendNewsCallback trendNewsCallback);

	List<String> getDataFromGoogleNews(final String query, final TaskCallback taskCallback);
}
