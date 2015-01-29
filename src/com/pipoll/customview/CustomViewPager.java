package com.pipoll.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Bulbi
 * 
 */
public class CustomViewPager extends ViewPager {

	private boolean mPagingEnabled;
	private boolean mIsVertical;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPagingEnabled = true;
		mIsVertical = false;
	}

	private void changeToVertical() {
		if (mIsVertical) {
			// The majority of the magic happens here
			setPageTransformer(true, new VerticalPageTransformer());
			// The easiest way to get rid of the overscroll drawing that happens on the left
			// and right
			setOverScrollMode(OVER_SCROLL_NEVER);
		}
	}

	public void setPagingEnabled(boolean enabled) {
		this.mPagingEnabled = enabled;
	}

	public void setIsVertical(boolean isVertical) {
		mIsVertical = isVertical;
		changeToVertical();
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// int height = 0;
	// for (int i = 0; i < getChildCount(); i++) {
	// View child = getChildAt(i);
	// child.measure(widthMeasureSpec,
	// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	// int h = child.getMeasuredHeight();
	// if (h > height)
	// height = h;
	// }
	// heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
	//
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Swaps the x and y coords of the touch event
		if (mIsVertical) {
			event.setLocation(event.getY(), event.getX());
		}
		performClick();
		if (this.mPagingEnabled) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean performClick() {
		// Calls the super implementation, which generates an AccessibilityEvent
		// and calls the onClick() listener on the view, if any
		super.performClick();

		// Handle the action for the custom click here
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.mPagingEnabled) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

	private class VerticalPageTransformer implements ViewPager.PageTransformer {
		@Override
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				view.setAlpha(1);

				// Counteract the default slide transition
				// view.setTranslationX(pageWidth * -position);
				view.setTranslationX(pageWidth * -position + 2); // +2 because of border

				// set Y position to swipe in from top
				float yPosition = position * pageHeight;
				view.setTranslationY(yPosition);

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

}
