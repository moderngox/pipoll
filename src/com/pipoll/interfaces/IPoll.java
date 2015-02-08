/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.callback.GetImgCallback;
import com.pipoll.interfaces.callback.ServiceCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Poll
 * 
 */
public interface IPoll {

	void createPoll(final Like userLike, final List<Trend> trends4like,
			ServiceCallback serviceCallback);

	void createPollFromTrend(final Trend trend, final ServiceCallback serviceCallback);

	String getLikeAvatar(final Session fbSession, final String likeID,
			final GetImgCallback getImgCallback);

	Like getLike(final Session fbSession, final String query,
			final ServiceCallback serviceCallback);

	@Deprecated
	void asyncCreatePolls(final List<Trend> trends, final int pollNb,
			final ServiceCallback serviceCallback);

	void createPolls(final List<Trend> trends, final int start, final int end,
			final ServiceCallback serviceCallback);
}
