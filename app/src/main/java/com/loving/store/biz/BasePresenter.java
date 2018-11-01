package com.loving.store.biz;

/**
 * Class: BasePresenter
 * Description: ${Description}
 * Creator: Appconomy
 * Date 22/06/2017.
 */

public abstract class BasePresenter<V extends IMvpView> implements Presenter<V> {
    protected V mvpView;

    public void attachView(V view) {
        mvpView = view;
    }

    @Override
    public void detachView(V view) {
        mvpView = null;
    }
}
