package com.laolian.home.newproject.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.laolian.base.recycler.BaseRecyclerAdapter;
import com.laolian.base.recycler.BaseViewHolder;
import com.laolian.common.GlideUtils;
import com.laolian.home.R;
import com.laolian.home.databinding.AdapterNewProjectBinding;

import java.text.MessageFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 16:32
 */
public class NewProjectAdapter extends BaseRecyclerAdapter<NewProjectBean.DatasBean,NewProjectItemView> {

    public NewProjectAdapter(List<NewProjectBean.DatasBean> datas ) {
        super(datas);
    }

    @Override
    protected NewProjectItemView onCreateItemView(Context context) {
        NewProjectItemView viewItem =  new NewProjectItemView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        viewItem.setLayoutParams(layoutParams);
        return viewItem;
    }
}
