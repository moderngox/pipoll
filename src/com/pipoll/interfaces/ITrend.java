/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Trend
 */
public interface ITrend {

	List<Trend> getTrends(final TaskCallback taskcallback);

	List<Trend> getTrends4Like(final Like userLike);

	@Deprecated
	void setNews(final List<Trend> trends, final TaskCallback taskcallback);
}
