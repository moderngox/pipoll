/**
 * 
 */
package com.pipoll.test;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.TestSession;
import com.pipoll.activity.MainActivity;
import com.pipoll.data.FBCategory;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.service.CategoryService;
import com.pipoll.service.TrendService;

/**
 * @author moderngox
 * 
 */
public class FBTest extends ActivityUnitTestCase<MainActivity> {

	private static String ACCESS_TOKEN = "CAAEyKSUKgk8BAMouzKIw3lo02NTsxZAzZAuRddtbiACvgK9otRcRBm9lfZAS5a5JrOkuZA4ZB4GCKZCys57RTtBkAmJ5ZA2BWvhem4f4eQn06VAHVnuuqZA3sekVZCjqwLf9A5mt1UCHfJYZCFpYmbPduZAoPmijcgTWPulfSq6M3CXcBETroTALm8LTDktkp8sLsJhYctZAsQYJzeZCOMwSxh8sqn6F2XVzbRekZD";
	private static String APP_ID = "336627273204303";
	private static String SECRET = "b6f2228568690ca67ade64e6ada835f7";
	private TestSession testSession;
	private Intent mLaunchIntent;

	public FBTest() {
		super(MainActivity.class);
	}

	public FBTest(Class<MainActivity> activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
				MainActivity.class);
		startActivity(mLaunchIntent, null, null);
		TestSession.setTestApplicationId(APP_ID);
		TestSession.setTestApplicationSecret(SECRET);
		testSession = TestSession.createSessionWithSharedUser(getActivity(),
				Arrays.asList("user_likes", "user_status"), "testUser");
		AccessToken accessToken = AccessToken.createFromExistingAccessToken(
				ACCESS_TOKEN, null, null, AccessTokenSource.TEST_USER,
				Arrays.asList("user_likes", "user_status"));

		testSession.open(accessToken, new StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {

			}
		});
	}

	@MediumTest
	public void testAuth() {
		assertNotNull(testSession);
	}

	@MediumTest
	public void testMe() {
		new Request(Session.getActiveSession(), "/" + APP_ID
				+ "/accounts/test-users", null, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						response.getRawResponse();
					}
				}).executeAsync();
	}

	@MediumTest
	public void testAccessToken() {
		assertNotNull(testSession.getAccessToken());
		assertFalse(testSession.getAccessToken().isEmpty());
	}

	@MediumTest
	public void testUserID() {
		assertNotNull(testSession.getTestUserId());
	}

	@MediumTest
	public void testCategorySearch() throws Throwable {
		final CategoryService catService = new CategoryService(getActivity());

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				new TrendService().getTrends(new TaskCallback() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub

					}
				});
				List<FBCategory> fbCategories = catService
						.getFBCategories(testSession);
				assertTrue(!fbCategories.isEmpty());
			}
		});

	}
}
