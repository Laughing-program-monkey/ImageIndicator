package com.indicator.titleview;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.indicator.Interface.IndicatorInterface;
import com.indicator.bean.ImageBean;
import com.indicator.bean.PositionData;
import com.indicator.utils.SvgaUtils;

import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

/**
 * project : AnimationImageTest
 * package : com.user.titleview
 * author  ：Administrator
 * date    : 2020/4/24 17:11
 */
public class ImageTitleView extends LinearLayout implements IndicatorInterface {
    private int ImageWidth;//图片的宽度
    private int ImageHeight;//图片的高度
    private List<ImageBean> mImageBeans;//图片的数据
    private List<PositionData> mPositionData = new ArrayList<>();//图片的位置坐标
    private ImageClick mImageClick;
    private int ImageType;
    private boolean first = true;
    private int width;
    private List<View> mViewList = new ArrayList<>();
    private int LastPosition;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int dealType = 1;
    private ImageView.ScaleType mScaleType;
    private int imageCornerRadius;
    private int imageFrameSize;
    private int imageFrameColor;
    private boolean imageCorner;
    private boolean imageFrame;
    private Context mContext;
    private LayoutInflater mInflater;
    private int loopCount;//系统设置的播放次数
    private int count;//计数当前播放的次数
    private List<SvgaUtils> mSvgaUtilsData = new ArrayList<>();
    public ImageTitleView setDealType(int dealType) {
        this.dealType = dealType;
        switch (dealType) {
            case 1:
                mScaleType = ImageView.ScaleType.FIT_XY;
                break;
            case 6:
                mScaleType = ImageView.ScaleType.CENTER_CROP;
                break;
        }
        return this;
    }

    public ImageTitleView setPaddingData(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        return this;
    }

    public ImageTitleView setImageCornerRadius(int imageCornerRadius) {
        this.imageCornerRadius = imageCornerRadius;
        return this;
    }

    public ImageTitleView setImageFrameSize(int imageFrameSize) {
        this.imageFrameSize = imageFrameSize;
        return this;
    }

    public ImageTitleView setImageFrameColor(int imageFrameColor) {
        this.imageFrameColor = imageFrameColor;
        return this;
    }

    public ImageTitleView setImageCorner(boolean imageCorner) {
        this.imageCorner = imageCorner;
        return this;
    }

    public ImageTitleView setImageFrame(boolean imageFrame) {
        this.imageFrame = imageFrame;
        return this;
    }

    public ImageTitleView setImageType(int imageType) {
        ImageType = imageType;
        return this;
    }

    public ImageTitleView setImageWidth(int imageWidth) {
        ImageWidth = imageWidth;
        return this;
    }

    public ImageTitleView setImageHeight(int imageHeight) {
        ImageHeight = imageHeight;
        return this;
    }

    public ImageTitleView setImageClick(ImageClick imageClick) {
        mImageClick = imageClick;
        return this;
    }

    public ImageTitleView setLoopCount(int loopCount) {
        this.loopCount = loopCount;
        this.count = loopCount;
        return this;
    }

