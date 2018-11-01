package com.loving.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loving.store.R;
import com.loving.store.base.HeaderViewRecyclerAdapter;
import com.loving.store.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * https://blog.csdn.net/birthmarkqiqi/article/details/78201785 圆角
 */
public class FragmentIncomeAdapter extends HeaderViewRecyclerAdapter {
    private Context context;
    private List<Map<String, Object>> mItems;

    public FragmentIncomeAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<Map<String, Object>> datas) {
        this.mItems = datas;
    }

    @Override
    public int getItemViewCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getHeaderViewCount() {
        return 0;
    }

    @Override
    public int getFooterViewCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_income, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Map<String, Object> map = mItems.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        String path = (String) map.get("path");
        String urlPath = (String) map.get("urlPath");
        String reason = (String) map.get("reason");
        String date = (String) map.get("date");
        date = Utils.translateDate(date);
        holder.adapter_fragment_date.setText(date);
        holder.adapter_fragment_reason.setText(reason);
        Utils.loadImg(context, path, urlPath, holder.adapter_fragment_cover);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adapter_fragment_cover;
        TextView adapter_fragment_reason;
        TextView adapter_fragment_date;

        public ViewHolder(View itemView) {
            super(itemView);
            adapter_fragment_cover = itemView.findViewById(R.id.adapter_fragment_cover);
            adapter_fragment_reason = itemView.findViewById(R.id.adapter_fragment_reason);
            adapter_fragment_date = itemView.findViewById(R.id.adapter_fragment_date);
        }
    }
}