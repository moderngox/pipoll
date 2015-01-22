/**
 * 
 */
package com.pipoll.interfaces;

import java.util.List;

import com.facebook.Session;
import com.pipoll.data.Category;
import com.pipoll.data.FBCategory;

/**
 * @author moderngox
 * 
 *         Interface gathering methods related to the @Category and @FBCategory
 */
public interface ICategory {

	List<Category> getAppCategories();

	List<FBCategory> getFBCategories(Session session);

	String createCategory(Category category); // return logical ID

	Category getCategory(String categoryID);
}
