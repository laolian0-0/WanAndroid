package com.laolian.base.model;

import java.lang.ref.WeakReference;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 16:46
 */
public abstract class BasePagingModel<T> extends SuperBaseModel<T> {
    protected boolean isRefresh = true;
    protected int pageNumber = 0;

    public interface IModelListener<T> extends IBaseModelListener {
        void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage);

        void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
    }

    protected void loadSuccess(T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                        for (WeakReference<IBaseModelListener> wekListener : mWeakListenerArrayList) {
                            if (wekListener.get() instanceof IModelListener) {
                                IModelListener listenerItem = (IModelListener) wekListener.get();
                                if (listenerItem != null) {
                                    listenerItem.onLoadFinish(BasePagingModel.this, data, isEmpty, isFirstPage, hasNextPage);
                                }
                            }
                        }
                        if (getCachedPreferenceKey() != null && isFirstPage) {
                            saveDataToPreference(data);
                        }
                    }
                    , 0);
        }
    }


    protected void loadFail(String prompt, boolean isFirstPage) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                        for (WeakReference<IBaseModelListener> wekListener : mWeakListenerArrayList
                        ) {
                            if (wekListener.get() instanceof IModelListener) {
                                IModelListener listenerItem = (IModelListener) wekListener.get();
                                if (listenerItem != null) {
                                    listenerItem.onLoadFail(BasePagingModel.this, prompt, isFirstPage);
                                }
                            }
                        }
                    }
                    , 0);
        }
    }


    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data, false, true, true);
    }

    /**
     * 加载下一页
     */
    abstract public void loadNexPage();
}
