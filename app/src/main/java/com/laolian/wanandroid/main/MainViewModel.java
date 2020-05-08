package com.laolian.wanandroid.main;

import com.laolian.base.activity.IBaseView;
import com.laolian.base.model.BaseModel;
import com.laolian.base.model.SuperBaseModel;
import com.laolian.base.recycler.BaseViewHolder;
import com.laolian.base.viewmodel.BaseViewModel;
import com.laolian.wanandroid.databinding.ActivityMainBinding;

import java.util.List;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/24 22:57
 */
public class MainViewModel extends BaseViewModel<MainViewModel.IMainView, MainModel> implements MainModel.IModelListener<List<HotSearch>> {


    interface IMainView extends IBaseView {
        void updateHotSearch(List<HotSearch> datas);
    }

    public MainViewModel() {
        model = new MainModel();
        model.register(this);
        model.load();
    }

    @Override
    public void onLoadFinish(BaseModel model, List<HotSearch> datas) {
        if (getPageView() != null) {
            if (model instanceof MainModel) {
                getPageView().updateHotSearch(datas);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

    }
}
