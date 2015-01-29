/**
 * 
 */
package com.pipoll.data.google;

/**
 * @author moderngox
 * 
 */
public class GoogleResult {

	private ResponseData responseData;

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public String toString() {
		return "ResponseData[" + responseData + "]";
	}
}
