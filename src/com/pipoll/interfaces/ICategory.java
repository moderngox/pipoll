/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.interfaces.callback.ServiceCallback;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Category and @FBCategory
 */
public interface ICategory {
	/**
	 * Get the app server categories
	 * 
	 * @param taskCallBack
	 * @return
	 */
	List<Category> getAppCategories(final TaskCallback taskCallBack);

	/**
	 * Get a consistent list of FB categories
	 * 
	 * @param session
	 * @return
	 */
	List<FBCategory> getFBCategories(final Session session);

	/**
	 * Get a FB category from a query
	 * 
	 * @param session
	 * @param query
	 * @return
	 */
	void getFBStringCategory(final String query, final ServiceCallback serviceCallback);

	/**
	 * Create a category into the app server
	 * 
	 * @param category
	 * @param taskCallBack
	 * @return a logical ID
	 */
	String createCategory(final Category category, final TaskCallback taskCallBack);

	/**
	 * Get a app category
	 * 
	 * @param categoryID
	 * @param taskCallBack
	 * @return
	 */
	Category getCategory(final String categoryID, final TaskCallback taskCallBack);
}
