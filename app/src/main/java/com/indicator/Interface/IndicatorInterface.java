package com.indicator.Interface;

import com.indicator.bean.ImageBean;
import com.indicator.bean.PositionData;

import java.util.List;

/**
 * project : AnimationImageTest
 * package : com.user.Interface
 * author  ：Administrator
 * date    : 2020/4/23 14:24
 */
//绑定ViewPager滑动的接口
public interface IndicatorInterface {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    void onPageSelected(int position);
    void onPageScrollStateChanged(int state);
    //拿到文字属性集合
    default void GetPositionData(List<PositionData> mDatas){};
    //获取文字指示器的数据
    default void  getTitleData(List<String> TitleData){};
    //获取图片指示器数据
    default  void getImageData(List<ImageBean> imageBeans){};
}
