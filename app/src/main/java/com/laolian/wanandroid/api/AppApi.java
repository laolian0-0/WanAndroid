package com.laolian.wanandroid.api;

import com.laolian.network.BaseApi;
import com.laolian.network.beans.BaseResponse;
import com.laolian.wanandroid.main.HotSearch;

import java.util.List;

import io.reactivex.Observer;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/24 22:47
 */
public class AppApi extends BaseApi {

    private static AppApi instance;
    private IAppApi iAppApi;

    public static AppApi getInstance() {
        if (instance == null) {
            synchronized (IAppApi.class) {
                if (instance == null) {
                    instance = new AppApi();
                }
            }
        }
        return instance;
    }

    private AppApi() {
        super("https://wanandroid.com/");
        iAppApi = retrofit.create(IAppApi.class);
    }

    public void getHotSearch(Observer<BaseResponse<List<HotSearch>>> observer) {
        apiSubscribe(iAppApi.getHotSearch(), observer);
    }

}
