package com.laolian.home.newblog.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.laolian.base.customview.BaseCustomView;
import com.laolian.base.customview.ICustomViewActionListener;
import com.laolian.common.ActivityJump;
import com.laolian.home.R;
import com.laolian.home.databinding.AdapterNewBlogBinding;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/25 16:22
 */
public class NewBlogItemView extends BaseCustomView<AdapterNewBlogBinding,NewBlogBean.DatasBean> {

    public NewBlogItemView(Context context) {
        super(context);
    }

    public NewBlogItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewBlogItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_new_blog;
    }

    @Override
    protected void setDataToView(NewBlogBean.DatasBean data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    protected void onRootClick(View view) {
        ActivityJump.toWebView(getContext(),getViewModel().getLink());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setStyle(int resId) {

    }

}
