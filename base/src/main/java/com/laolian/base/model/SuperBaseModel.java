package com.laolian.base.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.laolian.base.util.GsonUtils;
import com.laolian.base.util.LogUtils;
import com.laolian.base.util.preference.BasicDataPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

import androidx.annotation.CallSuper;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 14:50
 */
public abstract class SuperBaseModel<T> {
    protected Handler mUiHandler = new Handler(Looper.getMainLooper());
    protected ReferenceQueue<IBaseModelListener> mReferenceQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> mWeakListenerArrayList;
    private BaseCacheData<T> mData;
    protected CompositeDisposable compositeDisposable;

    /**
     * 有分页和无分页的实现方式可能不一样。所以交给子类去继承实现
     */
    protected interface IBaseModelListener {
    }

    ;

    public SuperBaseModel() {
        mReferenceQueue = new ReferenceQueue();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        if (getCachedPreferenceKey() != null) {
            mData = new BaseCacheData<>();
        }
    }

    /**
     * 该model的数据是否需要缓存，如果需要请返回缓存的key
     */
    protected String getCachedPreferenceKey() {
        return null;
    }

    ;

    /**
     * 由于渠道处在App的首页，为了保证app打开的时候由于网络慢或者异常的情况下tablayout不为空，
     * 所以app对渠道数据进行了预制；
     * 加载完成以后会立即进行网络请求，同时缓存在本地，今后app打开都会从preference读取，而不在读取
     * apk预制数据，由于渠道数据变化没那么快，在app第二次打开的时候会生效，并且是一天请求一次。
     */
    protected void saveDataToPreference(T data) {
        mData.data = data;
        mData.updateTimeInMills = System.currentTimeMillis();
        BasicDataPreferenceUtil.getInstance().setString(getCachedPreferenceKey(), GsonUtils.toJson(mData));
    }

    /**
     * 注册监听
     *
     * @param listener
     */
    public void register(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (this) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends IBaseModelListener> releaseListener = null;
            while ((releaseListener = mReferenceQueue.poll()) != null) {
                mWeakListenerArrayList.remove(releaseListener);
            }

            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener iBaseModelListener = weakListener.get();
                if (listener == iBaseModelListener) {
                    return;
                }
            }
            WeakReference<IBaseModelListener> weakListener = new WeakReference<>(listener, mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }
    }

    /**
     * 取消监听
     *
     * @param listener
     */
    public void unRegister(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener iBaseModelListener = weakListener.get();
                if (listener == weakListener.get()) {
                    mWeakListenerArrayList.remove(iBaseModelListener);
                    break;
                }
            }

        }
    }
    /**刷新数据*/
    public abstract void refresh();
    /**加载数据*/
    protected abstract void load();

    /**
     * 缓存数据通知
     * @param data
     */
    protected abstract void notifyCachedData(T data);

    /**
     * 缓存的数据的类型
     */
    protected Type getTClass() {
        return null;
    }

    /**
     * 该model的数据是否有apk预制的数据，如果有请返回，默认没有
     */
    protected String getApkString() {
        return null;
    }

    /**
     * 是否更新数据，可以在这里设计策略，可以是一天一次，一月一次等等，
     * 默认是每次请求都更新
     */
    protected boolean isNeedToUpdate() {
        return true;
    }

    /**
     * 注销订阅
     */
    @CallSuper
    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(d);
    }

    /**
     * 获取缓存和加载数据
     */
    public void getCacheDataAndLoad() {
        if (getCachedPreferenceKey() != null) {
            String saveDataString = BasicDataPreferenceUtil.getInstance().getString(getCachedPreferenceKey());
            if (!TextUtils.isEmpty(saveDataString)) {
                try {
                    T saveData = GsonUtils.fromLocalJson(new JSONObject(saveDataString).getString("data"), getTClass());
                    if (saveData != null) {
                        notifyCachedData(saveData);
                        if (isNeedToUpdate()) {
                            load();
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (getApkString() != null) {
                notifyCachedData((T) GsonUtils.fromLocalJson(getApkString(), getTClass()));
            }
        }
        load();
    }
}
