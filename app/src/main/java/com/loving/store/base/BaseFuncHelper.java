package com.loving.store.base;

import android.content.Context;

import com.loving.store.ui.dialog.LoadingDialog;

public class BaseFuncHelper implements PresentationLayerFunc {
    private Context context;
    protected LoadingDialog loadingDialog;

    public BaseFuncHelper(Context context) {
        this.context = context;
    }

    @Override
    public void showProgressDialog() {
        try {
            loadingDialog = LoadingDialog.show(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
