package com.loving.store.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.loving.store.R;
import com.loving.store.base.BaseActivity;

public class TestActivity extends BaseActivity {
    private LinearLayout test_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initViews();
        initPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        findViewById(R.id.testBtn).setOnClickListener(this);
        test_ll = findViewById(R.id.test_ll);
    }

    @Override
    public void onClick(View v) {
        setSize(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSize(1);
                mPresenter.test();
            }
        }, 2000);
    }

    @Override
    public void onError(Object errorMsg, String code) {

    }

    @Override
    public void onSuccess(Object response, String code) {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    private void setSize(int i) {
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        d.getMetrics(outMetrics);
        lp.width = i == 0 ? 1 : d.getWidth();
        lp.height = i == 0 ? 1 : d.getHeight();
        getWindowManager().updateViewLayout(view, lp);
    }
}
