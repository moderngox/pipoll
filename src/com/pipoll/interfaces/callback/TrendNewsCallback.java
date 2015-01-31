package com.pipoll.interfaces.callback;

import java.util.List;

import com.pipoll.data.TrendNews;

/**
 * @author moderngox
 * 
 */
public interface TrendNewsCallback {

	public void onNewsRetrieved(List<TrendNews> trendsnews);
}
