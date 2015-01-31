/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.google.api.services.customsearch.model.Result;
import com.pipoll.data.google.GoogleResult;

/**
 * @author moderngox
 * 
 */
public interface IGoogle {

	@Deprecated
	List<GoogleResult> googleSearch(final String query, final TaskCallback taskCallback);

	@Deprecated
	List<Result> getSearchResult(final String keyword, final TaskCallback taskCallback);

	List<String> getDataFromGoogle(final String query, final TaskCallback taskCallback);
}
