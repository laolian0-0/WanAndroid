package com.laolian.base.activity;

/**
 *
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/12 18:44
 */
public interface IBaseView {
    /**内容页*/
    void showContent();
    /**显示加载页*/
    void showLoading();
    /**显示刷新数据为空的页面*/
    void onRefreshEmpty();
    /**显示刷新错误页面*/
    void onRefreshFailure(String message);
}
