package com.pipoll.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
 *         onActivityResult() althought this is a fragment, not an Activity.
 */
public class CommentDialogFragment extends DialogFragment {
	public static final String EXTRA_KEY_LIKED = "CommentFragment.keyLiked";
	public static final String EXTRA_KEY_COMMENT_TITLE = "CommentFragment.keyCommentTitle";
	public static final String EXTRA_KEY_COMMENT_DESCRIPTION = "CommentFragment.keyComment";

	private boolean mLiked;
	private String mCommentTitle;
	private String mCommentDescription;

	private ImageButton imageButtonNo;
	private ImageButton imageButtonYes;

	private TextView tvLike;
	private EditText edtCommentTitle;
	private EditText edtCommentDescription;

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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);

		// TODO : Set the appearance of the buttons according to user's choice ?
		mLiked = (boolean) getArguments().getBoolean(EXTRA_KEY_LIKED);

		imageButtonNo = (ImageButton) v.findViewById(R.id.imageButtonNo);
		// imageButtonNo.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mLiked = false;
		// getArguments().putBoolean(EXTRA_KEY_LIKED, mLiked);
		// updateLike();
		// }
		// });
		//
		// onClick() does not work to keep a button at state pressed.
		imageButtonNo.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mLiked = false;
				// getArguments().putBoolean(EXTRA_KEY_LIKED, mLiked);
				updateLike();
				v.performClick();
				return true;
			}
		});

		imageButtonYes = (ImageButton) v.findViewById(R.id.imageButtonYes);
		imageButtonYes.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mLiked = true;
				// getArguments().putBoolean(EXTRA_KEY_LIKED, mLiked);
				updateLike();
				v.performClick();
				return true;
			}
		});

		tvLike = (TextView) v.findViewById(R.id.textViewLike);
		edtCommentTitle = (EditText) v.findViewById(R.id.editTextCommentTitle);
		edtCommentDescription = (EditText) v.findViewById(R.id.editTextCommentDescription);

		// TODO : temporary results !?
		// mCommentTitle = edtCommentTitle.getText().toString();
		// mCommentDescription = edtCommentDescription.getText().toString();

		mCommentTitle = "super titre";
		mCommentDescription = "super commentaire";

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
		if (mLiked) {
			tvLike.setText(R.string.like);
			imageButtonYes.setPressed(true);
			imageButtonNo.setPressed(false);
		} else {
			tvLike.setText(R.string.dislike);
			imageButtonYes.setPressed(false);
			imageButtonNo.setPressed(true);
		}
	}
}
