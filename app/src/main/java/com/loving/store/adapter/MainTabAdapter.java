package com.loving.store.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.loving.store.R;
import com.loving.store.view.tabpage.IconPagerAdapter;

import java.util.List;

public class MainTabAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private List<Fragment> fragmentList;
    private Context context;
    private final int[] CONTENT = new int[]{R.string.tab_income_tv, R.string.tab_expenditure_tv, R.string.tab_mine_tv};
    private final int[] ICONS = new int[]{R.drawable.tab_income_img, R.drawable.tab_expenditure_img, R.drawable.tab_mine_img};

    public MainTabAdapter(FragmentManager fm, List<Fragment> fragmentList, Context context) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(CONTENT[position]);
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index];
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}