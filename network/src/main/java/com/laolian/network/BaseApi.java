package com.laolian.network;

import com.laolian.network.errorhandler.AppDataErrorHandler;
import com.laolian.network.errorhandler.HttpErrorHandler;
import com.laolian.network.interceptor.RequestInterceptor;
import com.laolian.network.interceptor.ResponseInterceptor;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求base
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/16 7:42
 */
public abstract class BaseApi {
    protected Retrofit retrofit;
    protected static INetworkRequestInfo networkRequestInfo;
    private static RequestInterceptor requestInterceptor;
    private static ResponseInterceptor responseInterceptor;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();

    public BaseApi(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        requestInterceptor = new RequestInterceptor(requestInfo);
        responseInterceptor = new ResponseInterceptor();
    }


    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /*请求截拦 自定义*/
        builder.addInterceptor(requestInterceptor);
        /*响应截拦 自定义*/
        builder.addInterceptor(responseInterceptor);
        if (networkRequestInfo.isDebug()) {
            /*日志截拦*/
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    protected void apiSubscribe(Observable observable, Observer observer){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(sErrorTransformer)
                .subscribe(observer);
    }
    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }
}
