/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.pipoll.data.google.GoogleResult;

/**
 * @author moderngox
 * 
 */
public interface IGoogle {

	List<GoogleResult> googleSearch(String query, TaskCallback taskCallback);
}
