package com.laolian.home.api;

import com.laolian.home.main.banner.HomeBannerBean;
import com.laolian.home.newblog.adapter.NewBlogBean;
import com.laolian.home.newproject.adapter.NewProjectBean;
import com.laolian.network.beans.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 19:54
 */
interface IHomeApi {
    /**
     * 最新博客
     * @param index
     * @return
     */
    @GET("article/list/{index}/json")
    Observable<BaseResponse<NewBlogBean>> getHomeNewBlog(@Path("index") String index);

    /**
     * 最新项目
     *
     * @param index
     * @return
     */
    @GET("article/listproject/{index}/json")
    Observable<BaseResponse<NewProjectBean>> getHomeNewProject(@Path("index") String index);

    /**
     * 首页 广告banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<HomeBannerBean>>> getBanner();
}
