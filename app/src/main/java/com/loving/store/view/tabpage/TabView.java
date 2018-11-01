package com.loving.store.view.tabpage;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TabView extends RelativeLayout {
	public TabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public int mIndex;

	public TabView(Context context) {
		super(context);
	}

	// @Override
	// public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	//
	// // Re-measure if we went beyond our maximum size.
	// if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
	// super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
	// MeasureSpec.EXACTLY),
	// heightMeasureSpec);
	// }
	// }

	public int getIndex() {
		return mIndex;
	}
}