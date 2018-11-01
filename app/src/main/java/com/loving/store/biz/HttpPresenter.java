package com.loving.store.biz;

import android.os.Handler;
import android.text.TextUtils;

import com.loving.store.http.OKManager;

import java.util.Map;

/**
 * 作者：legend on 2017-09-04 14:47
 */
public class HttpPresenter extends BasePresenter implements Factory {

    public void httpRequest(String path, Map<String, Object> map) {
        httpRequest(path, map, true);
    }

    public void httpRequest(final String path, Map<String, Object> map, final boolean isAnimation) {
        if (TextUtils.isEmpty(path)) return;
        if (isAnimation)
            mvpView.showLoading();
        OKManager.getInstance().requestHttp(path, map, new OKManager.CallBack() {
            @Override
            public void doSuccess(Object o) {
                if (mvpView == null) return;
                if (isAnimation)
                    mvpView.hideLoading();
                mvpView.onSuccess(o, path);
            }

            @Override
            public void doFail(Object o) {
                if (mvpView != null) {
                    if (isAnimation)
                        mvpView.hideLoading();
                    mvpView.onError(o, path);
                }
            }
        });
    }

    public void httpRequest(final String path, final boolean isAnimation) {
        if (isAnimation)
            mvpView.showLoading();
        OKManager.getInstance().requestHttp(path, new OKManager.CallBack() {
            @Override
            public void doSuccess(Object o) {
                if (mvpView == null) return;
                if (isAnimation)
                    mvpView.hideLoading();
                mvpView.onSuccess(o, path);
            }

            @Override
            public void doFail(Object o) {
                if (mvpView != null) {
                    if (isAnimation)
                        mvpView.hideLoading();
                    mvpView.onError(o, path);
                }
            }
        });
    }

    public void test() {
        mvpView.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mvpView.hideLoading();
            }
        }, 3000);
    }
}
