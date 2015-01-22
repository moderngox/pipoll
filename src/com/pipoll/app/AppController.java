/**
 * 
 */
package com.pipoll.app;

import android.app.Application;

/**
 * @author moderngox
 * 
 *         Globals are defined and initialized in this singleton class.
 * 
 */
public class AppController extends Application {

	// TAGS

	// Generic
	public static final String ID_TAG = "id";
	public static final String IMAGE_TAG = "image";
	public static final String NAME_TAG = "name";
	public static final String CREATED_TAG = "createdAt";
	public static final String UPDATED_TAG = "updatedAt";

	// User
	public static final String USERNAME_TAG = "username";

	// Category
	public static final String CATEGORY_ID_TAG = "categoryID";
	public static final String CATEGORY_TAG = "category";
}
