package com.loving.store.biz;

/**
 * Class: IMvpView
 * Description: Activity需要实现的接口
 * Creator: Appconomy
 * Date 22/06/2017.
 */

public interface IMvpView<T> {
    void onError(Object errorMsg, String code);

    void onSuccess(T response, String code);

    void showLoading();

    void hideLoading();

    void initPresenter();
}
