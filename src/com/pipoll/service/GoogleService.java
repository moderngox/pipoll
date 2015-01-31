/**
 * 
 */
package com.pipoll.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.google.gson.Gson;
import com.pipoll.app.AppController;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.IGoogle;
import com.pipoll.interfaces.TaskCallback;

/**
 * @author moderngox
 * 
 */
public class GoogleService implements IGoogle {

	@SuppressWarnings("unused")
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.IGoogle#googleSearch(java.lang.String)
	 */
	@Override
	@Deprecated
	public List<GoogleResult> googleSearch(final String query, final TaskCallback taskCallback) {
		final List<GoogleResult> googleResults = new ArrayList<GoogleResult>();

		new AsyncTask<Void, Void, List<GoogleResult>>() {

			@Override
			protected void onPostExecute(List<GoogleResult> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					taskCallback.onSuccess();
				}
			}

			@Override
			protected List<GoogleResult> doInBackground(Void... params) {
				for (int i = 0; i < 20; i = i + 4) {
					String address = AppController.GOOGLE_ENDPOINT + i + "&q=";

					String charset = AppController.UTF_8;

					URL url;
					try {
						url = new URL(address + URLEncoder.encode(query, charset));
						Reader reader = new InputStreamReader(url.openStream(), charset);
						GoogleResult results = new Gson().fromJson(reader, GoogleResult.class);
						googleResults.add(results);

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return googleResults;
			}
		}.execute();

		return googleResults;
	}

	@Override
	public List<Result> getSearchResult(final String keyword, final TaskCallback taskCallback) {
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
	public List<String> getDataFromGoogle(final String query, final TaskCallback taskCallback) {
		final List<String> result = new ArrayList<String>();
		new AsyncTask<Void, Void, List<String>>() {

			@Override
			protected void onPostExecute(List<String> result) {
				super.onPostExecute(result);
				taskCallback.onSuccess();
			}

			@Override
			protected List<String> doInBackground(Void... params) {
				try {

					Elements links = Jsoup
							.connect(
									"https://www.google.com/search?q="
											+ URLEncoder.encode(query, AppController.UTF_8))
							.userAgent(
									"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
							.get().select("li.g>h3>a");
					for (Element link : links) {

						String title = link.text();
						String url = link.absUrl("href"); // Google returns URLs in format
															// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
						url = URLDecoder
								.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')),
										"UTF-8");

						if (!url.startsWith("http")) {
							continue; // Ads/news/etc.
						}

						result.add(title + ": " + url);

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return result;
			}

		}.execute();
		return result;
	}

	@Override
	public List<String> getDataFromGoogleNews(final String query,
			final TaskCallback taskCallback) {
		// TODO Auto-generated method stub
		return null;
	}
}
