package com.laolian.home.main;

import com.laolian.base.activity.IBaseView;
import com.laolian.base.model.BaseModel;
import com.laolian.base.util.RxBus;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.common.views.TitleLayout;
import com.laolian.home.event.HomeEvent;
import com.laolian.home.main.banner.HomeBannerBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/17 23:17
 */
public class HomeViewModel extends BaseViewModel<HomeViewModel.IHomeView, HomeModel> implements BaseModel.IModelListener<List<HomeBannerBean>> {
    List<HomeBannerBean> banners = new ArrayList<>();

    public HomeViewModel() {
        model = new HomeModel();
        model.register(this);
        model.getCacheDataAndLoad();
        Disposable disposable = RxBus.getDefault().register(TitleLayout.class, o -> {
            if (getPageView() == null ){
                return;
            }
            getPageView().getTitleLayout((TitleLayout) o);
        });
        Disposable disposable1 = RxBus.getDefault().register(String.class, o -> {
            String name = (String) o;
            if (name.equals(HomeEvent.SCROLL_TO_HEADER) && getPageView() != null) {
                getPageView().scrollToHeader();
            }
        });
        model.addDisposable(disposable);
        model.addDisposable(disposable1);
    }

    public void refresh(){
    }

    @Override
    public void onLoadFinish(BaseModel model, List<HomeBannerBean> data) {
        if (getPageView() == null ){
            return;
        }
        if (model instanceof HomeModel) {
            if (data != null) {
                banners.clear();
                banners.addAll(data);
                getPageView().showBanner(banners);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

    }

    public interface IHomeView extends IBaseView {
        void showBanner(List<HomeBannerBean> banners);
        /**获取到main的 titlelayout */
        void getTitleLayout(TitleLayout titleLayout);
        /**吸顶 滑动显示 headerview */
        void scrollToHeader();
    }

}
