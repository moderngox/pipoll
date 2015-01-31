/**
 * 
 */
package com.pipoll.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

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

	// Endpoints
	public static final String TRENDS_ENDPOINT = "http://hawttrends.appspot.com/api/terms/";
	public static final String GOOGLE_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start=";
	// Keys
	public static final String GCS_API_KEY = "AIzaSyB0H_CHXE6ZrpMstY4jGSVcLytDr9TfGN0";
	public static final String SEARCH_ENGINE_ID = "008262435911699479698:kytdbffa034";
	public static String FB_ACCESS_TOKEN = "CAAEyKSUKgk8BAMouzKIw3lo02NTsxZAzZAuRddtbiACvgK9otRcRBm9lfZAS5a5JrOkuZA4ZB4GCKZCys57RTtBkAmJ5ZA2BWvhem4f4eQn06VAHVnuuqZA3sekVZCjqwLf9A5mt1UCHfJYZCFpYmbPduZAoPmijcgTWPulfSq6M3CXcBETroTALm8LTDktkp8sLsJhYctZAsQYJzeZCOMwSxh8sqn6F2XVzbRekZD";
	// CONSTANT
	public static final String UTF_8 = "UTF-8";

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance() {
		return mInstance;

	}

	public void goToActivity(Activity srcActivity, Class<?> destActivity, Bundle bundle,
			boolean isToFinished) {
		Intent intent = new Intent(srcActivity, destActivity);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		srcActivity.startActivity(intent);
		if (isToFinished) {
			srcActivity.finish();
		}

	}
}
