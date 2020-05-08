package com.laolian.wanandroid.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laolian.base.fragment.BaseFragment;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.wanandroid.R;
import com.laolian.wanandroid.databinding.FragmentOthersBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 描述
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/13 20:27
 */
public class FourFragment extends BaseFragment<FragmentOthersBinding,BaseViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_others;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.tvCenter.setText("第四页");
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onReTryBtnClick() {

    }
}
