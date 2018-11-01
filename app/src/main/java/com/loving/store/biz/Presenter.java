package com.loving.store.biz;

/**
 * Class: Presenter
 * Description: ${Description}
 * Creator: Appconomy
 * Date 22/06/2017.
 */

public interface Presenter<V> {
    //绑定Activity
    void attachView(V view);
    //解除绑定Activity
    void detachView(V view);
}
