/**
 * 
 */
package com.pipoll.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import com.pipoll.activity.MainActivity;
import com.pipoll.interfaces.callback.TaskCallback;
import com.pipoll.service.TrendService;

/**
 * @author moderngox
 * 
 */
public class GetTrendsTest extends ActivityUnitTestCase<MainActivity> {

	private Intent mLaunchIntent;

	public GetTrendsTest() {
		super(MainActivity.class);
	}

	public GetTrendsTest(Class<MainActivity> activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
				MainActivity.class);
		startActivity(mLaunchIntent, null, null);
	}

	@MediumTest
	public void testGetTrends() throws Throwable {

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				new TrendService().getTrends(new TaskCallback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub

					}
				});
			}
		});

	}

}
