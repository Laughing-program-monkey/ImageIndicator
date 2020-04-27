package com.indicator.ImageIndicator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.indicator.bean.ImageBean;
import com.indicator.indicator.ImageIndicatorView;
import com.indicator.indicator.ViewPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project : AnimationImageTest
 * package : com.user.animationimagetest
 * author  ：Administrator
 * date    : 2020/4/21 16:32
 */
public class IndicatorActivity extends Activity {
    @BindView(R2.id.viewpager)
    ViewPager mViewPager;
    @BindView(R2.id.indicator)
    ImageIndicatorView indicator;
    @BindView(R2.id.indicator1)
    ImageIndicatorView indicator1;
    @BindView(R2.id.indicator2)
    ImageIndicatorView indicator2;
    @BindView(R2.id.indicator3)
    ImageIndicatorView indicator3;
    @BindView(R2.id.indicator4)
    ImageIndicatorView indicator4;
    @BindView(R2.id.indicator5)
    ImageIndicatorView indicator5;
    @BindView(R2.id.indicator6)
    ImageIndicatorView indicator6;
    @BindView(R2.id.indicator7)
    ImageIndicatorView indicator7;
    @BindView(R2.id.checkbox)
    RadioButton checkBox;
    @BindView(R2.id.checkbox1)
    RadioButton checkBox1;
    @BindView(R2.id.checkbox2)
    RadioButton checkBox2;
    @BindView(R2.id.checkbox3)
    RadioButton checkBox3;
    @BindView(R2.id.checkbox4)
    RadioButton checkBox4;
    @BindView(R2.id.checkbox5)
    RadioButton checkBox5;
    @BindView(R2.id.checkbox6)
    RadioButton checkBox6;
    private List<View> list = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<ImageBean> mImageBeans = new ArrayList<>();
    private List<ImageBean> mImageBeans1 = new ArrayList<>();
    private List<ImageBean> mImageBeans2 = new ArrayList<>();
    private List<ImageBean> mImageBeans3 = new ArrayList<>();
    private List<RadioButton> mCheckBoxes = new ArrayList<>();
    private List<ImageIndicatorView> mNormalIndicators = new ArrayList<>();
    private RadioButton lastRadioButton;
    private MagicIndicator mMagicIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mNormalIndicators.add(indicator);
        mNormalIndicators.add(indicator1);
        mNormalIndicators.add(indicator2);
        mNormalIndicators.add(indicator3);
        mNormalIndicators.add(indicator4);
        mNormalIndicators.add(indicator5);
        mNormalIndicators.add(indicator6);
        mNormalIndicators.add(indicator7);

        lastRadioButton = checkBox;
        mCheckBoxes.add(checkBox);
        mCheckBoxes.add(checkBox1);
        mCheckBoxes.add(checkBox2);
        mCheckBoxes.add(checkBox3);
        mCheckBoxes.add(checkBox4);
        mCheckBoxes.add(checkBox5);
        mCheckBoxes.add(checkBox6);
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.viewpager_item, null);
            list.add(view);
        }
        //添加文字指示器数据
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                title.add("测试一下");
            } else {
                title.add("测试");
            }

        }
        //添加一般图片指示器数据
        for (int i = 0; i < 10; i++) {
            ImageBean<Integer> imageBean = new ImageBean().setSelectResorce(R.mipmap.bg_contribute).setUnselectResorce(R.mipmap.bg_guard);
            mImageBeans.add(imageBean);
        }
        //添加GIF图片指示器数据
        for (int i = 0; i < 10; i++) {
            ImageBean<Integer> imageBean = new ImageBean().setSelectResorce(R.mipmap.gifimage).setUnselectResorce(R.mipmap.bg_guard);
            mImageBeans1.add(imageBean);
        }
        //添加SVGA图片指示器数据
        for (int i = 0; i < 10; i++) {
            ImageBean<String> imageBean = new ImageBean().setSelectResorce("cut_down").setUnselectResorce(R.mipmap.bg_guard).setThumResorce(R.mipmap.bg_contribute);
            mImageBeans2.add(imageBean);
        }
        //添加JSON图片指示器数据
        for (int i = 0; i < 10; i++) {
            ImageBean<String> imageBean = new ImageBean().setSelectResorce("loading_net.json").setUnselectResorce(R.mipmap.bg_guard).setThumResorce(R.mipmap.bg_contribute);
            mImageBeans3.add(imageBean);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(list);
        mViewPager.setAdapter(adapter);
        mMagicIndicator=indicator7.getThirdIndicator();
        //文字指示器+画出底部导航栏
        indicator.setTitleDatas(title);
        indicator.setViewPager(mViewPager);
        //文字指示器+图片导航栏
        indicator1.setTitleDatas(title);
        //一般图片指示器+画出导航栏
        indicator2.setImageBeans(mImageBeans);
        //一般图片指示器+图片导航栏
        indicator3.setImageBeans(mImageBeans);
        //GIF图片指示器+画出导航栏
        indicator4.setImageBeans(mImageBeans1);
        //SVGA图片指示器+画出导航栏
        indicator5.setImageBeans(mImageBeans2);
        //JSON图片指示器+画出导航栏
        indicator6.setImageBeans(mImageBeans3);
        //初始化第三方指示器
        initIndicator();
    }

    @OnClick({R2.id.checkbox, R2.id.checkbox1, R2.id.checkbox2, R2.id.checkbox3, R2.id.checkbox4, R2.id.checkbox5, R2.id.checkbox6})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.checkbox) {
            init(0);
        } else if (id == R.id.checkbox1) {
            init(1);
        } else if (id == R.id.checkbox2) {
            init(2);
        } else if (id == R.id.checkbox3) {
            init(3);
        } else if (id == R.id.checkbox4) {
            init(4);
        } else if (id == R.id.checkbox5) {
            init(5);
        } else if (id == R.id.checkbox6) {
            init(6);
        }
    }

    //初始化
    public void init(int index) {
        lastRadioButton.setChecked(false);
        mCheckBoxes.get(index).setChecked(true);
        mViewPager.setCurrentItem(0);
        lastRadioButton = mCheckBoxes.get(index);
        if (index == 7) {

            return;
        }
        mNormalIndicators.get(index).setViewPager(mViewPager);

    }
    //初始化导航栏
    public  void initIndicator(){
        CommonNavigator commonNavigator=new CommonNavigator(this);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ClipPagerTitleView clipPagerTitleView=new ClipPagerTitleView(context);
                clipPagerTitleView.setText(title.get(index));
                clipPagerTitleView.setClipColor(Color.RED);
                clipPagerTitleView.setTextColor(Color.GRAY);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index,true);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator=new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setColors(Color.RED);
                linePagerIndicator.setLineHeight(10);
                linePagerIndicator.setLineWidth(100);//必须设置精准模式才起作用
                linePagerIndicator.setRoundRadius(5);
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
         ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        //设置页数
       // mViewPager.setCurrentItem(0);
    }
}
