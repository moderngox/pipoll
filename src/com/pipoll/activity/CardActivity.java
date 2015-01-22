package com.pipoll.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.model.CardModel.OnCardDimissedListener;
import com.andtinder.view.CardContainer;
import com.pipoll.R;
import com.pipoll.adapter.CustomCardStackAdapter;

public class CardActivity extends Activity {

	private CardContainer mCardContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);

		mCardContainer = (CardContainer) findViewById(R.id.layoutview);

		Resources r = getResources();

		ArrayList<CardModel> cards = new ArrayList<CardModel>();
		cards.add(new CardModel("USA",
				"This is the description, it is a long description, as you can see", r
						.getDrawable(R.drawable.picture1)));
		cards.add(new CardModel("Japan", "JP", r.getDrawable(R.drawable.picture2)));
		cards.add(new CardModel("Paris", "Description goes here", r
				.getDrawable(R.drawable.picture3)));

		// bidouille : on instancie le listener avec cards et on a besoin du listener sur
		// le dernier élément de cards
		OnCardDimissedListener listener = new CardDismissedListenerImpl(this, cards);
		cards.get(cards.size() - 1).setOnCardDimissedListener(listener);

		final CustomCardStackAdapter adapter = new CustomCardStackAdapter(this, cards);
		mCardContainer.setAdapter(adapter);
	}

	private class CardDismissedListenerImpl implements CardModel.OnCardDimissedListener {
		// private Context mContext;
		private ArrayList<CardModel> mCards = new ArrayList<CardModel>();

		private int currentIndex;

		CardDismissedListenerImpl(Context c, ArrayList<CardModel> cards) {
			// mContext = c;
			mCards = cards;
			currentIndex = cards.size() - 1;

			Log.e("TAG", "index : " + currentIndex);

		}

		@Override
		public void onDislike() {
			Log.e("Swipeable Cards", "I dislike the card " + " "
					+ mCards.get(currentIndex).getTitle());
			Toast.makeText(CardActivity.this,
					"I dislike the card " + mCards.get(currentIndex).getTitle(),
					Toast.LENGTH_SHORT).show();
			currentIndex--;
			Log.e("TAG", "currentIndex : " + currentIndex);
		}

		@Override
		public void onLike() {
			Log.e("Swipeable Cards", "I like the card " + mCards.get(currentIndex).getTitle());
			Toast.makeText(CardActivity.this,
					"I like the card " + mCards.get(currentIndex).getTitle(),
					Toast.LENGTH_SHORT).show();
			currentIndex--;
			Log.e("TAG", "currentIndex : " + currentIndex);
		}
	}
}
