package com.laolian.common.views;

/**
 * title 搜索框移动监听
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/21 15:49
 */
public interface TitleSearchMoveListener {
    /**
     * 垂直移动
     * @param y
     */
    void searchScrollY(int y);

    /**
     * 是否隐藏
     * @param isHide
     */
    void searchScrollHide(boolean isHide);

}
