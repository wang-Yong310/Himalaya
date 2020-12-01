package com.example.himalayaproject.base;

import android.app.Application;

import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (DTransferConstants.isRelease) {
            CommonRequest mXimalaya = CommonRequest.getInstanse();
            String appSecret = "19c8428803e104ac93d7cf154521ae81";
            mXimalaya.setAppkey("920afe193099eed8e4321678157eea8e");
            mXimalaya.setPackid("com.example.himalayaproject");
            mXimalaya.init(this, appSecret);
        }
        //初始化LogUtils
        LogUtil.init(this.getPackageName(), false);
    }

}
