package com.laolian.home.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.laolian.base.util.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import androidx.annotation.NonNull;


/**
 * 自定义吸顶专用的 下来刷新头部 添加下来回弹顶部
 *
 * @author laolian Email : 3077075551@qq.com
 * @date 2020/3/23 0:26
 */
public class CeilingRefreshHeader extends ClassicsHeader {

    public interface TwoLevelRefreshListener {
        /**
         * 二级刷新完成
         */
        void twoLevelFinish();
    }

    private TwoLevelRefreshListener twoLevelRefreshListener;
    private boolean mEnableTwoLevel = true;

    public CeilingRefreshHeader(Context context, AttributeSet attrs) {
        super(context);
        mTextRelease = "释放立即刷新。继续下拉还有惊喜哦";
        mTextSecondary = "释放回弹至头部";
    }

    /**
     * 二级刷新监听（即是下拉回弹顶部样式刷新）
     *
     * @param twoLevelRefreshListener
     */
    public void setTwoLevelRefreshListener(
            TwoLevelRefreshListener twoLevelRefreshListener) {
        this.twoLevelRefreshListener = twoLevelRefreshListener;
    }

    /**
     * 设置是否开启二级刷新
     *
     * @param enabled 是否开启
     * @return TwoLevelHeader
     */
    public void setEnableTwoLevel(boolean enabled) {
        this.mEnableTwoLevel = enabled;
    }


    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
        kernel.requestFloorDuration(200);
        kernel.requestNeedTouchEventFor(this, false);
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
    }


    protected float mPercent = 0;
    protected float mMaxRate = 2.5f;
    protected float mFloorRate = 2.2f;
    protected float mRefreshRate = 1f;

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height,
                         int maxDragHeight) {
        if (mWrappedInternal != null && mWrappedInternal != this) {
            mWrappedInternal.onMoving(isDragging, percent, offset, height, maxDragHeight);
        }
        if (isDragging) {
            if (mPercent < mFloorRate && percent >= mFloorRate && mEnableTwoLevel) {
                mRefreshKernel.setState(RefreshState.ReleaseToTwoLevel);
            }
            mPercent = percent;
        }
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState,
                               @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        final View updateView = mLastUpdateText;
        switch (newState) {
            case None:
                updateView.setVisibility(mEnableLastTime ? VISIBLE : GONE);
            case PullDownToRefresh:
                mTitleText.setText(mTextPulling);
                arrowView.setVisibility(VISIBLE);
                arrowView.animate().rotation(0);
                break;
            case Refreshing:
            case RefreshReleased:
                mTitleText.setText(mTextRefreshing);
                arrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                mTitleText.setText(mTextRelease);
                arrowView.animate().rotation(180);
                break;
            case ReleaseToTwoLevel:
                mTitleText.setText(mTextSecondary);
                arrowView.animate().rotation(180);
                updateView.setVisibility(GONE);
                break;
            case Loading:
                arrowView.setVisibility(GONE);
                updateView.setVisibility(mEnableLastTime ? INVISIBLE : GONE);
                mTitleText.setText(mTextLoading);
                break;
            case TwoLevelReleased:
                if (mRefreshKernel != null) {
                    mRefreshKernel.startTwoLevel(false);
                    if (twoLevelRefreshListener != null) {
                        twoLevelRefreshListener.twoLevelFinish();
                    }
                }
                break;
        }
    }


}
