package com.laolian.common.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.laolian.common.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 自定义文字轮播view
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/20 12:26
 */
public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private int index= -1;
    private String[] resources = {"自定义View","性能优化","Java反射","OkHttp你知道多少？"};

    public TextSwitchView(Context context) {
        super(context);
        init();
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //实现ViewSwitcher.ViewFactory接口方法，创建出TextView并启动动画
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.in_animation));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.out_animation));
        postDelayed(runnable,2000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            index = next();
            updateText();
            postDelayed(runnable,2000);
        }
    };

    public void setResources(String[] res) {
        resources = res;
    }

    @Override
    public boolean postDelayed(Runnable action, long delayMillis) {
        return super.postDelayed(action, delayMillis);
    }

    private int next() {
        int flag = index + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        setText(resources[index]);
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(getContext());
        tv.setTextColor(Color.parseColor("#c4c4c4"));
        tv.setTextSize(22);
        return tv;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        super.setOnTouchListener(l);
    }
}
