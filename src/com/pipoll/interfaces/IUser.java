/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.User;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @User data
 */
public interface IUser {

	User getUser(String userID);

	List<Like> getUserLikes(Session fbSession, TaskCallback taskCallback);

	List<Poll> getUserPolls(String userID);

}
