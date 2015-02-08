/**
 * 
 */
package com.pipoll.data.parcelable;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.pipoll.data.Trend;

/**
 * @author moderngox
 * 
 */
public class ParcelableTrend implements Parcelable {

	private Trend trend;

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
		arg0.writeSerializable(trend);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelableTrend> CREATOR = new ParcelablePoll.Creator<ParcelableTrend>() {
		public ParcelableTrend createFromParcel(Parcel in) {
			return new ParcelableTrend((Trend) in.readSerializable());
		}

		public ParcelableTrend[] newArray(int size) {
			return new ParcelableTrend[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableTrend(Trend in) {
		trend = (Trend) in;
	}

	public Trend getTrend() {

		return trend;
	}

	public static List<ParcelableTrend> getParcelTrends(List<Trend> trends) {
		List<ParcelableTrend> pTrends = new ArrayList<ParcelableTrend>();
		for (Trend trend : trends) {
			pTrends.add(new ParcelableTrend(trend));
		}

		return pTrends;
	}

	public static List<Trend> getTrends(List<ParcelableTrend> pTrends) {
		List<Trend> trends = new ArrayList<Trend>();
		for (ParcelableTrend pTrend : pTrends) {
			trends.add(pTrend.getTrend());
		}

		return trends;
	}
}
