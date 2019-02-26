package com.pwang.wanandroid.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/02/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.VH> {

    private List<T> data;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(@NonNull List<T> data) {
        this.data = data;
    }

    public static class VH extends RecyclerView.ViewHolder{

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
