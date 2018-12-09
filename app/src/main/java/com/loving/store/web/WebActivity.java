package com.loving.store.web;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.loving.store.R;
import com.loving.store.base.BaseActivity;

import java.util.Map;

/**
 * 活动web
 */
public class WebActivity extends BaseActivity {
    private HtmlFragment htmlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!htmlFragment.goBack()) {
                finish();
            } else {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onError(Object errorMsg, String code) {

    }

    @Override
    public void onSuccess(Object responseEntity, String code) {
        Map<String, Object> map = (Map<String, Object>) responseEntity;
        String url = (String) map.get("html");
        htmlFragment.initWebView(url);
    }

    @Override
    public void initViews() {
        initPresenter();
        htmlFragment = new HtmlFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_web, htmlFragment, "htmlFragment").commit();
    }


    @Override
    public void initData() {
        mPresenter.httpRequest("get_html", true);
    }

    @Override
    public void onClick(View v) {

    }
}
