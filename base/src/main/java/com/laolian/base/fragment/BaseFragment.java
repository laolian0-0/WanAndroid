package com.laolian.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.laolian.base.activity.IBaseView;
import com.laolian.base.loadsir.EmptyCallback;
import com.laolian.base.loadsir.ErrorCallback;
import com.laolian.base.loadsir.LoadingCallback;
import com.laolian.base.viewmodel.BaseViewModel;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * fragment基类  mvvm
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/14 12:18
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;
    /**
     * layout id
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * viewModel
     * @return
     */
    protected abstract VM getViewModel();
    protected abstract int getBindingVariable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (viewModel != null) {
            viewModel.attachUI(this);
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
            viewDataBinding.executePendingBindings();
        }
    }

    /***
     *   初始化参数，在{@link #onCreateView}之前
     */
    protected void initParameters() {
    }
    /**
     * 网络错误 无数据 重新加载逻辑
     * */
    protected abstract void onReTryBtnClick();
    //设置网络请求异常页面替换的view
    public void setLoadSir(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // 重新加载逻辑
                showLoading();
                onReTryBtnClick();
            }
        });
    }
    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (viewModel != null && viewModel.isUIAttached()){
            viewModel.detachUI();
        }
    }
}
