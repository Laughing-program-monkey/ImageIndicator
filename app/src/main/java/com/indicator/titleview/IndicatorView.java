package com.indicator.titleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.indicator.Interface.IndicatorInterface;
import com.indicator.ImageIndicator.R;
import com.indicator.bean.PositionData;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * project : AnimationImageTest
 * package : com.user.indicator
 * author  ：Administrator
 * date    : 2020/4/23 14:18
 */
public class IndicatorView extends View implements IndicatorInterface {
    private Paint mPaint=new Paint();//画导航栏的画笔
    private RectF mRect=new RectF();//带圆角的矩形
    private int UndlineColor;//下划线的颜色
    private List<PositionData> mData;
    private int UndlineHeight;//下划线的高度
    private int mRadius;//下划线的圆弧半径
    private int paddingLeft;//下划线左边往内缩短的距离
    private int paddingRight;//下划线右边往内缩短的距离
    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    public void setUndlineHeight(int undlineHeight) {
        UndlineHeight = undlineHeight;
        invalidate();
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        invalidate();
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        invalidate();
    }
    public void setUndlineColor(int undlineColor) {
        UndlineColor = undlineColor;
        invalidate();
    }
    // 控制动画
    private Interpolator mStartInterpolator = new LinearInterpolator();

    public IndicatorView(Context context) {
        super(context);
    }
    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        mRadius = (int) typedArray.getDimension(R.styleable.IndicatorView_undlineRadius, 0);
        UndlineColor = typedArray.getColor(R.styleable.IndicatorView_undlineColor, Color.GREEN);
        UndlineHeight = (int) typedArray.getDimension(R.styleable.IndicatorView_undlineHeight, 10);
        paddingLeft=(int) typedArray.getDimension(R.styleable.IndicatorView_paddingLeft, 0);
        paddingRight=(int) typedArray.getDimension(R.styleable.IndicatorView_paddingRight, 0);
    }
    //初始化画笔
    public  void init(){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(UndlineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.drawRoundRect(mRect,mRadius,mRadius,mPaint);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mData == null || mData.isEmpty()) {
            return;
        }
        if(position+1<mData.size()) {
            int left = mData.get(position).mLeft;
            int nextLeft = mData.get(position + 1).mLeft;
            int right = mData.get(position).mRight;
            int nextRight = mData.get(position + 1).mRight;
            mRect.left = (int) (left + (nextLeft - left) * mStartInterpolator.getInterpolation(positionOffset))+paddingLeft;
            mRect.right = (int) (right + (nextRight - right) * mStartInterpolator.getInterpolation(positionOffset))- paddingRight;;
            mRect.top = 0;
            mRect.bottom = UndlineHeight;
            invalidate();
        }
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void GetPositionData(List<PositionData> mDatas) {
        mData=mDatas;
        Log.e("tbtgbtbvt",mData.size()+"---indicatorView");
        if(mData!=null && mData.size()>0) {
            mRect.left = mData.get(0).mLeft+paddingLeft;
            mRect.right = mData.get(0).mRight-paddingRight;
            mRect.top = 0;
            mRect.bottom = UndlineHeight;
        }
    }

    //获取IndicatorView的高度
    public int getUndlineHeight(){
        return UndlineHeight;
    }

}
