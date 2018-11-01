package com.loving.store.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loving.store.R;
import com.loving.store.adapter.FragmentIncomeAdapter;
import com.loving.store.base.BaseFragment;
import com.loving.store.db.DBUtils;
import com.loving.store.view.refresh.BaseSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView fragment_income_recyclerView;
    private BaseSwipeRefreshLayout fragment_income_refresh;
    private int page;
    private boolean isLocal = true;
    private FragmentIncomeAdapter adapter;
    private List<Map<String, Object>> mapList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, null);
//        initPresenter();
//        initView(view);
//        initData();
        return view;
    }

    private void initData() {
    }

    private void initView(View view) {
        view.findViewById(R.id.incomeBtn).setOnClickListener(this);
        fragment_income_recyclerView = (RecyclerView) view.findViewById(R.id.fragment_income_recyclerView);
        fragment_income_refresh = (BaseSwipeRefreshLayout) view.findViewById(R.id.fragment_income_refresh);
        fragment_income_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FragmentIncomeAdapter(getActivity());
        fragment_income_recyclerView.setAdapter(adapter);
        fragment_income_refresh.setOnFreshOrMoreListener(new BaseSwipeRefreshLayout.OnFreshOrMoreListener() {
            @Override
            public void OnFresh() {
                page = 0;
                localLoad();
                fragment_income_refresh.setRefreshing(false);
            }

            @Override
            public void OnMore() {
                ++page;
                localLoad();
                fragment_income_refresh.setLoadMore(false);
            }
        });
        localLoad();
    }

    private void localLoad() {
        if (!isLocal) {
            netLoad();
            return;
        }
        DBUtils dbUtils = new DBUtils(getActivity());
        List<Map<String, Object>> maps = dbUtils.getIncome(page, 20);
        mapList.addAll(maps);
        if (mapList == null || mapList.size() == 0) {
            netLoad();
            isLocal = false;
        } else {
            adapter.addData(mapList);
            adapter.notifyDataSetChanged();
        }
    }

    private void netLoad() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", 20);
        mPresenter.httpRequest("", map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
        }
    }

    @Override
    public void onError(Object errorMsg, String code) {

    }

    @Override
    public void onSuccess(Object response, String code) {

    }
}
