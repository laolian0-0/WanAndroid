package com.laolian.home.newproject;

import com.laolian.base.fragment.IBasePagingView;
import com.laolian.base.model.BasePagingModel;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.home.newproject.adapter.NewProjectBean;


import java.util.ArrayList;
import java.util.List;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 13:52
 */
public class NewProjectViewModel extends BaseViewModel<NewProjectViewModel.INewProjectView,NewProjectModel> implements BasePagingModel.IModelListener<NewProjectBean> {


    List<NewProjectBean.DatasBean> datas = new ArrayList<>();

    public interface INewProjectView extends IBasePagingView {
        /**
         * 更新列表
         * @param datas
         * @param isRefresh 该次请求是否是刷新
         * @param hasNextPage 是否可以加载下一页
         */
        void updateProject(List<NewProjectBean.DatasBean> datas, boolean isRefresh, boolean hasNextPage);
    }


    public NewProjectViewModel() {
        model = new NewProjectModel();
        model.register(this);
    }


    @Override
    public void onLoadFinish(BasePagingModel model, NewProjectBean data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        if (getPageView() != null){
            if (model instanceof  NewProjectModel){
                if (isFirstPage){
                    datas.clear();
                }
                if (isEmpty){
                    if (isFirstPage) {
                        getPageView().onRefreshEmpty();
                    } else {
                        getPageView().onLoadMoreEmpty();
                    }
                }else {
                    datas.addAll(data.getDatas());
                    getPageView().updateProject(datas,isFirstPage,hasNextPage);
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
