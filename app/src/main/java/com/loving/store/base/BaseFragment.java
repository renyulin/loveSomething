package com.loving.store.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.loving.store.biz.HttpFactory;
import com.loving.store.biz.HttpPresenter;
import com.loving.store.biz.IMvpView;

/**
 * baseFragment
 */
public abstract class BaseFragment extends Fragment implements IMvpView {
    private BaseFuncHelper baseFuncHelper;
    protected HttpPresenter mPresenter;
    private Toast mtoast;

    public void initPresenter() {
        mPresenter = HttpFactory.create(HttpPresenter.class);
        mPresenter.attachView(this);
        baseFuncHelper = new BaseFuncHelper(getActivity());
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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView(this);
        }
        super.onDestroy();
    }

    public void showToast(String msg) {
        if (mtoast == null)
            mtoast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        mtoast.show();
    }
}
