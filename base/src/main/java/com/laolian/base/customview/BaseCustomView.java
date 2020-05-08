package com.laolian.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author laolian
 * Email : 3077075551@qq.com
 * @date 2020/3/25 14:55
 */
public abstract class BaseCustomView<T extends ViewDataBinding, S extends BaseCustomViewModel> extends ConstraintLayout implements ICustomView<S>, View.OnClickListener {

    private T dataBinding;
    private S viewModel;
    private ICustomViewActionListener mListener;
    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public View getRootView() {
        return dataBinding.getRoot();
    }

    public abstract @LayoutRes
    int getLayoutId();

    protected abstract void setDataToView(S data);

    protected abstract void onRootClick(View view);

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getLayoutId() != 0) {
            dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), this, false);
            dataBinding.getRoot().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, view, viewModel);
                    }
                    onRootClick(view);
                }
            });
        }
        this.addView(dataBinding.getRoot());
    }

    @Override
    public void setData(S data) {
        viewModel = data;
        setDataToView(viewModel);
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
        onDataUpdated();
    }

    protected void onDataUpdated() {
    }

    public T getDataBinding() {
        return dataBinding;
    }

    public S getViewModel() {
        return viewModel;
    }

    @Override
    public void setActionListener(ICustomViewActionListener listener) {
        this.mListener = listener;
    }
}
