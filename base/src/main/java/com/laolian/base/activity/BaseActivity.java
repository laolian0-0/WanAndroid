package com.laolian.base.activity;

import android.os.Bundle;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.laolian.base.R;
import com.laolian.base.loadsir.EmptyCallback;
import com.laolian.base.loadsir.ErrorCallback;
import com.laolian.base.loadsir.LoadingCallback;
import com.laolian.base.util.StatusBarUtil;
import com.laolian.base.viewmodel.BaseViewModel;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * activity基类 mvvm
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/12 16:42
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;

    /**
     * layout id
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * viewModel
     *
     * @return
     */
    protected abstract VM getViewModel();

    protected abstract int getBindingVariable();

    private void initViewModel(){
        viewModel = getViewModel();
        if (viewModel!=null){
            viewModel.attachUI(this);
        }
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null) {
            viewModel = getViewModel();
        }
        if (getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        performDataBinding();
        StatusBarUtil.setTransparent(this);
    }

    protected abstract void onReTryBtnClick();

    //设置网络请求异常页面替换的view
    public void setLoadSir(View view) {
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // 重新加载逻辑
                onReTryBtnClick();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
    }

    @Override
    public void onRefreshEmpty() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
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
}
