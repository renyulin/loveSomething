package com.loving.store.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.loving.store.R;

/**
 * 加载对话框
 */
public class LoadingDialog extends Dialog {
    private static LoadingDialog dialog;

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static LoadingDialog show(Context context) throws Exception {
        if (dialog == null) {
            dialog = new LoadingDialog(context, R.style.ProgressHUD);
        }

        if (!dialog.isShowing()) {
            dialog.setContentView(R.layout.dialog_loading);
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
//            lp.y = -60; // 设置位置
            dialog.getWindow().setAttributes(lp);
            dialog.setCancelable(false);
            dialog.show();
        }
        return dialog;
    }


    @Override
    public void dismiss() {
        try {
            super.dismiss();
            dialog = null;
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        dialog.dismiss();
        return super.onKeyDown(keyCode, event);
    }
}
