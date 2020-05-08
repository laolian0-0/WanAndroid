package com.laolian.home.main;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.laolian.base.fragment.BaseFragment;
import com.laolian.common.views.TitleLayout;
import com.laolian.home.R;
import com.laolian.home.databinding.FragmentHomeBinding;
import com.laolian.home.main.banner.HomeBannerAdapter;
import com.laolian.home.main.banner.HomeBannerBean;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 首页
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/14 10:51
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeViewModel.IHomeView {

    private HomeBannerAdapter homeBannerAdapter;
    private TitleLayout titleLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onReTryBtnClick() {
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewDataBinding.viewpager.setAdapter(new HomeViewPageAdapter(this));
        homeBannerAdapter = new HomeBannerAdapter(getContext(), null);
        viewDataBinding.banner.setAdapter(homeBannerAdapter);
        //绑定
        new TabLayoutMediator(viewDataBinding.tablayout, viewDataBinding.viewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("最新博文");
                    break;
                case 1:
                    tab.setText("最新项目");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }
        }).attach();
    }

    @Override
    public void showBanner(List<HomeBannerBean> banners) {
        homeBannerAdapter.setDatas(banners);
        viewDataBinding.banner.start();
        viewDataBinding.banner.setIndicator(new CircleIndicator(getContext()));
    }

    @Override
    public void getTitleLayout(TitleLayout titleLayout) {
        this.titleLayout = titleLayout;
        viewDataBinding.ceilingView.setSearchMoveListener(titleLayout);
    }

    @Override
    public void scrollToHeader() {
        //显示头部
        viewDataBinding.ceilingView.scrollToHeader();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewDataBinding.banner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewDataBinding.banner.stop();
    }
}
