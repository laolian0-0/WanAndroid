package com.laolian.home.newproject;

import android.os.Bundle;
import android.view.View;

import com.laolian.base.fragment.BasePagingFragment;
import com.laolian.base.util.RxBus;
import com.laolian.home.R;
import com.laolian.home.databinding.FragmentNewBlogBinding;
import com.laolian.home.event.HomeEvent;
import com.laolian.home.newproject.adapter.NewProjectAdapter;
import com.laolian.home.newproject.adapter.NewProjectBean;
import com.laolian.home.newproject.adapter.NewProjectItemView;
import com.laolian.home.views.CeilingRefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 最新项目
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 13:52
 */
public class NewProjectFragment extends BasePagingFragment<FragmentNewBlogBinding,NewProjectViewModel> implements NewProjectViewModel.INewProjectView, OnRefreshLoadMoreListener {

    private NewProjectAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_blog;
    }

    @Override
    protected NewProjectViewModel getViewModel() {
        return new NewProjectViewModel();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onReTryBtnClick() {
        viewModel.tryToRefresh();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.smartRefresh.setOnRefreshLoadMoreListener(this);
        adapter = new NewProjectAdapter(null);
        viewDataBinding.recyclerView.setAdapter(adapter);
        viewDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        setLoadSir(viewDataBinding.smartRefresh);
        showLoading();
        viewModel.tryToRefresh();
        CeilingRefreshHeader refreshHeader = new CeilingRefreshHeader(getContext(), null);
        viewDataBinding.smartRefresh.setRefreshHeader(refreshHeader);
        refreshHeader.setTwoLevelRefreshListener(() -> RxBus.getDefault().post(HomeEvent.SCROLL_TO_HEADER));
    }

    @Override
    public void updateProject(List<NewProjectBean.DatasBean> datas, boolean isRefresh, boolean hasNextPage) {
        viewDataBinding.smartRefresh.setEnableLoadMore(hasNextPage);
        if (datas != null && datas.size()>0){
            viewDataBinding.smartRefresh.finishRefresh();
            viewDataBinding.smartRefresh.finishLoadMore();
            adapter.setData(datas);
            showContent();
        }else {
            onRefreshEmpty();
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.tryToLoadNextPage();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.tryToRefresh();
    }
}
