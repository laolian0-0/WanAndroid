package com.laolian.wanandroid.api;

import com.laolian.network.beans.BaseResponse;
import com.laolian.wanandroid.main.HotSearch;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/24 22:48
 */
interface IAppApi {

    /**
     * 搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotSearch>>> getHotSearch();

}
