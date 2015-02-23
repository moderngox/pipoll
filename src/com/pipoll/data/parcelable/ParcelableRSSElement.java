/**
 * 
 */
package com.pipoll.data.parcelable;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.pipoll.data.RSSNode;

/**
 * @author moderngox
 * 
 */
public class ParcelableRSSElement implements Parcelable {

	private RSSNode rssElement;

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
		arg0.writeSerializable(rssElement);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelableRSSElement> CREATOR = new ParcelablePoll.Creator<ParcelableRSSElement>() {
		public ParcelableRSSElement createFromParcel(Parcel in) {
			return new ParcelableRSSElement((RSSNode) in.readSerializable());
		}

		public ParcelableRSSElement[] newArray(int size) {
			return new ParcelableRSSElement[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableRSSElement(RSSNode in) {
		rssElement = (RSSNode) in;
	}

	public static List<ParcelableRSSElement> getParcelRSSElements(List<RSSNode> rssElements) {
		List<ParcelableRSSElement> pRSSElems = new ArrayList<ParcelableRSSElement>();
		for (RSSNode rssElem : rssElements) {
			pRSSElems.add(new ParcelableRSSElement(rssElem));
		}

		return pRSSElems;
	}

	public static ArrayList<RSSNode> getRSSElements(List<ParcelableRSSElement> pRSSElement) {
		ArrayList<RSSNode> rssElems = new ArrayList<RSSNode>();
		for (ParcelableRSSElement pRSSElem : pRSSElement) {
			rssElems.add(pRSSElem.getRSSElement());
		}

		return rssElems;
	}

	public RSSNode getRSSElement() {

		return rssElement;
	}

}
