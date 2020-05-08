package com.laolian.home.main.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.laolian.common.ActivityJump;
import com.laolian.common.GlideUtils;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/18 17:46
 */
public class HomeBannerAdapter extends BannerAdapter<HomeBannerBean, HomeBannerAdapter.BannerViewHolder> {

    private Context mContext;

    public HomeBannerAdapter(Context mContext, List<HomeBannerBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.mContext = mContext;
    }

    @Override
    public void setDatas(List<HomeBannerBean> datas) {
        super.setDatas(datas);
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, HomeBannerBean data, int position, int size) {
        GlideUtils.LoaderImg(mContext,holder.imageView,data.getImagePath());
        holder.imageView.setOnClickListener(v -> ActivityJump.toWebView(mContext,data.getUrl()));
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }


}
