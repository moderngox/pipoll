/**
 * 
 */
package com.pipoll.data.parcelable;

import com.pipoll.data.Category;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author moderngox
 * 
 */
public class ParcelableCategory implements Parcelable {

	private Category category;

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
		arg0.writeSerializable(category);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelableCategory> CREATOR = new ParcelablePoll.Creator<ParcelableCategory>() {
		public ParcelableCategory createFromParcel(Parcel in) {
			return new ParcelableCategory((Category) in.readSerializable());
		}

		public ParcelableCategory[] newArray(int size) {
			return new ParcelableCategory[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableCategory(Category in) {
		category = (Category) in;
	}

	public Category getCategory() {

		return category;
	}

}