    public ImageTitleView(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public ImageTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (first) {
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
            if (mImageClick != null) {
                Log.e("dwedwedwecw", mPositionData.size() + "size");
                mImageClick.setImagePositionData();
            }
            first = false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ImageBean lastImageBean = mImageBeans.get(LastPosition);
        ImageBean currentImageBean = mImageBeans.get(position);
        switch (ImageType) {
            case 0://一般类型
                ((ImageView) mViewList.get(LastPosition)).setImageResource(lastImageBean.getUnselectResorce());
                ((ImageView) mViewList.get(position)).setImageResource((Integer) currentImageBean.getSelectResorce());
                break;
            case 1://SVGA类型
                ((SVGAImageView) mViewList.get(LastPosition)).pauseAnimation();
                mSvgaUtilsData.get(position).initAnimator();
                mSvgaUtilsData.get(position).startAnimator((String) mImageBeans.get(position).getSelectResorce());
                setSVGAPlayListener((SVGAImageView) mViewList.get(position), position);
                ((SVGAImageView) mViewList.get(LastPosition)).setImageResource(mImageBeans.get(LastPosition).getUnselectResorce());
                break;
            case 2://JSON类型
                count = loopCount;
                ((LottieAnimationView) mViewList.get(LastPosition)).cancelAnimation();
                ((LottieAnimationView) mViewList.get(LastPosition)).setImageResource(lastImageBean.getUnselectResorce());
                ((LottieAnimationView) mViewList.get(position)).setAnimation((String) currentImageBean.getSelectResorce());
                ((LottieAnimationView) mViewList.get(position)).playAnimation();
                setJSONPlayListener((LottieAnimationView) mViewList.get(position), position);
                break;
            case 3://GIF类型
                ((GifImageView) mViewList.get(LastPosition)).setImageResource(lastImageBean.getUnselectResorce());
                ((GifImageView) mViewList.get(position)).setImageResource((Integer) currentImageBean.getSelectResorce());
                break;
        }
        LastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void getImageData(List<ImageBean> imageBeans) {
        mImageBeans = imageBeans;
        switch (ImageType) {
            case 0://一般类型
                addNormalImage();
                break;
            case 1://SVGA类型
                addSvgaImage();
                break;
            case 2://JSON类型
                addJsonImage();
                break;
            case 3://GIF类型
                addGifImage();
                break;
        }
    }

    //当图片类型为普通类型时
    public void addNormalImage() {
        if (mImageBeans != null) {
            mViewList.clear();
            for (int i = 0; i < mImageBeans.size(); i++) {
                RoundedImageView imageView = new RoundedImageView(getContext());
                setParams(imageView, i);
                imageView.setScaleType(mScaleType);
                if (imageCorner) {
                    imageView.setCornerRadius(imageCornerRadius);
                }
                if (imageFrame) {
                    imageView.setBorderWidth(imageFrameSize);
                    imageView.setBorderColor(imageFrameColor);
                }
                if (i == 0) {
                    imageView.setImageResource((Integer) mImageBeans.get(i).getSelectResorce());
                } else {
                    imageView.setImageResource(mImageBeans.get(i).getUnselectResorce());
                }
            }
        }
    }

    //当图片类型为gif类型时
    public void addGifImage() {
        if (mImageBeans != null) {
            mViewList.clear();
            for (int i = 0; i < mImageBeans.size(); i++) {
                GifImageView imageView = new GifImageView(mContext);//mInflater.inflate(R.layout.gif_image,null);
                setParams(imageView, i);
                imageView.setScaleType(mScaleType);
                if (i == 0) {
                    imageView.setImageResource((Integer) mImageBeans.get(i).getSelectResorce());
                } else {
                    imageView.setImageResource(mImageBeans.get(i).getUnselectResorce());
                }
            }
        }
    }

    //当图片类型为svga时
    public void addSvgaImage() {
        if (mImageBeans != null) {
            mViewList.clear();
            for (int i = 0; i < mImageBeans.size(); i++) {
                SVGAImageView imageView = new SVGAImageView(mContext);//mInflater.inflate(R.layout.gif_image,null);
                setParams(imageView, i);
                SvgaUtils mSvgaUtils = new SvgaUtils(mContext, imageView);
                if (i == 0) {
                    mSvgaUtils.initAnimator();
                    mSvgaUtils.startAnimator((String) mImageBeans.get(i).getSelectResorce());
                    setSVGAPlayListener(imageView, i);
                } else {
                    imageView.setImageResource(mImageBeans.get(i).getUnselectResorce());
                }
                imageView.setLoops(loopCount);
                mSvgaUtilsData.add(mSvgaUtils);
            }

        }
    }

    //当图片类型为json类型时
    public void addJsonImage() {
        if (mImageBeans != null) {
            mViewList.clear();
            for (int i = 0; i < mImageBeans.size(); i++) {
                LottieAnimationView imageView = new LottieAnimationView(mContext);//mInflater.inflate(R.layout.gif_image,null);
                setParams(imageView, i);
                if (i == 0) {
                    imageView.setAnimation((String) mImageBeans.get(i).getSelectResorce());
                    imageView.playAnimation();
                    setJSONPlayListener(imageView, i);
                } else {
                    imageView.setImageResource(mImageBeans.get(i).getUnselectResorce());
                }
                if (loopCount == -1) {
                    imageView.loop(true);
                }
            }
        }
    }

    //设置params
    public void setParams(View imageView, int i) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = ImageWidth;
        params.height = ImageHeight;
        imageView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        imageView.setLayoutParams(params);
        addView(imageView);
        mViewList.add(imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageClick != null) {
                    mImageClick.imageClick(i);
                }
            }
        });
    }

    //对JSON播放的监听
    public void setJSONPlayListener(LottieAnimationView imageView, int position) {
        imageView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("cerrygageshg", count + "");
                if (count > 0) {
                    animation.start();
                    count--;
                } else {
                    imageView.cancelAnimation();
                    imageView.setImageResource(mImageBeans.get(position).getThumResorce());

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                count = 0;
            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //对svga播放的监听
    public void setSVGAPlayListener(SVGAImageView imageView, int position) {
        imageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
                imageView.setImageResource(mImageBeans.get(position).getUnselectResorce());
            }
            @Override
            public void onFinished() {
                imageView.stopAnimation();
                imageView.setImageResource(mImageBeans.get(position).getThumResorce());
            }
            @Override
            public void onRepeat() {
            }
            @Override
            public void onStep(int i, double v) {
            }
        });
    }

    public List<PositionData> getPositionData() {
        return mPositionData;
    }
   //图片指示器之图片的渐变显示
    public void startAlphaAnimation(View view){
        AlphaAnimation outAlphaAnimation=new AlphaAnimation(1.0f,0.2f);
        outAlphaAnimation.setDuration(100);
        outAlphaAnimation.setFillAfter(false);
        AlphaAnimation inAlphaAnimtion=new AlphaAnimation(0.2f,1.0f);
        inAlphaAnimtion.setDuration(100);
        outAlphaAnimation.setFillAfter(false);
        view.startAnimation(outAlphaAnimation);
        outAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(inAlphaAnimtion);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        inAlphaAnimtion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public interface ImageClick {
        void imageClick(int index);
        void setImagePositionData();
    }
}
