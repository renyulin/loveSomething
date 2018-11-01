package com.loving.store.base;

import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loving.store.biz.HttpFactory;
import com.loving.store.biz.HttpPresenter;
import com.loving.store.biz.IMvpView;

/**
 * baseActivity
 */
public abstract class BaseActivity extends FragmentActivity implements IMvpView, OnClickListener {
    public HttpPresenter mPresenter;
    private BaseFuncHelper baseFuncHelper;
    private Toast mtoast;

    protected abstract void initData();

    protected abstract void initViews();

    @Override
    public void initPresenter() {
        baseFuncHelper = new BaseFuncHelper(this);
        mPresenter = HttpFactory.create(HttpPresenter.class);
        mPresenter.attachView(this);
    }

    @Override
    public void showLoading() {
        baseFuncHelper.showProgressDialog();
    }

    @Override
    public void hideLoading() {
        baseFuncHelper.hideProgressDialog();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView(this);
        }
        super.onDestroy();
    }

    public void showToast(String msg) {
        if (mtoast == null)
            mtoast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mtoast.show();
    }
}
