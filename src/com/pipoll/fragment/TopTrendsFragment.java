package com.pipoll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipoll.R;

public class TopTrendsFragment extends Fragment {

	TextView mTextView;

	// public static ProfileFragment newInstance(UUID crimeId) {
	// Bundle args = new Bundle();
	// args.putSerializable(EXTRA_CRIME_ID, crimeId);
	//
	// ProfileFragment fragment = new ProfileFragment();
	// fragment.setArguments(args);
	//
	// return fragment;
	// }
	public static TopTrendsFragment newInstance() {
		Bundle args = new Bundle();

		TopTrendsFragment fragment = new TopTrendsFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		// UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		// mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_top_trends, parent, false);

		return v;
	}
	//
	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// if (resultCode != Activity.RESULT_OK)
	// return;
	// if (requestCode == REQUEST_DATE) {
	// Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
	// mCrime.setDate(date);
	// updateDate();
	// }
	// }
}
