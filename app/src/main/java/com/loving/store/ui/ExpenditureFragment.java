package com.loving.store.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loving.store.R;
import com.loving.store.jpush.ExampleUtil;
import com.loving.store.jpush.LocalBroadcastManager;

import cn.jpush.android.api.JPushInterface;

public class ExpenditureFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure, null);
        TextView expenditure = view.findViewById(R.id.expenditure);
        expenditure.setOnClickListener(this);
        view.findViewById(R.id.expenditure2).setOnClickListener(this);
        registerMessageReceiver();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expenditure:
//        File file = new File("/mnt/sdcard/loving", "log.txt");
//        //判断文件是否存在
//        if (file.exists()) {
//            //文件如果存在删除这个文件
//            file.delete();
//        }
                JPushInterface.init(getActivity());
//        JPushInterface.stopPush(getActivity());
//        JPushInterface.resumePush(getActivity());
                break;

            case R.id.expenditure2:
                String rid = JPushInterface.getRegistrationID(getActivity());
                break;
        }
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Toast.makeText(getActivity(), showMsg.toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    public void onPause() {
        isForeground = false;
        super.onPause();
    }

    public static boolean isForeground = false;

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}