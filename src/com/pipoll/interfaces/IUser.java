/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.User;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @User data
 */
public interface IUser {
	/**
	 * get a user from the app server thanks to its sharedpreferences userID
	 * 
	 * @param userID
	 * @return
	 */
	User getUser(String userID);

	/**
	 * get a user FB likes
	 * 
	 * @param fbSession
	 * @param taskCallback
	 * @return
	 */
	List<Like> getUserLikes(Session fbSession, TaskCallback taskCallback);

	/**
	 * Get user polls from the app server
	 * 
	 * @param userID
	 * @return
	 */
	List<Poll> getUserPolls(String userID);

}
