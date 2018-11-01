package com.loving.store.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2015-10-26
 * Time: 18:23
 */
public abstract class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public int getItemViewType(int position) {
        int headerViewCount = getHeaderViewCount();
        if (position < headerViewCount) {
            return 1;//create headerView
        }
        if (position > headerViewCount + getItemViewCount() - 1) {
            return 3; //create footerView
        }
        return 2; //create item view
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 1:
                return onCreateHeaderViewHolder(viewGroup, viewType);
            case 2:
                return onCreateItemViewHolder(viewGroup, viewType);
            case 3:
                return onCreateFooterViewHolder(viewGroup, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                onBindHeaderViewHolder(viewHolder, position);
                break;
            case 2:
                onBindItemViewHolder(viewHolder, position - getHeaderViewCount());
                break;
            case 3:
                onBindFooterViewHolder(viewHolder, position - getHeaderViewCount() - getItemViewCount());
                break;

        }
    }

    @Override
    public int getItemCount() {
        return getItemViewCount() + getHeaderViewCount() + getFooterViewCount();
    }

    public abstract int getItemViewCount();

    public abstract int getHeaderViewCount();

    public abstract int getFooterViewCount();

    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType);

    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType);

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup, int viewType);

    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    public abstract void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position);
}