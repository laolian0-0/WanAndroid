package com.laolian.base.viewmodel;

import com.laolian.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import androidx.lifecycle.ViewModel;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/12 16:41
 */
public class BaseViewModel<V, M extends SuperBaseModel> extends ViewModel implements IBaseViewModel<V> {

    protected M model;
    private Reference<V> mUIRef;


    @Override
    public void attachUI(V view) {
        mUIRef = new WeakReference<>(view);
    }

    @Override
    public V getPageView() {
        if (mUIRef == null) {
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null) {
            mUIRef.clear();
            mUIRef = null;
        }
        if (model != null) {
            model.cancel();
            model = null;
        }
    }
}
