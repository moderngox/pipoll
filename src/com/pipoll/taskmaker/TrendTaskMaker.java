/**
 * 
 */
package com.pipoll.taskmaker;

import java.util.List;

import android.app.Activity;

import com.pipoll.data.Like;
import com.pipoll.data.Trend;
import com.pipoll.interfaces.ITrend;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @Trend
 * 
 */
public class TrendTaskMaker implements ITrend {

	@SuppressWarnings("unused")
	private Activity activity;

	public TrendTaskMaker(Activity activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ITrend#getTrends()
	 */
	@Override
	public List<Trend> getTrends() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ITrend#getTrends4Like(com.pipoll.data.Like)
	 */
	@Override
	public List<Trend> getTrends4Like(Like chosenLike) {
		// TODO Auto-generated method stub
		return null;
	}

}
