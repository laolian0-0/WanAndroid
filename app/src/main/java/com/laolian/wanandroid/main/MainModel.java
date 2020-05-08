package com.laolian.wanandroid.main;

import com.laolian.base.model.BaseModel;
import com.laolian.network.beans.BaseResponse;
import com.laolian.network.errorhandler.ExceptionHandle;
import com.laolian.network.observer.BaseObserver;
import com.laolian.wanandroid.api.AppApi;

import java.util.List;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/24 23:01
 */
public class MainModel extends BaseModel<List<HotSearch>> {
    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        AppApi.getInstance().getHotSearch(new BaseObserver<BaseResponse<List<HotSearch>>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(BaseResponse<List<HotSearch>> baseResponse) {
                loadSuccess(baseResponse.getData());
            }
        });
    }


}
