package com.laolian.base;

import android.app.Application;

import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.laolian.base.loadsir.EmptyCallback;
import com.laolian.base.loadsir.ErrorCallback;
import com.laolian.base.loadsir.LoadingCallback;
import com.laolian.base.util.LogUtils;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/11 22:26
 */
public abstract class BaseApplication extends Application {
    public static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;


    }

}
