package com.indicator.titleview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indicator.Interface.IndicatorInterface;
import com.indicator.bean.PositionData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * project : AnimationImageTest
 * package : com.user.titleview
 * author  ：Administrator
 * date    : 2020/4/24 9:23
 */
public class TextTitleView extends LinearLayout implements IndicatorInterface {
    private List<String> TitleDatas;
    private List<PositionData> mPositionData=new ArrayList<>();
    private List<View> mViewList=new ArrayList<>();
    private int TextPaddingLeft;//设置文字的左内边距
    private int TextPaddingTop;//设置文字的上内边距
    private int TextPaddingRight;//设置文字的右内边距
    private int TextPaddingBottom;//设置文字的底内边距
    private int TextSize;///设置文字的大小
    private int SelectTextColor;//设置文字选中的颜色
    private int UnSelectTextColor;//设置文字未选择的颜色
    private boolean TextBold;//设置文字是否加粗
    private TitleClick mTitleClick;
    private int width;
    private boolean first=true;
    private int lastPosition;

    public TextTitleView(Context context) {
        super(context);
    }
    public TextTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextTitleView setTextPadding(int textPaddingLeft,int textPaddingRight,int textPaddingTop,int textPaddingBottom) {
        TextPaddingLeft = textPaddingLeft;
        TextPaddingRight = textPaddingRight;
        TextPaddingTop = textPaddingTop;
        TextPaddingBottom = textPaddingBottom;
        return this;
    }
    public TextTitleView setTextSize(int textSize) {
        TextSize = textSize;
        return  this;
    }

    public TextTitleView setSelectTextColor(int selectTextColor) {
        SelectTextColor = selectTextColor;
        return  this;
    }

    public TextTitleView setUnSelectTextColor(int unSelectTextColor) {
        UnSelectTextColor = unSelectTextColor;
        return  this;
    }

    public TextTitleView setTextBold(boolean textBold) {
        TextBold = textBold;
        return this;
    }
    public TextTitleView setTitleClick(TitleClick titleClick){
        mTitleClick=titleClick;
        return  this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(first) {
            mPositionData.clear();
            width = 0;
            for (int i = 0; i < mViewList.size(); i++) {
                PositionData data = new PositionData();
                View v = this.getChildAt(i);
                if (v != null) {
                    Log.e("etertertertr", v.getMeasuredWidth() + "");
                    data.mLeft = width;
                    width = width + v.getMeasuredWidth();
                    data.mRight = width;
                    data.mTop = v.getTop();
                    data.mBottom = v.getBottom();
                }
                mPositionData.add(data);
            }
            if(mTitleClick!=null){
                Log.e("dwedwedwecw",mPositionData.size()+"size");
                mTitleClick.setPositionData();
            }
            first=false;
        }
    }
    //返回标题属性坐标
    public List<PositionData> getPositionData(){
        return  mPositionData;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setBold(position);
        changeColor(position);
        lastPosition=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void getTitleData(List<String> TitleData) {

        TitleDatas=TitleData;
        initIndicator();
    }
    //初始化文字指示器
    public void initIndicator() {
        if (TitleDatas != null) {
            mViewList.clear();
            for (int i = 0; i < TitleDatas.size(); i++) {
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setPadding(TextPaddingLeft, TextPaddingTop, TextPaddingRight, TextPaddingBottom);
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                textView.setText(TitleDatas.get(i));
                textView.setTextSize(TextSize);
                if (i == 0) {
                    textView.setTextColor(SelectTextColor);
                    if (TextBold) {
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                } else {
                    textView.setTextColor(UnSelectTextColor);
                }
                addView(textView);
                mViewList.add(textView);
                final int finalI = i;
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mTitleClick!=null){
                            mTitleClick.titleClick(finalI);
                        }
                    }
                });
            }
        }
    }
    //文字指示器的字体加粗
    public void setBold(int position) {
        if (TextBold) {
            ((TextView) mViewList.get(lastPosition)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ((TextView) mViewList.get(position)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    //文字变更颜色
    public void changeColor(int position) {
        ((TextView) mViewList.get(lastPosition)).setTextColor(UnSelectTextColor);
        ((TextView) mViewList.get(position)).setTextColor(SelectTextColor);
    }
    //标题点击事件传递
    public interface  TitleClick{
        void titleClick(int position);
        void setPositionData();
    }
}
