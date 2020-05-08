package com.laolian.base.fragment;

import com.laolian.base.R;
import com.laolian.base.util.ToastUtil;
import com.laolian.base.viewmodel.BaseViewModel;

import androidx.databinding.ViewDataBinding;

/**
 * 分页的fragment
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 9:59
 */
public abstract class BasePagingFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V , VM > implements IBasePagingView{

    @Override
    public void onLoadMoreFailure(String msg) {
        ToastUtil.show(getContext(), msg);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show(getContext(), getString(R.string.no_more_data));
    }

}
