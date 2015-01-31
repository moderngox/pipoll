/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Category and @FBCategory
 */
public interface ICategory {

	List<Category> getAppCategories(final TaskCallback taskCallBack);

	List<FBCategory> getFBCategories(final Session session);

	String createCategory(final Category category, final TaskCallback taskCallBack); // return
																						// logical
																						// ID

	Category getCategory(final String categoryID, final TaskCallback taskCallBack);
}
