package com.pipoll.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.pipoll.R;

/**
 * @author Bulbi
 * 
 *         This custom DialogFragment manage the poll comment posted by user. Caller must
 *         overrides onActivityResult() althought this is a fragment, not an Activity. The data
 *         is send back to caller in the Extras whose keys are EXTRA_KEY_LIKED,
 *         EXTRA_KEY_COMMENT_TITLE and EXTRA_KEY_COMMENT_DESCRIPTION
 */
public class FilterDialogFragment extends DialogFragment {
	public static final String EXTRA_KEY_CATEGORY = "FilterFragment.Category";
	public static final String EXTRA_KEY_PERIOD = "FilterFragment.Period";
	public static final String EXTRA_KEY_COUNTRY = "FilterFragment.Country";

	// TODO : not sure what type to use yet
	private String mCategory, mPeriod, mCountry;
	private Spinner mSpinCategory, mSpinPeriod, mSpinCountry;

	// public static CommentDialogFragment newInstance() {
	// TextView textViewLike = (TextV CommentDialogFragment fragment = new
	// CommentDialogFragment();
	// EditText edtCommentTitle = (Ed return fragment;
	// EditText edtCommentDescription}

	public static FilterDialogFragment newInstance() {
		FilterDialogFragment fragment = new FilterDialogFragment();

		return fragment;
	}

	public static FilterDialogFragment newInstance(String category, String period,
			String country) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_KEY_CATEGORY, category);
		args.putSerializable(EXTRA_KEY_PERIOD, period);
		args.putSerializable(EXTRA_KEY_COUNTRY, country);

		FilterDialogFragment fragment = new FilterDialogFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;

		Intent i = new Intent();
		i.putExtra(EXTRA_KEY_CATEGORY, mCategory);
		i.putExtra(EXTRA_KEY_PERIOD, mPeriod);
		i.putExtra(EXTRA_KEY_COUNTRY, mCountry);

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_filter, null);

		// Set the appearance of the buttons according to user's choice ?
		mCategory = getArguments().getString(EXTRA_KEY_CATEGORY);
		mPeriod = getArguments().getString(EXTRA_KEY_PERIOD);
		mCountry = getArguments().getString(EXTRA_KEY_COUNTRY);

		mSpinCategory = (Spinner) v.findViewById(R.id.spinner_category);
		mSpinPeriod = (Spinner) v.findViewById(R.id.spinner_period);
		mSpinCountry = (Spinner) v.findViewById(R.id.spinner_country);

		// TODO : populate the spinners

		mSpinCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// TODO : do stuff to preserve data on rotation ? => Check DialogDate in CriminalIntent

		return new AlertDialog.Builder(getActivity()).setView(v)
				.setTitle(R.string.filter_dialog_title)
				.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).create();
	}

}