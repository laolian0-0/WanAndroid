package com.laolian.wanandroid;

import com.billy.cc.core.component.CC;
import com.kingja.loadsir.core.LoadSir;
import com.laolian.base.BaseApplication;
import com.laolian.base.loadsir.EmptyCallback;
import com.laolian.base.loadsir.ErrorCallback;
import com.laolian.base.loadsir.LoadingCallback;
import com.laolian.base.util.LogUtils;
import com.laolian.base.util.preference.PreferencesUtil;
import com.laolian.network.BaseApi;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/11 22:28
 */
public class WanAndroidApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        //sp初始化
        PreferencesUtil.init(this);
        //设置默认请求信息
        BaseApi.setNetworkRequestInfo(new NetworkRequestInfo());
        //日志
        LogUtils.setIsDebug(BuildConfig.DEBUG);
        // 默认是false: 关闭状态
        CC.enableDebug(BuildConfig.DEBUG);
        // 默认是false: 关闭状态
        CC.enableVerboseLog(BuildConfig.DEBUG);
        // 默认是false: 关闭状态
        CC.enableRemoteCC(BuildConfig.DEBUG);
        //LoadSir 初始化，为页面添加默认的网络请求错误页面
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();

    }
}
