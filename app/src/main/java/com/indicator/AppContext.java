package com.indicator;

import android.app.Application;


/**
 * Created by cxf on 2017/8/3.
 */
public class AppContext extends Application {
    public static AppContext sInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }
    public static AppContext getInstance() {
        return sInstance;
    }
}
