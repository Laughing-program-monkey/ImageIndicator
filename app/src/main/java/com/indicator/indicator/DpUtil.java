package com.indicator.indicator;


import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.indicator.AppContext;

/**
 * Created by cxf on 2017/8/9.
 * dp转px工具类
 */

public class DpUtil {
    private int mScreenWdith;
    private static float scale;

    static {
        scale = AppContext.sInstance.getResources().getDisplayMetrics().density;
    }

    public static int dp2px(int dpVal) {//dp转px
        return (int) (scale * dpVal + 0.5f);
    }
    public static int px2dp(int pxVal){
        return (int)(pxVal/scale+0.5f);
    }
    //获取屏幕的宽度
    public static int getScreenWidth(){
       Resources mResources = AppContext.sInstance.getResources();
        DisplayMetrics dm = mResources.getDisplayMetrics();
     return  dm.widthPixels;
    }


}
