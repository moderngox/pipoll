/**
 * 
 */
package com.pipoll.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pipoll.app.AppController;
import com.pipoll.data.RSSFeed;
import com.pipoll.data.RSSNode;
import com.pipoll.data.Trend;
import com.pipoll.entity.rssnodeendpoint.Rssnodeendpoint;
import com.pipoll.entity.rssnodeendpoint.model.RSSNodeCollection;
import com.pipoll.interfaces.ITrend;
import com.pipoll.interfaces.callback.TaskCallback;

/**
 * @author moderngox
 * 
 *         Instanciate and use this class whenever you need to make a generic task related to @Trend
 * 
 */
public class TrendService implements ITrend {

	@SuppressWarnings("unused")
	private Activity activity;

	public TrendService(Activity activity) {
		this.activity = activity;
	}

	public TrendService() {
	}

	public boolean trendAlreadyPresent(String trendname, List<Trend> trends) {
		boolean result = false;

		for (Trend t : trends) {
			if (t.getName().equals(trendname)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pipoll.interfaces.ITrend#getTrends(com.pipoll.interfaces.TaskCallback)
	 */
	@Override
	public List<Trend> getHawtTrends(final TaskCallback taskcallback) {
		final List<Trend> trends = new ArrayList<Trend>();
		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... arg0) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				try {
					String request = AppController.TRENDS_ENDPOINT;
					HttpGet httpgetreq = new HttpGet(request);
					httpgetreq.setHeader("Content-type", "application/json");
					String responseText = null;
					HttpResponse httpresponse = httpclient.execute(httpgetreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);

					if (!responseText.isEmpty()) {
						JSONObject jsonResponse = new JSONObject(responseText);
						Iterator<String> keysIt = jsonResponse.keys();
						List<String> keysList = IteratorUtils.toList(keysIt);
						for (String key : keysList) {
							JSONArray jsonArray = jsonResponse.getJSONArray(key);
							for (int i = 0; i < jsonArray.length(); i++) {
								Trend trend = new Trend();
								String trendname = jsonArray.getString(i);
								if (!trendAlreadyPresent(trendname, trends)) {
									trend.setName(trendname);
									trends.add(trend);
								}
							}
						}

					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				httpclient.getConnectionManager().shutdown();
				return trends;
			}

			@Override
			protected void onPostExecute(List<Trend> result) {
				super.onPostExecute(result);
				if (!trends.isEmpty()) {
					taskcallback.onSuccess();
				}
			}

		}.execute();
		return trends;
	}

	@Override
	public List<Trend> getDailyTopTrends(final String location, final String language,
			final String dateUSFormat, final TaskCallback taskcallback) {
		final List<Trend> trends = new ArrayList<Trend>();
		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... params) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(AppController.GOOGLE_HOT_TRENDS
						+ "?ajax=1&htd=" + dateUSFormat + "&pn=" + location + "&htv=l&hl="
						+ language);
				httppostreq.setHeader("Content-type", "application/json");
				String responseText = null;

				try {
					StringEntity se = new StringEntity(AppController.UTF_8);
					httppostreq.setEntity(se);
					HttpResponse httpresponse = httpclient.execute(httppostreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					JSONObject jsonResponse = new JSONObject(responseText);
					JSONArray jsonArray = jsonResponse.getJSONArray("trendsByDateList")
							.getJSONObject(0).getJSONArray("trendsList");
					for (int i = 0; i < jsonArray.length(); i++) {
						Trend trend = new Trend();
						trend.setName(jsonArray.getJSONObject(i).getString("title"));
						trend.setImage(jsonArray.getJSONObject(i).getString("imgUrl"));
						trends.add(trend);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return trends;
			}

			@Override
			protected void onPostExecute(List<Trend> result) {
				super.onPostExecute(result);
				if (!trends.isEmpty()) {
					taskcallback.onSuccess();
				}
			}
		}.execute();
		return trends;
	}

	@Override
	public List<Trend> getHourlyTrends(final String location, final String language,
			final TaskCallback taskcallback) {
		final List<Trend> trends = new ArrayList<Trend>();
		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... params) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(AppController.GOOGLE_HOT_TRENDS
						+ "?ajax=1&pn=" + location + "&htv=l&hl=" + language);
				httppostreq.setHeader("Content-type", "application/json");
				String responseText = null;

				try {
					StringEntity se = new StringEntity(AppController.UTF_8);
					httppostreq.setEntity(se);
					HttpResponse httpresponse = httpclient.execute(httppostreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					JSONObject jsonResponse = new JSONObject(responseText);
					JSONArray trendsByDateList = jsonResponse.getJSONArray("trendsByDateList");
					for (int i = 0; i < trendsByDateList.length(); i++) {
						JSONObject trendsObj = trendsByDateList.getJSONObject(i);
						if (!trendsObj.isNull("trendsList")) {
							JSONArray trendsList = trendsObj.getJSONArray("trendsList");
							for (int j = 0; j < trendsList.length(); j++) {
								Trend t = new Trend();
								t.setName(trendsList.getJSONObject(j).getString("title"));
								trends.add(t);
							}
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return trends;
			}

			@Override
			protected void onPostExecute(List<Trend> result) {
				super.onPostExecute(result);
				if (!trends.isEmpty()) {
					taskcallback.onSuccess();
				}
			}
		}.execute();
		return trends;
	}

	@Override
	public List<Trend> getMonthlyTopTrends(final String location, final String language,
			final TaskCallback taskcallback) {
		final List<Trend> trends = new ArrayList<Trend>();
		new AsyncTask<Void, Void, List<Trend>>() {

			@Override
			protected List<Trend> doInBackground(Void... params) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(AppController.GOOGLE_HOT_TRENDS
						+ "?ajax=1&pn=" + location + "&htv=m&hl=" + language);
				httppostreq.setHeader("Content-type", "application/json");
				String responseText = null;

				try {
					StringEntity se = new StringEntity(AppController.UTF_8);
					httppostreq.setEntity(se);
					HttpResponse httpresponse = httpclient.execute(httppostreq);
					responseText = EntityUtils.toString(httpresponse.getEntity());
					Log.d("Response: ", responseText);
					JSONObject jsonResponse = new JSONObject(responseText);
					JSONArray weeksList = jsonResponse.getJSONArray("weeksList");
					for (int i = 0; i < weeksList.length(); i++) {
						JSONArray daysList = weeksList.getJSONObject(i).getJSONArray(
								"daysList");
						for (int j = 0; j < daysList.length(); j++) {
							if (!daysList.getJSONObject(j).isNull("data")) {
								JSONObject trend = daysList.getJSONObject(j)
										.getJSONObject("data").getJSONObject("trend");
								Trend t = new Trend();
								t.setName(trend.getString("title"));
								trends.add(t);
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return trends;
			}

			@Override
			protected void onPostExecute(List<Trend> result) {
				super.onPostExecute(result);
				if (!trends.isEmpty()) {
					taskcallback.onSuccess();
				}
			}
		}.execute();
		return trends;
	}

	@Override
	public List<RSSNode> getRSSNode(List<RSSFeed> rssFeeds, final TaskCallback taskcallback) {
		final List<RSSNode> rssNodes = new ArrayList<RSSNode>();
		new AsyncTask<RSSFeed, Void, List<RSSNode>>() {

			protected List<RSSNode> doInBackground(RSSFeed... rssFeeds) {

				for (RSSFeed rssFeed : rssFeeds) {
					try {
						URL url = new URL(rssFeed.getFeedEntry());
						InputStream in;
						in = url.openStream(); // <=> in =
												// url.openConnection().getInputStream();
						XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
						factory.setNamespaceAware(false);
						XmlPullParser xpp = factory.newPullParser();
						xpp.setInput(in, "UTF_8");
						/*
						 * RSS Parsing from :
						 * http://androidresearch.wordpress.com/2012/01/21/creating
						 * -a-simple-rss-application-in-android/
						 */
						boolean insideItem = false;
						List<String> titles = new ArrayList<String>();
						List<String> links = new ArrayList<String>();
						List<String> descriptions = new ArrayList<String>();
						// Returns the type of current event: START_TAG, END_TAG, etc..
						int eventType = xpp.getEventType();
						while (eventType != XmlPullParser.END_DOCUMENT) {
							if (eventType == XmlPullParser.START_TAG) {

								if (xpp.getName().equalsIgnoreCase("item")) {
									insideItem = true;
								} else if (xpp.getName().equalsIgnoreCase("title")) {
									if (insideItem)
										titles.add(xpp.nextText()); // extract the title
								} else if (xpp.getName().equalsIgnoreCase("link")) {
									if (insideItem)
										links.add(xpp.nextText()); // extract the link of
																	// article
								} else if (xpp.getName().equalsIgnoreCase("description")) {
									if (insideItem)
										descriptions.add(xpp.nextText()); // extract the
																			// description
								}
							} else if (eventType == XmlPullParser.END_TAG
									&& xpp.getName().equalsIgnoreCase("item")) {
								insideItem = false;
							}
							eventType = xpp.next(); // move to next element
						}
						for (int i = 0; i < titles.size(); i++) {
							rssNodes.add(new RSSNode(titles.get(i), descriptions.get(i), links
									.get(i), rssFeed.getCategory()));
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return rssNodes;
			}

			// after downloading
			protected void onPostExecute(List<RSSNode> result) {
				if (!result.isEmpty()) {
					taskcallback.onSuccess();
				}
			}
		}.execute(rssFeeds.toArray(new RSSFeed[rssFeeds.size()]));
		return rssNodes;
	}

	@Override
	public List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> getBackendNodes(
			List<RSSFeed> rssFeeds, final TaskCallback taskcallback) {
		final List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> rssNodes = new ArrayList<com.pipoll.entity.rssnodeendpoint.model.RSSNode>();
		new AsyncTask<RSSFeed, Void, List<com.pipoll.entity.rssnodeendpoint.model.RSSNode>>() {
			Rssnodeendpoint.Builder endpointBuilder = new Rssnodeendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					new HttpRequestInitializer() {
						public void initialize(HttpRequest httpRequest) {
						}
					}).setRootUrl(AppController.BACKEND_ENDPOINT);
			Rssnodeendpoint endpoint = endpointBuilder.build();
			RSSNodeCollection rssNodeColl;

			protected List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> doInBackground(
					RSSFeed... rssFeeds) {

				for (RSSFeed rssFeed : rssFeeds) {
					try {
						rssNodeColl = endpoint.listFeedsNodes(rssFeed.getFeedEntry(),
								rssFeed.getCategory()).execute();
						for (com.pipoll.entity.rssnodeendpoint.model.RSSNode rssNode : rssNodeColl
								.getItems()) {
							rssNode.setTopic(AppController.extractTopic(rssNode.getTitle()));
							rssNodes.add(rssNode);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return rssNodes;
			}

			// after downloading
			protected void onPostExecute(
					List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> result) {
				if (!result.isEmpty()) {
					taskcallback.onSuccess();
				}
			}
		}.execute(rssFeeds.toArray(new RSSFeed[rssFeeds.size()]));
		return rssNodes;
	}
}
