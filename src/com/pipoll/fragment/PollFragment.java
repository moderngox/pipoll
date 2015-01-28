package com.pipoll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pipoll.R;
import com.pipoll.data.Poll;
import com.pipoll.data.PollLab;

public class PollFragment extends Fragment {

	private static final String KEY_POLL_ID = "keyPollId";

	TextView mTvTitle;
	TextView mTvDescription;
	TextView mTvDescription2;
	TextView mTvDescription3;
	ImageButton mImgBtnYes;
	ImageButton mImgBtnNo;

	Poll mPoll;

	// public static ProfileFragment newInstance(UUID crimeId) {
	// Bundle args = new Bundle();
	// args.putSerializable(EXTRA_CRIME_ID, crimeId);
	//
	// ProfileFragment fragment = new ProfileFragment();
	// fragment.setArguments(args);
	//
	// return fragment;
	// }
	// public static PollFragment newInstance() {
	// Bundle args = new Bundle();
	//
	// PollFragment fragment = new PollFragment();
	// fragment.setArguments(args);
	//
	// return fragment;
	// }

	public static PollFragment newInstance(String pollId) {
		Bundle args = new Bundle();
		args.putString(KEY_POLL_ID, pollId);

		PollFragment fragment = new PollFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String pollId = (String) getArguments().getSerializable(KEY_POLL_ID);
		mPoll = PollLab.get(getActivity()).getPoll(pollId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_poll, parent, false);

		mTvTitle = (TextView) v.findViewById(R.id.text_view_title);
		mTvDescription = (TextView) v.findViewById(R.id.text_view_description);
		mTvDescription2 = (TextView) v.findViewById(R.id.text_view_description_2);
		mTvDescription3 = (TextView) v.findViewById(R.id.text_view_description_3);
		mImgBtnYes = (ImageButton) v.findViewById(R.id.image_button_yes);
		mImgBtnNo = (ImageButton) v.findViewById(R.id.image_button_no);

		mTvTitle.setText(mPoll.getId());

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
