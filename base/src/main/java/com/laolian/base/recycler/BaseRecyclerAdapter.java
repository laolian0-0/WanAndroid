package com.laolian.base.recycler;

import android.content.Context;
import android.view.ViewGroup;


import com.laolian.base.customview.BaseCustomView;
import com.laolian.base.customview.BaseCustomViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/25 0:33
 */
public abstract class BaseRecyclerAdapter<T extends BaseCustomViewModel, V extends BaseCustomView> extends RecyclerView.Adapter<BaseViewHolder> {


    protected List<T> datas;
    protected V itemView;

    public BaseRecyclerAdapter(List<T> datas) {
        this.datas = datas;
    }


    public void setData(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView =  onCreateItemView(parent.getContext());
        return new BaseViewHolder(itemView);
    }

    protected abstract V onCreateItemView(Context context);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(datas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
