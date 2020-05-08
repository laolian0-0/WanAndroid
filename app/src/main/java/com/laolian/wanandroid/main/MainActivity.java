package com.laolian.wanandroid.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laolian.base.activity.BaseActivity;
import com.laolian.base.util.StatusBarUtil;
import com.laolian.base.util.ToastUtil;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.network.beans.BaseResponse;
import com.laolian.network.errorhandler.ExceptionHandle;
import com.laolian.network.observer.BaseObserver;
import com.laolian.wanandroid.R;
import com.laolian.wanandroid.api.AppApi;
import com.laolian.wanandroid.databinding.ActivityMainBinding;
import com.laolian.wanandroid.main.fragment.FourFragment;
import com.laolian.wanandroid.main.fragment.SecondFragment;
import com.laolian.wanandroid.main.fragment.ThirdFragment;

import java.util.List;


/**
 * @author laolian email : 3077075551@qq.com
 * @date 2020/3/11
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements
        BottomNavigationView.OnNavigationItemSelectedListener, MainViewModel.IMainView {

    private Fragment[] fragment = new Fragment[4];
    private Fragment fromFragment;
    private int fragmentIndex;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onReTryBtnClick() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        CCResult result = CC.obtainBuilder("Home")
                .setActionName("getHomeFragment")
                .build()
                .call();

        fragment[0] = fromFragment = (Fragment) result.getDataMap().get("fragment");
        fragment[1] = new SecondFragment();
        fragment[2] = new ThirdFragment();
        fragment[3] = new FourFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment[0]);
        transaction.commit();

        viewDataBinding.bottomView.setOnNavigationItemSelectedListener(this);
        viewDataBinding.titleLayout.setItemSearchClick(v -> {
            //TODO
            ToastUtil.show(MainActivity.this, "打开搜索页面");
        });
        viewDataBinding.titleLayout.setLeftClick(v -> {
            //TODO
            ToastUtil.show(MainActivity.this, "打开个人页面");
        });
        viewDataBinding.titleLayout.setRightClick(v -> {
            //TODO
            ToastUtil.show(MainActivity.this, "打开更多选项");
        });

        viewDataBinding.bottomView.post(new Runnable() {
            @Override
            public void run() {
                CC.obtainBuilder("Home")
                        .setActionName("TitleSearchMoveListener")
                        .addParam("TitleSearchMoveListener", viewDataBinding.titleLayout)
                        .build().call();
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment tempFragment = null;
        int newPosition;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_home:
                newPosition = 0;
                tempFragment = fragment[0];
                break;
            case R.id.menu_second:
                newPosition = 1;
                tempFragment = fragment[1];
                break;
            case R.id.menu_third:
                newPosition = 2;
                tempFragment = fragment[2];
                break;
            case R.id.menu_four:
                newPosition = 3;
                tempFragment = fragment[3];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        switchFragment(fromFragment, tempFragment);
        titleChange(newPosition, fragmentIndex);
        fragmentIndex = newPosition;
        fromFragment = tempFragment;
        return true;
    }

    /**
     * fragment切换
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from == null || to == null) {
            return;
        }
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from);
                transaction.add(R.id.container, to);
            } else {
                transaction.hide(from);
                transaction.show(to);
            }
            transaction.commit();
        }
    }

    private void titleChange(int position, int oldPosition) {
        viewDataBinding.titleLayout.saveSearchViewState(oldPosition);
        viewDataBinding.titleLayout.resetSearchViewState(position);
    }


    @Override
    public void updateHotSearch(List<HotSearch> datas) {
        if (viewDataBinding.titleLayout != null && datas != null) {
            String[] strs = new String[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                strs[i] = datas.get(i).getName();
            }
            viewDataBinding.titleLayout.setSearchDatas(strs);
        }
    }
}
