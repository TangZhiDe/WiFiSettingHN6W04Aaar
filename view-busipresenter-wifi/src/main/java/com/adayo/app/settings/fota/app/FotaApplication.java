package com.adayo.app.settings.fota.app;

import android.content.Context;
import android.util.Log;
import com.adayo.app.base.BaseApplication;

/**
 * Created by foryou Y3434 on 2019/1/21.
 */

public class FotaApplication extends BaseApplication {
    private final static String TAG = "fotaappservice";
    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        Log.d(TAG, "onCreate: mAppContext="+mAppContext );

    }



    /**
     * 获取应用程序的抽象基类对象的方法
     * */
    public static Context getAppContext(){
        Log.d(TAG, "getAppContext: "+mAppContext);
        return mAppContext;
    }
}
