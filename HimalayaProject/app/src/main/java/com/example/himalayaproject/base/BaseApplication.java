package com.example.himalayaproject.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;


public class BaseApplication extends Application {
    private static Handler sHandler = null;

    private static Context sContext = null;

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
        //初始化播放器
        XmPlayerManager.getInstance(this).init();

        //初始化LogUtils
        LogUtil.init(this.getPackageName(), false);

        sHandler = new Handler(Looper.myLooper());
        sContext = getBaseContext();
    }
    public static Handler getHandler(){
        return sHandler;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
