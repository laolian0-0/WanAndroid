package com.laolian.home.newproject;


import com.laolian.base.model.BasePagingModel;
import com.laolian.home.api.HomeApi;
import com.laolian.home.newproject.adapter.NewProjectBean;
import com.laolian.network.beans.BaseResponse;
import com.laolian.network.errorhandler.ExceptionHandle;
import com.laolian.network.observer.BaseObserver;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 13:52
 */
public class NewProjectModel extends BasePagingModel<NewProjectBean> {

    @Override
    public void refresh() {
        isRefresh = true;
        load();
    }

    @Override
    public void loadNexPage() {
        isRefresh = false;
        load();
    }

    @Override
    protected void load() {
        HomeApi.getInstance().getNewProject(new BaseObserver<BaseResponse<NewProjectBean>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message, isRefresh);
            }

            @Override
            public void onNext(BaseResponse<NewProjectBean> baseResponse) {
                if (!isRefresh) {
                    pageNumber++;//请求成功了，才把页码更新
                }
                NewProjectBean bean = baseResponse.getData();
                loadSuccess(bean, bean.getDatas().size() == 0, isRefresh, !bean.isOver());
            }
        }, String.valueOf(isRefresh ? pageNumber = 0 : pageNumber + 1));
    }


}
