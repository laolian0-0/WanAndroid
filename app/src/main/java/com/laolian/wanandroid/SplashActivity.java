package com.laolian.wanandroid;

import android.content.Intent;
import android.os.Bundle;


import com.billy.cc.core.component.CC;
import com.kingja.loadsir.core.LoadSir;
import com.laolian.base.loadsir.EmptyCallback;
import com.laolian.base.loadsir.ErrorCallback;
import com.laolian.base.loadsir.LoadingCallback;
import com.laolian.base.util.LogUtils;
import com.laolian.base.util.preference.PreferencesUtil;
import com.laolian.network.BaseApi;
import com.laolian.wanandroid.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 利用给SplashActivity设置带有背景的SplashTheme来避免启动白屏的问题
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
