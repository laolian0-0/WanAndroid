package com.laolian.common;

import android.content.Context;
import android.content.Intent;

import com.laolian.common.activity.BaseWebActivity;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/26 10:40
 */
public class ActivityJump {
    /**
     * 跳转到web
     * @param mContext
     * @param webUrl
     */
    public static void toWebView(Context mContext,String webUrl){
        Intent intent = new Intent(mContext, BaseWebActivity.class);
        intent.putExtra("webUrl",webUrl);
        mContext.startActivity(intent);
    }

}
