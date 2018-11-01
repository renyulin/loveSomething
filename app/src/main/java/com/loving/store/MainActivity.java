package com.loving.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loving.store.adapter.MainTabAdapter;
import com.loving.store.ui.ExpenditureFragment;
import com.loving.store.ui.IncomeFragment;
import com.loving.store.ui.MineFragment;
import com.loving.store.view.MainActivityViewPager;
import com.loving.store.view.tabpage.TabPageIndicator;
import com.loving.store.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewPager mainViewPager;
    private TabPageIndicator mainIndicator;
    private MainTabAdapter tabAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mainViewPager = findViewById(R.id.mainViewPager);
        mainIndicator = findViewById(R.id.mainIndicator);
        initTab();
    }

    private void initTab() {
        fragmentList.add(new IncomeFragment());
        fragmentList.add(new ExpenditureFragment());
        fragmentList.add(new MineFragment());
        tabAdapter = new MainTabAdapter(getSupportFragmentManager(), fragmentList, this);
        mainViewPager.setAdapter(tabAdapter);
        mainViewPager.setOffscreenPageLimit(2);
        mainIndicator.setViewPager(mainViewPager);
        mainIndicator.setVisibility(View.VISIBLE);
        mainIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 800) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }

    public void mainClick(View view) {
        startActivity(new Intent(this, WebActivity.class));
    }
}
