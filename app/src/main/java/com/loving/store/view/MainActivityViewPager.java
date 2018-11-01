package com.loving.store.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Class: ${Method}
 * Description: ${Description}
 * Creator: Appconomy
 * Date 29/08/2017.
 */

public class MainActivityViewPager extends ViewPager {
    public MainActivityViewPager(Context context) {
        super(context);
    }

    public MainActivityViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
