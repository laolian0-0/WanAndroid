package com.laolian.base.viewmodel;

/**
 *
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/14 12:35
 */
public interface IBaseViewModel<V> {

    void attachUI(V view);

    V getPageView();

    boolean isUIAttached();

    void detachUI();
}
