package com.pipoll.adapter;

import java.util.Collection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardStackAdapter;
import com.pipoll.R;

public class CustomCardStackAdapter extends CardStackAdapter {
	public CustomCardStackAdapter(Context context) {
		super(context);
	}

	public CustomCardStackAdapter(Context context, Collection<? extends CardModel> items) {
		super(context, items);

	}

	@Override
	public View getCardView(int position, CardModel model, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.custom_card_inner, parent, false);
			assert convertView != null;
		}

		((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model
				.getCardImageDrawable());
		((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());
		((TextView) convertView.findViewById(R.id.textview_description)).setText(model
				.getDescription());
		((TextView) convertView.findViewById(R.id.textview_description_2)).setText(model
				.getDescription());
		((TextView) convertView.findViewById(R.id.textview_description_3)).setText(model
				.getDescription());

		ImageButton btnYes = (ImageButton) convertView.findViewById(R.id.image_button_yes);
		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("TAG", "yes click");
				Toast.makeText(getContext(), "Yes clicked", Toast.LENGTH_SHORT).show();

				pop();
			}
		});

		ImageButton btnNo = (ImageButton) convertView.findViewById(R.id.image_button_no);
		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("TAG", "No click");
				Toast.makeText(getContext(), "No clicked", Toast.LENGTH_SHORT).show();

				pop();
			}
		});

		return convertView;
	}
}
