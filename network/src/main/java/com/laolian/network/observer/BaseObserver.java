package com.laolian.network.observer;

import com.laolian.base.model.SuperBaseModel;
import com.laolian.network.errorhandler.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *  观察者基类
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/16 14:20
 */
public abstract class BaseObserver<T> implements Observer<T> {

    SuperBaseModel baseModel;

    public BaseObserver(SuperBaseModel baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseModel!=null){
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);
}
