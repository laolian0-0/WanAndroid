package com.laolian.base.fragment;


import com.laolian.base.activity.IBaseView;

/**
 *
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/14 12:25
 */
public interface IBasePagingView extends IBaseView {
    /**
     * 加载更多失败
     */
    void onLoadMoreFailure(String msg);
    /**
     * 加载更多无数据
     */
    void onLoadMoreEmpty();


}
