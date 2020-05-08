package com.laolian.home.api;

import com.laolian.home.main.banner.HomeBannerBean;
import com.laolian.home.newblog.adapter.NewBlogBean;
import com.laolian.home.newproject.adapter.NewProjectBean;
import com.laolian.network.BaseApi;
import com.laolian.network.beans.BaseResponse;

import java.util.List;

import io.reactivex.Observer;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 20:32
 */
public class HomeApi extends BaseApi {
    private static HomeApi instance;
    private IHomeApi iHomeApi;

    public static HomeApi getInstance() {
        if (instance == null) {
            synchronized (HomeApi.class) {
                if (instance == null) {
                    instance = new HomeApi();
                }
            }
        }
        return instance;
    }

    private HomeApi(){
        super("https://wanandroid.com/");
        iHomeApi = retrofit.create(IHomeApi.class);
    }



    public void getBanner(Observer<BaseResponse<List<HomeBannerBean>>> observer){
        apiSubscribe(iHomeApi.getBanner(),observer);
    }

    public void getNewBlog(Observer<BaseResponse<NewBlogBean>> observer, String index){
        apiSubscribe(iHomeApi.getHomeNewBlog(index),observer);
    }

    public void getNewProject(Observer<BaseResponse<NewProjectBean>> observer, String index){
        apiSubscribe(iHomeApi.getHomeNewProject(index),observer);
    }

}
