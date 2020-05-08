package com.laolian.home.newproject.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.laolian.base.customview.BaseCustomView;
import com.laolian.base.customview.ICustomViewActionListener;
import com.laolian.common.ActivityJump;
import com.laolian.common.GlideUtils;
import com.laolian.home.R;
import com.laolian.home.databinding.AdapterNewProjectBinding;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/25 17:52
 */
public class NewProjectItemView extends BaseCustomView<AdapterNewProjectBinding,NewProjectBean.DatasBean> {

    public NewProjectItemView(Context context) {
        super(context);
    }

    public NewProjectItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewProjectItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_new_project;
    }

    @Override
    protected void setDataToView(NewProjectBean.DatasBean data) {
        getDataBinding().setViewModel(data);
        GlideUtils.LoaderImg(getContext(),getDataBinding().ivIcon,data.getEnvelopePic());
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
