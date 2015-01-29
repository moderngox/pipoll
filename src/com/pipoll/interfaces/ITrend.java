/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.pipoll.data.Like;
import com.pipoll.data.Trend;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Trend
 */
public interface ITrend {

	List<Trend> getTrends();

	List<Trend> getTrends4Like(Like userLike);
}
