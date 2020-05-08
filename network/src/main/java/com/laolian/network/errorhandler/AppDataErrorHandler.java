package com.laolian.network.errorhandler;

import io.reactivex.functions.Function;

import com.laolian.network.beans.BaseResponse;

/**
 * HandleFuc处理以下网络错误：
 * 1、应用数据的错误会抛RuntimeException；
 */
public class AppDataErrorHandler implements Function<BaseResponse, BaseResponse> {
    @Override
    public BaseResponse apply(BaseResponse response) throws Exception {
        //response中code码0 不会出现错误
        if (response instanceof BaseResponse && response.getErrorCode() != 0) {
            throw new RuntimeException(response.getErrorCode() + "" + (response.getErrorMsg() != null ? response.getErrorMsg() : ""));
        }
        return response;
    }
}