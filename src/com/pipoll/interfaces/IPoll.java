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

	Poll createPoll(Like userLike, List<Trend> trends4like);

	String getLikeAvatar(Session fbSession, String likeID, GetImgCallback getImgCallback);

	Like getLike(Session fbSession, String query, TaskCallback taskCallback);
}
