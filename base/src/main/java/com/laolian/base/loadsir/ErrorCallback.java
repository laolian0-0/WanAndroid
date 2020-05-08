package com.laolian.base.loadsir;


import com.kingja.loadsir.callback.Callback;
import com.laolian.base.R;


public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
