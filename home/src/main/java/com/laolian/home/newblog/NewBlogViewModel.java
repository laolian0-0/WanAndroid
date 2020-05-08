package com.laolian.home.newblog;

import android.content.Context;

import com.laolian.base.fragment.IBasePagingView;
import com.laolian.base.model.BasePagingModel;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.home.newblog.adapter.NewBlogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 18:46
 */
public class NewBlogViewModel extends BaseViewModel<NewBlogViewModel.INewBlogView, NewBlogModel> implements BasePagingModel.IModelListener<NewBlogBean> {

    private List<NewBlogBean.DatasBean> datas = new ArrayList<>();

    public interface INewBlogView extends IBasePagingView {
        /**
         * 更新blog列表
         * @param datas
         * @param isRefresh 该次请求是否是刷新
         * @param hasNextPage 是否可以加载下一页
         */
        void updateBlog(List<NewBlogBean.DatasBean> datas,boolean isRefresh,boolean hasNextPage);
    }

    public NewBlogViewModel(Context mContext) {
        model = new NewBlogModel(mContext);
        model.register(this);
        model.getCacheDataAndLoad();
    }


    @Override
    public void onLoadFinish(BasePagingModel model, NewBlogBean data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        if (getPageView() != null) {
            if (model instanceof NewBlogModel) {
                if (isFirstPage) {
                    datas.clear();
                }
                if (isEmpty) {
                    if (isFirstPage) {
                        getPageView().onRefreshEmpty();
                    } else {
                        getPageView().onLoadMoreEmpty();
                    }
                } else {
                    datas.addAll(data.getDatas());
                    getPageView().updateBlog(datas,isFirstPage,hasNextPage);
                }
            }
        }
    }

    @Override
    public void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage) {
        if (getPageView() != null){
            if (isFirstPage){
                getPageView().onRefreshFailure(prompt);
            }else {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }

    public void tryToRefresh() {
        model.refresh();
    }

    public void tryToLoadNextPage() {
        model.loadNexPage();
    }


}
