/**
 * 
 */
package com.pipoll.test;

import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.service.GoogleService;
import com.pipoll.service.TrendService;

/**
 * @author moderngox
 * 
 */
public class SearchTest extends AndroidTestCase {

	private List<GoogleResult> googleSearch;

	public SearchTest() {

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void googleSearchTest() {
		// assertThat(googleSearch.size(),
		// org.hamcrest.CoreMatchers.is(org.hamcrest.CoreMatchers.not(0)));
		googleSearch = new GoogleService().googleSearch("Dieudonné",
				new TaskCallback() {

					@Override
					public void onSuccess() {
						assertTrue(!googleSearch.isEmpty());
					}

				});

	}

	@Test
	public void getTrendsTest() {

		new TrendService().getTrends(new TaskCallback() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.testAndroidTestCaseSetupProperly();
	}
}
