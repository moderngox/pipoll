/**
 * 
 */
package com.pipoll.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pipoll.R;
import com.pipoll.data.google.GoogleResult;
import com.pipoll.interfaces.TaskCallback;
import com.pipoll.taskmaker.GoogleService;

/**
 * @author moderngox
 * 
 */
public class GoogleSearchActivity extends FragmentActivity {

	private List<GoogleResult> googleSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_search);

		final EditText search = (EditText) findViewById(R.id.google_searchbar);
		ImageView show = (ImageView) findViewById(R.id.btn_show);
		final TextView results = (TextView) findViewById(R.id.results);
		final GoogleService googleTaskMaker = new GoogleService(this);
		show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				googleSearch = googleTaskMaker.googleSearch(search.getText().toString(),
						new TaskCallback() {

							private String result;

							@Override
							public void onSuccess() {
								for (GoogleResult gr : googleSearch) {
									result += gr.toString() + "\n";
								}
								results.setText(result);
								results.setVisibility(View.VISIBLE);
							}
						});

			}
		});

	}

}
