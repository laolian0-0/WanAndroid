package com.laolian.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.laolian.common.R;
import com.laolian.common.databinding.LayoutTitleBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

/**
 * title
 *
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/20 11:14
 */
public class TitleLayout extends ConstraintLayout implements TitleSearchMoveListener {

    private View itemSearch;
    private TextSwitchView tsSearch;
    private ImageView ivLeft;
    private ImageView ivRight;
    private View titleLayout;
    private int topY;
    private int bottomY;
    private List<TitleSearchState> searchStateList;
    private LayoutTitleBinding mBinding;


    public TitleLayout(Context context) {
        super(context);
        initView(null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.layout_title,this,true);
        titleLayout = mBinding.titleLayout;
        itemSearch = mBinding.itemSearch;
        tsSearch = mBinding.tsSearch;
        ivLeft = mBinding.ivLeft;
        ivRight = mBinding.ivRight;
        mBinding.getRoot().post(() -> {
            topY = mBinding.getRoot().getTop();
            bottomY = mBinding.getRoot().getBottom();
        });
        titleLayout.setBackgroundResource(R.color.colorPrimary);
        searchStateList = new ArrayList<>();//初始化 默认4
        searchStateList.add(new TitleSearchState(0, topY, false));
        searchStateList.add(new TitleSearchState(1, topY, false));
        searchStateList.add(new TitleSearchState(2, topY, false));
        searchStateList.add(new TitleSearchState(3, topY, false));
    }

    /**
     * 设置热搜
     * @param datas
     */
    public void setSearchDatas(String[] datas){
        if (datas == null){
            return;
        }
        tsSearch.setResources(datas);
    }

    public void setItemSearchClick(View.OnClickListener clickListener) {
        if (itemSearch != null) {
            itemSearch.setOnClickListener(clickListener);
        }
    }

    public void setLeftClick(View.OnClickListener clickListener) {
        if (ivLeft != null) {
            ivLeft.setOnClickListener(clickListener);
        }
    }

    public void setRightClick(View.OnClickListener clickListener) {
        if (ivRight != null) {
            ivRight.setOnClickListener(clickListener);
        }
    }

    @Override
    public void searchScrollY(int y) {
        if (itemSearch != null) {
            if (y >0 ){
                itemSearch.scrollTo(0, topY + y);
                titleLayout.setBackgroundResource(R.color.color_transparent);
            }else if ( y == 0 && itemSearch.getScaleX() != 0){
                //复位
                itemSearch.scrollTo(0, topY);
                titleLayout.setBackgroundResource(R.color.colorPrimary);
            }
        }
    }

    @Override
    public void searchScrollHide(boolean isHide) {
        if (itemSearch != null) {
            itemSearch.setVisibility(isHide ? GONE : VISIBLE);
            titleLayout.setBackgroundResource(isHide ? R.color.color_transparent : R.color.colorPrimary);
        }
    }

    /**
     * 恢复搜索框状态
     * @param position
     */
    public void resetSearchViewState(int position) {
        if (searchStateList.get(position) != null) {
            TitleSearchState titleSearchState = searchStateList.get(position);
            itemSearch.setVisibility(titleSearchState.isHidden  ? GONE : VISIBLE);
            itemSearch.scrollTo(0, titleSearchState.scrollY);
            if (titleSearchState.scrollY > 0){
                titleLayout.setBackgroundResource(R.color.color_transparent);
            }else {
                titleLayout.setBackgroundResource(R.color.colorPrimary);
            }
        }
    }

    /**
     * 保存搜索框状态信息
     * @param position
     */
    public void saveSearchViewState(int position) {
        if (searchStateList.get(position) != null) {
            TitleSearchState titleSearchState = searchStateList.get(position);
            titleSearchState.isHidden = !itemSearch.isShown();
            titleSearchState.scrollY = itemSearch.getScrollY();
        } else {
            searchStateList.add(new TitleSearchState(position, itemSearch.getScrollY(), !itemSearch.isShown()));
        }
    }

    /**
     * 搜索框的状态
     */
    class TitleSearchState {
        public int position;//对应的tab位置
        public int scrollY;// Y
        public boolean isHidden;//是否隐藏

        public TitleSearchState(int position, int scrollY, boolean isHidden) {
            this.position = position;
            this.scrollY = scrollY;
            this.isHidden = isHidden;
        }
    }
}
