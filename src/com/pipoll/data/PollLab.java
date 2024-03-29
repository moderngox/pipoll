package com.pipoll.data;

import java.util.ArrayList;

import android.content.Context;

public class PollLab {
	private ArrayList<Poll> mPolls;

	private static PollLab sPollLab;

	@SuppressWarnings("unused")
	private Context mAppContext;

	private PollLab(Context appContext) {
		mAppContext = appContext;
		mPolls = new ArrayList<Poll>();

		// create polls sample
		for (int i = 0; i < 5; i++) {
			Poll p = new Poll();

			Trend t = new Trend();
			t.setId("a trend");
			t.setName("a trend name");
			ArrayList<TrendNews> news = new ArrayList<TrendNews>();
			TrendNews trendNews = new TrendNews();
			TrendNews trendNews2 = new TrendNews();
			trendNews
					.setUrl("http://www.jeuxvideo.com/news/415383/lumines-et-meteos-vont-revenir-sur-smartphones.htm");
			news.add(trendNews);
			trendNews2
					.setUrl("http://www.jeuxvideo.com/preview/415318/the-witcher-3-wild-hunt.htm");
			news.add(trendNews2);
			t.setTrendNews(news);

			// p.setTrend(t);
			p.setId("poll #" + i);

			mPolls.add(p);
		}
	}

	public static PollLab get(Context c) {
		if (sPollLab == null) {
			sPollLab = new PollLab(c.getApplicationContext());
			// sPollLab = new PollLab(null);
		}
		return sPollLab;
	}

	public Poll getPoll(String id) {
		for (Poll p : mPolls) {
			if (p.getId().equals(id))
				return p;
		}
		return null;
	}

	public ArrayList<Poll> getPolls() {
		return mPolls;
	}
}
