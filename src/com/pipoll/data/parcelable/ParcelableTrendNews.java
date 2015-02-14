/**
 * 
 */
package com.pipoll.data.parcelable;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.pipoll.data.TrendNews;

/**
 * @author moderngox
 * 
 */
public class ParcelableTrendNews implements Parcelable {

	private TrendNews trendNews;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	// 99.9% of the time you can just ignore this
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	// write your object's data to the passed-in Parcel
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeSerializable(trendNews);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelableTrendNews> CREATOR = new ParcelablePoll.Creator<ParcelableTrendNews>() {
		public ParcelableTrendNews createFromParcel(Parcel in) {
			return new ParcelableTrendNews((TrendNews) in.readSerializable());
		}

		public ParcelableTrendNews[] newArray(int size) {
			return new ParcelableTrendNews[size];
		}
	};

	public static ArrayList<TrendNews> toTrendNewsList(
			ArrayList<ParcelableTrendNews> parcelTrendNewsList) {
		ArrayList<TrendNews> trendNewsList = new ArrayList<TrendNews>();
		for (ParcelableTrendNews pTrendNews : parcelTrendNewsList) {
			trendNewsList.add(pTrendNews.getTrendNews());
		}
		return trendNewsList;
	}

	public static List<ParcelableTrendNews> getParcelTrendNewsList(
			List<TrendNews> trendNewsList) {
		List<ParcelableTrendNews> pTrendNewsList = new ArrayList<ParcelableTrendNews>();
		for (TrendNews trendNews : trendNewsList) {
			pTrendNewsList.add(new ParcelableTrendNews(trendNews));
		}

		return pTrendNewsList;
	}

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableTrendNews(TrendNews in) {
		trendNews = (TrendNews) in;
	}

	public TrendNews getTrendNews() {

		return trendNews;
	}

}
