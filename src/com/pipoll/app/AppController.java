/**
 * 
 */
package com.pipoll.app;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author moderngox
 * 
 *         Globals are defined and initialized in this singleton class.
 * 
 */
public class AppController extends Application {

	// testing counters
	public static int Register_Count;
	public static int Unregister_Count;

	// TAGS

	private static final String STRING_SPACE = " ";
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

	// Poll(s)
	public static final String POLLS_TAG = "polls";
	public static final String POLL_TAG = "poll";

	// Trends
	public static final String TRENDS_TAG = "trends";
	public static final String TREND_TAG = "trend";
	// RSS elems
	public static final String RSS_ELEMS_TAG = "rssElems";
	// Endpoints
	public static final String TRENDS_ENDPOINT = "http://hawttrends.appspot.com/api/terms/";
	public static final String GOOGLE_ENDPOINT = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start=";
	public static final String GOOGLE_SEARCH_ENDPOINT = "https://www.google.com/search?q=";
	public static final String GOOGLE_NEWS_ENDPOINT = "http://news.google.com/news?q=";
	// Keys
	public static final String GCS_API_KEY = "AIzaSyB0H_CHXE6ZrpMstY4jGSVcLytDr9TfGN0";
	public static final String SEARCH_ENGINE_ID = "008262435911699479698:kytdbffa034";
	public static String FB_ACCESS_TOKEN = "CAAEyKSUKgk8BAMouzKIw3lo02NTsxZAzZAuRddtbiACvgK9otRcRBm9lfZAS5a5JrOkuZA4ZB4GCKZCys57RTtBkAmJ5ZA2BWvhem4f4eQn06VAHVnuuqZA3sekVZCjqwLf9A5mt1UCHfJYZCFpYmbPduZAoPmijcgTWPulfSq6M3CXcBETroTALm8LTDktkp8sLsJhYctZAsQYJzeZCOMwSxh8sqn6F2XVzbRekZD";
	// CONSTANT
	public static final String UTF_8 = "UTF-8";
	public static final String GOOGLE_HOT_TRENDS = "http://www.google.com/trends/hottrends/hotItems";
	public static final String GOOGLE_TOP_CHART_CAT = "http://www.google.com/trends/topcharts/category";
	public static final String NO_IMAGE_URL = "http://www.clker.com/cliparts/B/u/S/l/W/l/no-photo-available-md.png";
	public static final String NO_CATEGORY_TAG = "N/C";

	// RSS
	public static final String REUTERS_FEED = "https://news.google.com/news?hl=fr&gl=fr&q=source:Reuters&um=1&ie=UTF-8&output=rss&num=100";
	public static final String AFP_FEED = "https://news.google.com/news?hl=fr&gl=fr&q=source:AFP&um=1&ie=UTF-8&output=rss&num=100";

	public static final String UPI_FEED = "https://news.google.com/news?hl=fr&gl=fr&q=source:Bloomberg&um=1&ie=UTF-8&output=rss&num=100"; // broke?

	public static final String FASHION_WEEKLY_FEED = "http://fashionweekdaily.com/feed/";
	public static final String SELECTISM_FEED = "http://feeds.feedburner.com/selectism/rss?format=xml";
	public static final String F365_PLEAGUE_FEED = "http://www.football365.com/premier-league/rss";
	public static final String ECONOMIST_BUSINESS_FEED = "http://www.economist.com/sections/business-finance/rss.xml";
	public static final String WSJ_TECH_FEED = "http://online.wsj.com/xml/rss/3_7455.xml";

	private static final String AFP = "AFP";
	private static final String REUTERS = "Reuters";

	public static final String FASHION_CAT = "Fashion";
	public static final String GLOBAL_CAT = "Global news";
	public static final String SPORT_CAT = "Sport";
	public static final String FOOTBALL_CAT = "Football";
	public static final String BUSINESS_CAT = "Business";
	public static final String TECH_CAT = "Tech";

	// SharedPreferences
	public static final String LOCAL_PREFERENCES = "localPreferences";
	// Pick account
	public static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

	private SharedPreferences sharedPreferences;
	private static AppController mInstance;
	private ImageLoader mImageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance() {
		return mInstance;

	}

	public SharedPreferences getSharedPreferences() {
		if (sharedPreferences == null) {
			sharedPreferences = getSharedPreferences(AppController.LOCAL_PREFERENCES,
					Activity.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	public void pickUserAccount(Activity activity) {
		String[] accountTypes = new String[] { "com.google" };
		Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false,
				null, null, null, null);
		activity.startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
	}

	public void clearLocalPolls(Activity activity) {
		getSharedPreferences();

		if (!sharedPreferences.getAll().isEmpty()) {
			sharedPreferences.edit().clear().commit();
			Toast.makeText(activity, "Local Polls cleared", Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isTopicAlreadyPresentToday(String topic, String rawDate) {
		getSharedPreferences();
		boolean result = false;
		if (sharedPreferences.getAll().containsKey(topic)) {
			if (sharedPreferences.getString(topic, null).equals(topic + " - " + rawDate)) {
				result = true;
			}
		}
		return result;
	}

	public ImageLoader getImageLoader() {

		if (mImageLoader == null) {
			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
					.build();
			mImageLoader = ImageLoader.getInstance();
			mImageLoader.init(config);
		}

		return this.mImageLoader;
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

	public static String extractTopic(String title) {
		StringBuilder sb = new StringBuilder("");
		String word = "";
		StringTokenizer st = new StringTokenizer(title, STRING_SPACE);
		while (st.hasMoreElements()) {
			word = st.nextToken();
			if (java.lang.Character.isUpperCase(word.charAt(0))) {
				if (!word.contains(REUTERS) && !word.contains(AFP) && word.length() > 2) {
					sb.append(word).append(STRING_SPACE);
				}
			}
		}

		return sb.toString();

	}
}
