package com.pipoll.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pipoll.R;

/**
 * @author Bulbi
 * 
 *         This custom DialogFragment manage the poll comment posted by user. Caller must overrides
 *         onActivityResult() althought this is a fragment, not an Activity. The data is send back to caller
 *         in the Extras whose keys are EXTRA_KEY_LIKED, EXTRA_KEY_COMMENT_TITLE and
 *         EXTRA_KEY_COMMENT_DESCRIPTION
 */
public class CommentDialogFragment extends DialogFragment {
	public static final String EXTRA_KEY_LIKED = "CommentFragment.keyLiked";
	public static final String EXTRA_KEY_COMMENT_TITLE = "CommentFragment.keyCommentTitle";
	public static final String EXTRA_KEY_COMMENT_DESCRIPTION = "CommentFragment.keyComment";

	private boolean mLiked;
	private String mCommentTitle;
	private String mCommentDescription;

	private ImageButton mImgBtnNo;
	private ImageButton mImgBtnYes;

	private TextView mTvLike;
	private EditText mEdtCommentTitle;
	private EditText mEdtCommentDescription;

	// public static CommentDialogFragment newInstance() {
	// TextView textViewLike = (TextV CommentDialogFragment fragment = new CommentDialogFragment();
	// EditText edtCommentTitle = (Ed return fragment;
	// EditText edtCommentDescription}

	public static CommentDialogFragment newInstance(boolean liked) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_KEY_LIKED, liked);

		CommentDialogFragment fragment = new CommentDialogFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;

		Intent i = new Intent();
		i.putExtra(EXTRA_KEY_LIKED, mLiked);
		i.putExtra(EXTRA_KEY_COMMENT_TITLE, mCommentTitle);
		i.putExtra(EXTRA_KEY_COMMENT_DESCRIPTION, mCommentDescription);

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);

		// Set the appearance of the buttons according to user's choice ?
		mLiked = getArguments().getBoolean(EXTRA_KEY_LIKED);

		mImgBtnNo = (ImageButton) v.findViewById(R.id.imageButtonNo);
		// onClick() does not work to keep a button at state pressed.
		mImgBtnNo.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mLiked = false;
				// getArguments().putBoolean(EXTRA_KEY_LIKED, mLiked);
				updateLike();
				v.performClick();
				return true;
			}
		});

		mImgBtnYes = (ImageButton) v.findViewById(R.id.imageButtonYes);
		mImgBtnYes.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mLiked = true;
				updateLike();
				v.performClick();
				return true;
			}
		});

		mTvLike = (TextView) v.findViewById(R.id.textViewLike);
		mEdtCommentTitle = (EditText) v.findViewById(R.id.editTextCommentTitle);
		mEdtCommentTitle.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCommentTitle = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mEdtCommentDescription = (EditText) v.findViewById(R.id.editTextCommentDescription);
		mEdtCommentDescription.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCommentDescription = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// TODO : do stuff to preserve data on rotation ? => Check DialogDate in CriminalIntent

		updateLike();

		return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.comment_dialog_title)
				.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).create();
	}

	private void updateLike() {
		Resources r = getActivity().getResources();
		if (mLiked) {
			mTvLike.setText(R.string.comment_like);
			mTvLike.setTextColor(r.getColor(android.R.color.holo_green_dark));
			mImgBtnYes.setPressed(true);
			mImgBtnNo.setPressed(false);
		} else {
			mTvLike.setText(R.string.comment_dislike);
			mTvLike.setTextColor(r.getColor(android.R.color.holo_orange_dark));
			mImgBtnYes.setPressed(false);
			mImgBtnNo.setPressed(true);
		}
	}
}