package com.laolian.wanandroid;

import com.laolian.network.INetworkRequestInfo;

import java.util.HashMap;

/**
 * 默认请求头信息
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/15 16:52
 */
public class NetworkRequestInfo implements INetworkRequestInfo {
    HashMap<String,String> handler = new HashMap<>();

    public NetworkRequestInfo(){
        handler.put("os","android");
        handler.put("versionName",BuildConfig.VERSION_NAME);
        handler.put("versionCode",String.valueOf(BuildConfig.VERSION_CODE));
        handler.put("applicationId",BuildConfig.APPLICATION_ID);
    }

    @Override
    public HashMap<String, String> getRequestHeaderMap() {

        return handler;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
