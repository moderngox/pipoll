/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Poll
 * 
 */
public interface IPoll {

	Poll createPoll(final Like userLike, final List<Trend> trends4like);

	String getLikeAvatar(final Session fbSession, final String likeID,
			final GetImgCallback getImgCallback);

	Like getLike(final Session fbSession, final String query, final TaskCallback taskCallback);
}
