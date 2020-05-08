package com.laolian.base.model;

import java.io.Serializable;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 14:55
 */
public class BaseCacheData<T> implements Serializable {
    public long updateTimeInMills;
    public T data;
}
