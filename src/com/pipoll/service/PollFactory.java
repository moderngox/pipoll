package com.pipoll.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.pipoll.data.Trend;
import com.pipoll.data.parcelable.ParcelablePoll;
import com.pipoll.interfaces.callback.ServiceCallback;

public class PollFactory {



	public void createGPolls(final List<Trend> trends, int start, int end, final ServiceCallback serviceCallback) {
		end = end > trends.size() ? trends.size() : end;
		List<Trend> trendSubList = trends.subList(start, end);
		final DefaultHttpClient httpclient = new DefaultHttpClient();

		new AsyncTask<Trend, Void, List<ParcelablePoll>>() {

			@Override
			protected List<ParcelablePoll> doInBackground(Trend... arg0) {
				List<ParcelablePoll> polls = new ArrayList<ParcelablePoll>();

				for (Trend trend : trends) {
				}
				httpclient.getConnectionManager().shutdown();
				return polls;
			}

			@Override
			protected void onPostExecute(List<ParcelablePoll> result) {
				super.onPostExecute(result);
				if (!result.isEmpty()) {
					serviceCallback.onServiceDone(result);
				}
			}
		}.execute(trendSubList.toArray(new Trend[trendSubList.size()]));
	}
}
