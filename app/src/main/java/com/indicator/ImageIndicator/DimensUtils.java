package com.indicator.ImageIndicator;

import android.content.Context;

/**
 * project : AnimationImageTest
 * package : com.user.animationimagetest
 * author  ：Administrator
 * date    : 2020/4/6 9:40
 */
public class DimensUtils {
    private DimensUtils() {
    }

     public static int px2dip(Context context, float pxValue) {
         final float scale = context.getResources().getDisplayMetrics().density;
         return (int) (pxValue / scale + 0.5f);
     }

     public static int dip2px(Context context, float dipValue) {
         final float scale = context.getResources().getDisplayMetrics().density;
         return (int) (dipValue * scale + 0.5f);
     }

     public static int px2sp(Context context, float pxValue) {
         final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
         return (int) (pxValue / fontScale + 0.5f);
     }

     public static int sp2px(Context context, float spValue) {
         final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
         return (int) (spValue * fontScale + 0.5f);
     }
}
