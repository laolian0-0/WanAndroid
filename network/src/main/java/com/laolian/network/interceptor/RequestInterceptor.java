package com.laolian.network.interceptor;

import android.text.TextUtils;

import com.laolian.network.INetworkRequestInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求截拦
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/15 17:00
 */
public class RequestInterceptor implements Interceptor {
    private INetworkRequestInfo networkRequestInfo;

    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.networkRequestInfo = networkRequestInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        /*添加头信息*/
        if (networkRequestInfo != null) {
            for (String key : networkRequestInfo.getRequestHeaderMap().keySet()) {
                if (!TextUtils.isEmpty(networkRequestInfo.getRequestHeaderMap().get(key))) {
                    builder.addHeader(key, networkRequestInfo.getRequestHeaderMap().get(key));
                }
            }
        }
        return chain.proceed(builder.build());
    }
}
