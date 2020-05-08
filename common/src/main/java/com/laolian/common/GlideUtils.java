package com.laolian.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 19:07
 */
public class GlideUtils {

    /**
     * 加载网络图片
     *
     * @param context Context
     * @param url     图片地址
     */
    public static void LoaderImg(@NonNull Context context, @NonNull ImageView imageVIew, String url) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_images_fail)
                .placeholder(R.drawable.default_images_loading)
                .into(imageVIew);
    }


    /**
     * 清空内存缓存
     *
     * @param context Context
     */
    public static void clearMemory(@NonNull Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 清空内存缓存
     *
     * @param context Context
     */
    public static void clearDiskCache(@NonNull Context context) {
        Glide.get(context).clearDiskCache();
    }

}
