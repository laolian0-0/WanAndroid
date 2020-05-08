package com.laolian.network;

import java.util.HashMap;

/**
 * 请求信息
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/15 16:42
 */
public interface INetworkRequestInfo {

    /**
     * 请求头
     * @return HashMap
     */
    HashMap<String,String> getRequestHeaderMap();
    boolean isDebug();
}
