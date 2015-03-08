/**
 * 
 */
package com.pipoll.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.java.frej.fuzzy.Fuzzy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.pipoll.app.AppController;
import com.pipoll.data.Poll;
import com.pipoll.data.TrendNews;
import com.pipoll.interfaces.IGoogle;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.interfaces.callback.TrendNewsCallback;

/**
 * @author moderngox
 * 
 */
public class GoogleService implements IGoogle {

	private Activity activity;
	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}

	public GoogleService(Activity activity) {
		this.activity = activity;
	}

	public GoogleService() {

	}

	@SuppressLint("DefaultLocale")
	public String getDomainName(String url) {

		String domainName = "";
		matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}
		return domainName;

	}

	@Override
	@Deprecated
	public List<Result> getCustomSearchResult(final String keyword,
			final TaskCallback taskCallback) {
		final List<Result> resultList = new ArrayList<Result>();
		new AsyncTask<Void, Void, List<Result>>() {

			@Override
			protected void onPostExecute(List<Result> result) {
				super.onPostExecute(result);
				taskCallback.onSuccess();
			}

			@Override
			protected List<Result> doInBackground(Void... params) {
				// Set up the HTTP transport and JSON factory
				HttpTransport httpTransport = new NetHttpTransport();
				JsonFactory jsonFactory = new JacksonFactory();
				Customsearch customsearch = new Customsearch(httpTransport, jsonFactory, null);

				try {
					Customsearch.Cse.List list = customsearch.cse().list(keyword);
					list.setKey(AppController.GCS_API_KEY);
					list.setCx(AppController.SEARCH_ENGINE_ID);

					// for pagination
					list.setStart(10L);
					Search results = list.execute();
					resultList.addAll(results.getItems());

				} catch (IOException e) {
					e.printStackTrace();
				}

				return resultList;

			}

		}.execute();

		return resultList;
	}

	@Override
	public List<TrendNews> getDataFromGoogle(final String query,
			final TrendNewsCallback trendNewsCallback) {
		final List<TrendNews> trendNews = new LinkedList<TrendNews>();
		new AsyncTask<Void, Void, List<TrendNews>>() {

			@Override
			protected void onPostExecute(List<TrendNews> result) {
				super.onPostExecute(result);
				trendNewsCallback.onNewsRetrieved(result);
			}

			@Override
			protected List<TrendNews> doInBackground(Void... params) {
				try {

					Elements links = Jsoup
							.connect(
									"https://www.google.com/search?q="
											+ URLEncoder.encode(query, AppController.UTF_8))
							.userAgent(
									"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
							.get().select("li.g>h3>a");
					for (Element link : links) {
						TrendNews news = new TrendNews();
						String title = link.text();
						String url = link.absUrl("href"); // Google returns URLs in format
															// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
						url = URLDecoder
								.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')),
										"UTF-8");

						if (!url.startsWith("http")) {
							continue; // Ads/news/etc.
						}
						news.setTitle(title);
						news.setUrl(url);
						trendNews.add(news);

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return trendNews;
			}

		}.execute();
		return new ArrayList<TrendNews>(trendNews);
	}

	@Override
	public List<TrendNews> getDataFromGoogleNews(final String query,
			final TrendNewsCallback trendNewsCallback) {
		final List<TrendNews> result = new LinkedList<TrendNews>();
		new AsyncTask<Void, Void, List<TrendNews>>() {

			private String queryVsTitle;

			@Override
			protected void onPostExecute(List<TrendNews> result) {
				super.onPostExecute(result);
				if (result.isEmpty()) {
					getDataFromGoogle(query, trendNewsCallback);
				} else {
					trendNewsCallback.onNewsRetrieved(result);
				}
			}

			@Override
			protected List<TrendNews> doInBackground(Void... params) {

				try {

					Document document = Jsoup
							.connect(
									AppController.GOOGLE_NEWS_ENDPOINT
											+ URLEncoder.encode(query, AppController.UTF_8))
							.userAgent(
									"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
							.get();
					Elements links = document.select("a[href]");
					for (Element link : links) {
						TrendNews trendnews = new TrendNews();
						String title = link.text();
						String url = link.absUrl("href");
						queryVsTitle = title + " VS " + query;
						try {

							if (url.contains(".google.")
									|| (Fuzzy.substrStart(title, query) == -1 && Fuzzy
											.substrEnd(title, query) == -1)) {
								continue; // Ads/news/etc.
							}
						} catch (Exception e) {
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

									Toast.makeText(activity, queryVsTitle, Toast.LENGTH_SHORT)
											.show();
								}
							});
						}
						trendnews.setTitle(title);
						trendnews.setUrl(url);
						result.add(trendnews);
						if (result.size() == 3) {
							break;
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return result;
			}

		}.execute();
		return new ArrayList<TrendNews>(result);
	}

	@Override
	public String setGoogleImage(final Poll poll) {
		final String imgURL = null;
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... queries) {
				final DefaultHttpClient httpclient = new DefaultHttpClient();
				String request;
				String imgURL = null;
				try {
					request = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
							+ URLEncoder.encode(queries[0], AppController.UTF_8)
							+ "&userip="
							+ InetAddress.getLocalHost().toString();
					HttpGet httpgetreq = new HttpGet(request);
					httpgetreq.setHeader("Content-type", "application/json");
					String responseText = null;
					HttpResponse httpresponse = httpclient.execute(httpgetreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					JSONObject jsonResponse = new JSONObject(responseText);
					JSONObject responseData = jsonResponse.isNull("responseData") ? null
							: jsonResponse.getJSONObject("responseData");
					if (responseData != null) {
						int i = 0;
						JSONArray results = responseData.getJSONArray("results");
						while (i < results.length()
								&& (!imgURL.contains(".jpg") || !imgURL.contains(".JPG")
										|| !imgURL.contains(".png") || !imgURL
											.contains(".PNG"))) {
							imgURL = results.getJSONObject(0).getString("url");
							i++;
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return imgURL;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				poll.setImage(imgURL);
			}

		}.execute(poll.getTheme());
		return imgURL;
	}
}
