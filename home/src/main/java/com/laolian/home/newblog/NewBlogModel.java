package com.laolian.home.newblog;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.laolian.base.model.BasePagingModel;
import com.laolian.base.util.StreamUtils;
import com.laolian.home.R;
import com.laolian.home.api.HomeApi;
import com.laolian.home.newblog.adapter.NewBlogBean;
import com.laolian.network.beans.BaseResponse;
import com.laolian.network.errorhandler.ExceptionHandle;
import com.laolian.network.observer.BaseObserver;

import java.lang.reflect.Type;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 18:46
 */
public class NewBlogModel extends BasePagingModel<NewBlogBean> {

    private Context mContext;

    public NewBlogModel(Context mContext) {
        this.mContext = mContext;
    }

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
        HomeApi.getInstance().getNewBlog(new BaseObserver<BaseResponse<NewBlogBean>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message, isRefresh);
            }

            @Override
            public void onNext(BaseResponse<NewBlogBean> baseResponse) {
                if (!isRefresh) {
                    pageNumber++;//请求成功了，才把页码更新
                }
                NewBlogBean bean = baseResponse.getData();
                loadSuccess(bean, bean.getDatas().size() == 0, isRefresh, !bean.isOver());
            }
        }, String.valueOf(isRefresh ? pageNumber = 0 : pageNumber + 1));
    }

    @Override
    protected String getCachedPreferenceKey() {
        return "new_blog";
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<NewBlogBean>() {
        }.getType();
    }

    @Override
    protected String getApkString() {
        return StreamUtils.get(mContext, R.raw.newblog);
    }
}
