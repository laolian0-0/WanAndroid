package com.laolian.home.main;

import com.google.gson.reflect.TypeToken;
import com.laolian.base.model.BaseModel;
import com.laolian.home.api.HomeApi;
import com.laolian.home.main.banner.HomeBannerBean;
import com.laolian.network.beans.BaseResponse;
import com.laolian.network.errorhandler.ExceptionHandle;
import com.laolian.network.observer.BaseObserver;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 23:18
 */
public class HomeModel extends BaseModel<List<HomeBannerBean>> {

    @Override
    public void refresh() {

    }

    @Override
    protected void load() {
        HomeApi.getInstance().getBanner(new BaseObserver<BaseResponse<List<HomeBannerBean>>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message);
            }

            @Override
            public void onNext(BaseResponse<List<HomeBannerBean>> baseResponse) {
               loadSuccess(baseResponse.getData());
            }
        });
    }


    @Override
    protected String getCachedPreferenceKey() {
        return "home_banner";
    }

    @Override
    protected Type getTClass() {
        return new TypeToken<List<HomeBannerBean>>(){}.getType();
    }

    @Override
    protected String getApkString() {
        return "[\n" +
                "        {\n" +
                "            \"desc\": \"享学~\",\n" +
                "            \"id\": 29,\n" +
                "            \"imagePath\": \"https://wanandroid.com/blogimgs/99cdb2f0-5482-4ec0-81d6-2d8e00c34e0b.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 0,\n" +
                "            \"title\": \"你的APP，性能优化了吗？\",\n" +
                "            \"type\": 0,\n" +
                "            \"url\": \"https://mp.weixin.qq.com/s/W8wq0QgjvEHLIC5vaGIEbg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"\",\n" +
                "            \"id\": 6,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"我们新增了一个常用导航Tab~\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/navi\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"一起来做个App吧\",\n" +
                "            \"id\": 10,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"一起来做个App吧\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/blog/show/2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"\",\n" +
                "            \"id\": 20,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 2,\n" +
                "            \"title\": \"flutter 中文社区 \",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://flutter.cn/\"\n" +
                "        }\n" +
                "    ]";
    }

}
