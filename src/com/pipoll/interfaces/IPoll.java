/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.RSSElement;
import com.pipoll.data.Trend;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.interfaces.callback.GetImgCallback;
import com.pipoll.interfaces.callback.ServiceCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Poll
 * 
 */
public interface IPoll {

	/**
	 * Create a poll when a FB Like match with a trend from @Trend list
	 * 
	 * @param userLike
	 * @param trends4like
	 * @param serviceCallback
	 */
	void createMatchPoll(final Like userLike, final List<Trend> trends4like,
			ServiceCallback serviceCallback);

	/**
	 * Create a poll from a trend
	 * 
	 * @param trend
	 * @param serviceCallback
	 */
	@Deprecated
	void createPollFromTrend(final Trend trend, final ServiceCallback serviceCallback);

	/**
	 * Facebook async function for getting likeAvatar
	 * 
	 * @param fbSession
	 * @param likeID
	 * @param getImgCallback
	 * @return
	 */
	@Deprecated
	String getLikeAvatar(final Session fbSession, final String likeID,
			final GetImgCallback getImgCallback);

	/**
	 * Facebook async function for getting Like page data
	 * 
	 * @param fbSession
	 * @param query
	 * @param serviceCallback
	 * @return
	 */
	@Deprecated
	Like getLike(final Session fbSession, final String query,
			final ServiceCallback serviceCallback);

	void createPolls(final List<Trend> trends, final int start, final int end,
			final ServiceCallback serviceCallback);

	/**
	 * Create Polls from via Google Image Search API
	 * 
	 * @param trends
	 * @param start
	 * @param end
	 * @param serviceCallback
	 */
	void createGPolls(final List<Trend> trends, final int start, final int end,
			final ServiceCallback serviceCallback);

	/**
	 * Create and return Polls (without loaded an image for it) from a list of Trend
	 * 
	 * @param trends
	 * @param start
	 * @param end
	 * @return
	 */
	List<ParcelablePoll> createPollsFromTrends(List<Trend> trends, int start, int end);

	/**
	 * Create polls from a list of @RSSElement Node
	 * 
	 * @param rssNodes
	 * @param serviceCallback
	 */
	void createPollsFromRssNodes(List<RSSElement> rssNodes,
			final ServiceCallback serviceCallback);
}
