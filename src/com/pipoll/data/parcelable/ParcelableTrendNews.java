/**
 * 
 */
package com.pipoll.data.parcelable;

import com.pipoll.data.TrendNews;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author moderngox
 * 
 */
public class ParcelableTrendNews implements Parcelable {

	private TrendNews trendnews;

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
		arg0.writeSerializable(trendnews);
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

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableTrendNews(TrendNews in) {
		trendnews = (TrendNews) in;
	}

	public TrendNews getTrendnews() {

		return trendnews;
	}

}
