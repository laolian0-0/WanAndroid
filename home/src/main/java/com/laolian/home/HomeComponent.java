package com.laolian.home;

import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.laolian.base.util.RxBus;
import com.laolian.home.main.HomeFragment;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 *
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/14 10:43
 */
public class HomeComponent implements IComponent {

    @Override
    public String getName() {
        return "Home";
    }

    @Override
    public boolean onCall(CC cc) {
        String name = cc.getActionName();
        switch (name) {
            case "getHomeFragment":
                CCResult result = new CCResult();
                result.addData("fragment",new HomeFragment());
                CC.sendCCResult(cc.getCallId(),result);
                Log.e("test","getHomeFragment");
                return true;
            case "TitleSearchMoveListener":
                sendTitleSearchMoveListener(cc);
                return true;
            default:
                CC.sendCCResult(cc.getCallId(),CCResult.errorUnsupportedActionName());
                return false;
        }
    }

    private void sendTitleSearchMoveListener(CC cc) {
        RxBus.getDefault().post(cc.getParamItem("TitleSearchMoveListener"));
        CC.sendCCResult(cc.getCallId(),CCResult.success());
    }
}
