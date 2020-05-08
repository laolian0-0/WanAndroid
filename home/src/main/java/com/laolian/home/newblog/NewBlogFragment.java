package com.laolian.home.newblog;

import android.os.Bundle;
import android.view.View;

import com.laolian.base.fragment.BasePagingFragment;
import com.laolian.base.util.RxBus;
import com.laolian.home.R;
import com.laolian.home.databinding.FragmentNewBlogBinding;
import com.laolian.home.event.HomeEvent;
import com.laolian.home.newblog.adapter.NewBlogAdapter;
import com.laolian.home.newblog.adapter.NewBlogBean;
import com.laolian.home.newblog.adapter.NewBlogItemView;
import com.laolian.home.views.CeilingRefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 最新博文
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 18:46
 */
public class NewBlogFragment extends BasePagingFragment<FragmentNewBlogBinding, NewBlogViewModel> implements NewBlogViewModel.INewBlogView, OnRefreshLoadMoreListener {


    private NewBlogAdapter newBlogAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_blog;
    }

    @Override
    protected NewBlogViewModel getViewModel() {
        return new NewBlogViewModel(getContext());
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onReTryBtnClick() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.smartRefresh.setOnRefreshLoadMoreListener(this);
        newBlogAdapter = new NewBlogAdapter(null);
        viewDataBinding.recyclerView.setAdapter(newBlogAdapter);
        viewDataBinding.smartRefresh.setHeaderMaxDragRate(5);
        viewDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        CeilingRefreshHeader refreshHeader = new CeilingRefreshHeader(getContext(), null);
        viewDataBinding.smartRefresh.setRefreshHeader(refreshHeader);
        refreshHeader.setTwoLevelRefreshListener(() -> RxBus.getDefault().post(HomeEvent.SCROLL_TO_HEADER));
    }


    @Override
    public void updateBlog(List<NewBlogBean.DatasBean> datas, boolean isRefresh, boolean hasNextPage) {
        viewDataBinding.smartRefresh.setEnableLoadMore(hasNextPage);
        newBlogAdapter.setData(datas);
        if (isRefresh) {
            viewDataBinding.smartRefresh.finishRefresh();
        } else {
            viewDataBinding.smartRefresh.finishLoadMore();
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
