package com.laolian.home.newblog.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.laolian.base.recycler.BaseRecyclerAdapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 8:54
 */
public class NewBlogAdapter extends BaseRecyclerAdapter<NewBlogBean.DatasBean, NewBlogItemView> {


    public NewBlogAdapter(List<NewBlogBean.DatasBean> datas) {
        super(datas);
    }

    @Override
    protected NewBlogItemView onCreateItemView(Context context) {
        NewBlogItemView viewItem = new NewBlogItemView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        viewItem.setLayoutParams(layoutParams);
        return viewItem;
    }

}
