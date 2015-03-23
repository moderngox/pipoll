/**
 * 
 */
package com.pipoll.data.parcelable;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.pipoll.data.RSSNode;
import com.pipoll.utils.ObjectMapper;

/**
 * @author moderngox
 * 
 */
public class ParcelableRSSNode implements Parcelable {

	private RSSNode rssNode;

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
		arg0.writeSerializable(rssNode);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<ParcelableRSSNode> CREATOR = new ParcelablePoll.Creator<ParcelableRSSNode>() {
		public ParcelableRSSNode createFromParcel(Parcel in) {
			return new ParcelableRSSNode((RSSNode) in.readSerializable());
		}

		public ParcelableRSSNode[] newArray(int size) {
			return new ParcelableRSSNode[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public ParcelableRSSNode(RSSNode in) {
		rssNode = (RSSNode) in;
	}

	public static List<ParcelableRSSNode> getParcelRSSNodes(List<RSSNode> rssNodes) {
		List<ParcelableRSSNode> pRSSNodes = new ArrayList<ParcelableRSSNode>();
		for (RSSNode rssNode : rssNodes) {
			pRSSNodes.add(new ParcelableRSSNode(rssNode));
		}

		return pRSSNodes;
	}

	public static List<ParcelableRSSNode> getParcelNodesFromBE(
			List<com.pipoll.entity.rssnodeendpoint.model.RSSNode> pRssNodes) {

		List<ParcelableRSSNode> pRSSNodes = new ArrayList<ParcelableRSSNode>();
		for (com.pipoll.entity.rssnodeendpoint.model.RSSNode beRSSNode : pRssNodes) {
			pRSSNodes.add(new ParcelableRSSNode(ObjectMapper.mapRSSNode(beRSSNode)));
		}

		return pRSSNodes;
	}

	public static ArrayList<RSSNode> getRSSNodes(List<ParcelableRSSNode> pRSSNodes) {
		ArrayList<RSSNode> rssNodes = new ArrayList<RSSNode>();
		for (ParcelableRSSNode pRSSNode : pRSSNodes) {
			rssNodes.add(pRSSNode.getRSSNode());
		}

		return rssNodes;
	}

	public RSSNode getRSSNode() {

		return rssNode;
	}

	public static ArrayList<com.pipoll.entity.rssnodeendpoint.model.RSSNode> getBERSSNodes(
			ArrayList<ParcelableRSSNode> pRSSNodes) {

		ArrayList<com.pipoll.entity.rssnodeendpoint.model.RSSNode> rssNodes = new ArrayList<com.pipoll.entity.rssnodeendpoint.model.RSSNode>();
		for (ParcelableRSSNode pRSSNode : pRSSNodes) {
			rssNodes.add(ObjectMapper.mapRSSNode(pRSSNode.getRSSNode()));
		}

		return rssNodes;
	}

}
