/**
 * 
 */
package com.pipoll.taskmaker;

import java.util.List;

import com.pipoll.data.Like;
import com.pipoll.data.Poll;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.IPoll;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @Poll
 */
public class PollTaskMaker implements IPoll {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.IPoll#createPoll(com.pipoll.data.Like, java.util.List)
	 */
	@Override
	public Poll createPoll(Like chosenLike, List<Trend> trends4like) {
		// TODO Auto-generated method stub
		return null;
	}

}
