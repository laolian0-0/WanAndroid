package com.laolian.home.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.laolian.common.views.TitleSearchMoveListener;
import com.laolian.home.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 自定义吸顶效果view
 *
 * @author laolian Email : 3077075551@qq.com
 * @date 2020/3/21 15:25
 */
public class CeilingScrollView extends NestedScrollView {

    private TitleSearchMoveListener titleSearchMoveListener;
    /**
     * 可以隐藏的高度
     */
    private int hiddenHeight = 100;
    /**
     * 吸顶 固定头部的view
     */
    private int fixedViewHeight;
    /**
     * itemView 的top  y坐标
     */
    private int fixedViewTop;
    /**头部是否隐藏*/
    private boolean isHidden;

    private View mTopView;
    private ViewPager2 mViewPager;
    private View mFixedView;

    public CeilingScrollView(@NonNull Context context) {
        super(context);
    }

    public CeilingScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CeilingScrollView(@NonNull Context context, @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setSearchMoveListener(TitleSearchMoveListener searchMoveListener) {
        this.titleSearchMoveListener = searchMoveListener;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.banner);
        mFixedView = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.post(() -> {
            fixedViewHeight = mFixedView.getMeasuredHeight();
            fixedViewTop = mTopView.getMeasuredHeight();
            hiddenHeight = fixedViewTop + fixedViewHeight;
            isHidden = false;
        });

        setSmoothScrollingEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();

        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft()
                + getPaddingRight(), lp.width);

        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(parentHeightMeasureSpec + hiddenHeight, MeasureSpec.EXACTLY);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        // NestedScrollView 子view只有一个，默认子view 是unspecified，这里把子view的高度确定下来 exactly
        // 解决嵌套RecycleView 复用机制失效问题。
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec + hiddenHeight,
                getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }


    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                  int type) {
        if (!isHidden) {
            //不是隐藏的状态 就让父（this）消耗完子的滑动距离
            consumed[1] = dy;
            scrollBy(0, dy);
            dispatchNestedPreScroll(dx, dy, consumed, null, type);
        } else {
            super.onNestedPreScroll(target, dx, dy, consumed, type);
        }
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY,
                                  boolean clampedX, boolean clampedY) {
        scrollTo(scrollX, scrollY);
    }


    @Override
    public void scrollTo(int x, int y) {
        if (y <= 0) {
            y = 0;
        }
        if (isHidden) {
            if (y <= hiddenHeight) {
                return;
            }
        }
        // 根据隐藏的距离 计算 搜索框 是否应该移动 移动的距离
        if (y >= fixedViewTop && y <= hiddenHeight && titleSearchMoveListener != null) {
            titleSearchMoveListener.searchScrollY(y - fixedViewTop);
        } else if (y <= hiddenHeight && titleSearchMoveListener != null) {
            //修正搜索框的位置
            titleSearchMoveListener.searchScrollY(0);
        }
        if (y >= hiddenHeight) {
            //修正滑动距离
            y = hiddenHeight;
            isHidden = true;
            if (titleSearchMoveListener != null) {
                titleSearchMoveListener.searchScrollY(hiddenHeight);
                titleSearchMoveListener.searchScrollHide(true);
            }
        }
        super.scrollTo(x, y);
    }

    /**
     * 滑动最顶部 显示隐藏的头
     */
    public void scrollToHeader() {
        isHidden = false;
        scrollTo(0, 0);
        if (titleSearchMoveListener != null) {
            titleSearchMoveListener.searchScrollY(0);
            titleSearchMoveListener.searchScrollHide(false);
        }
    }

}
