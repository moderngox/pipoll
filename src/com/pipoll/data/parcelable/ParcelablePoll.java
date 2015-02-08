/**
 * 
 */
package com.pipoll.data.parcelable;

import java.util.ArrayList;

import com.pipoll.data.Poll;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author moderngox
 * 
 */
public class ParcelablePoll implements Parcelable {

	private Poll poll;

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
		arg0.writeSerializable(poll);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelablePoll> CREATOR = new ParcelablePoll.Creator<ParcelablePoll>() {
		public ParcelablePoll createFromParcel(Parcel in) {
			return new ParcelablePoll((Poll) in.readSerializable());
		}

		public ParcelablePoll[] newArray(int size) {
			return new ParcelablePoll[size];
		}
	};

	public static ArrayList<Poll> toPolls(ArrayList<ParcelablePoll> parcelPolls) {
		ArrayList<Poll> polls = new ArrayList<Poll>();
		for (ParcelablePoll pPoll : parcelPolls) {
			polls.add(pPoll.getPoll());
		}
		return polls;
	}

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelablePoll(Poll in) {
		poll = (Poll) in;
	}

	public Poll getPoll() {

		return poll;
	}

}
