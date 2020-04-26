package com.indicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.indicator.Interface.IndicatorInterface;
import com.indicator.ImageIndicator.R;
import com.indicator.bean.ImageBean;
import com.indicator.bean.PositionData;
import com.indicator.titleview.ImageTitleView;
import com.indicator.titleview.IndicatorView;
import com.indicator.titleview.TextTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * project : AnimationImageTest
 * package : com.user.indicator
 * author  ：Administrator
 * date    : 2020/4/21 15:24
 */
public class NormalIndicator extends LinearLayout implements TextTitleView.TitleClick,ImageTitleView.ImageClick{
    private Context mContext;
    private HorizontalScrollView mScrollView;
    private LinearLayout indicator_container;
    private List<View> mViewList = new ArrayList<>();
    private ImageView line;
    private ViewPager mViewPager;
    private List<PositionData> mPositionData=new ArrayList<>();//存储文字属性
    private int width;//文字宽度的存储
    private int lastPosition;//暂时存储上一个viewpager内容
    private boolean first = true;//是否是第一次初始化

    private List<ImageBean> mImageBeans;//图片作为指示器的资源
    private boolean TextBold;//字体是否加粗
    private int SelectTextColor;//选中字体的颜色
    private int UnSelectTextColor;//未选择字体的颜色
    private int TextSize;//字体大小
    private int undlinePaddingLeft;//底部导航栏的左内边距
    private int undlinePaddingRight;//底部导航栏的右内边距
    private int undlinePaddingTop;//底部导航栏的上内边距
    private int undlinePaddingBottom;//底部导航栏的下内边距
    private int TextPaddingLeft;//字体的左内边距
    private int TextPaddingRight;//字体的右内边距
    private int TextPaddingTop;//字体的上内边距
    private int TextPaddingBottom;//字体的下内边距
    private int UndlineResource;//底部图片导航栏的图片资源
    private boolean HideUndline;//是否隐藏底部导航栏
    private int IndicatorLayout;//自定义导航栏资源(资源id要一致)
    private int ImageWidth;//图片指示器的宽度
    private int ImageHeight;//图片指示器的高度
    private int UndlineScrollTime;//底部导航栏的一次滑动时间
    private int IndicatorKind;//指示器的类型(文字指示器，图片指示器，图片+文字指示器)
    private boolean UserThird;//是否使用第三方(第三方资源implementation 'com.github.hackware1993:MagicIndicator:1.5.0'))
    private int ImageKind;//图片作为指示器时的图片格式
    private int imagePaddingLeft;//图片的左内边距
    private int imagePaddingRight;//图片的右内边距
    private int imagePaddingTop;//图片的上内边距
    private int imagePaddingBottom;//图片的下内边距
    private int containerPaddingLeft;//指示器容器的左内边距
    private int containerPaddingRight;//指示器容器的右内边距
    private int containerPaddingTop;//指示器容器的上内边距
    private int containerPaddingBottom;//指示器容器的下内边距
    private boolean ImageCorner;//图片指示器的图片是否需要圆角
    private boolean ImageFrame;//图片指示器的图片是否需要边框
    private int ImageCornerRadius;//图片指示器的图片的圆角大小
    private int ImageFrameSize;//图片指示器的图片边框大小
    private int ImageFrameColor;//图片指示器的图片边框的颜色
    private int loop_count;//设置动图图片指示器的播放次数(默认-1循环播放)
    private Handler mHandler;
    private IndicatorInterface mTextTitleInterface;
    private IndicatorInterface mUndlineInterface;
    private IndicatorInterface mImageInterface;
    private LinearLayout indicator;
    private IndicatorView indicatorView;
    private TextTitleView mTextTitleView;
    private ImageTitleView mImageTitleView;
    private int DealImage;

    public NormalIndicator(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public NormalIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttr(attrs);
        init(context);
    }

    public NormalIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //定义指示器类型的枚举类
    public enum IndicatorType{
        TEXT_PUNDINE (0),
        TEXT_IMAGE  (1),
        IMAGE_PUNDLINE(2),
        IMAGE_IMAGE(3);
        IndicatorType(int type){
            TYPE=type;
        }
       final int TYPE;
    }
    //定义图片指示器图片格式的枚举类
    public enum ImageType{
        NORMAL (0),
        SVGA  (1),
        JSON (2),
        GIF (3);
        ImageType(int type){
            TYPE=type;
        }
        final int TYPE;
    }
    //定义处理图片类型的枚举类
    public enum DealImageType{
        CenterCrop (6),
        FitXY  (1);
        DealImageType(int type){
            TYPE=type;
        }
        final int TYPE;
    }
    //属性初始化
    public void initAttr(AttributeSet attributeSet) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.NormalIndicator);
        TextBold = typedArray.getBoolean(R.styleable.NormalIndicator_textBold, false);
        SelectTextColor = typedArray.getColor(R.styleable.NormalIndicator_selectTextColor, Color.RED);
        UnSelectTextColor = typedArray.getColor(R.styleable.NormalIndicator_unSelectTextColor, Color.GRAY);
        TextSize = typedArray.getInteger(R.styleable.NormalIndicator_textSize, 15);
        undlinePaddingLeft = (int) typedArray.getDimension(R.styleable.NormalIndicator_undlinePaddingLeft, 20);
        undlinePaddingRight = (int) typedArray.getDimension(R.styleable.NormalIndicator_undlinePaddingRight, 20);
        undlinePaddingTop = (int) typedArray.getDimension(R.styleable.NormalIndicator_undlinePaddingTop, 0);
        undlinePaddingBottom = (int) typedArray.getDimension(R.styleable.NormalIndicator_undlinePaddingBottom, 0);
        TextPaddingLeft = (int) typedArray.getDimension(R.styleable.NormalIndicator_textPaddingLeft, 10);
        TextPaddingRight = (int) typedArray.getDimension(R.styleable.NormalIndicator_textPaddingRight, 10);
        TextPaddingTop = (int) typedArray.getDimension(R.styleable.NormalIndicator_textPaddingTop, 0);
        TextPaddingBottom = (int) typedArray.getDimension(R.styleable.NormalIndicator_textPaddingBottom, 0);
        UndlineResource = typedArray.getResourceId(R.styleable.NormalIndicator_unlineImageResource, R.drawable.line_bg);
        HideUndline = typedArray.getBoolean(R.styleable.NormalIndicator_hideUndline, false);
        IndicatorLayout = typedArray.getResourceId(R.styleable.NormalIndicator_indicatorLayout, R.layout.indicator_layout);
        ImageWidth = (int) typedArray.getDimension(R.styleable.NormalIndicator_imageWidth, 50);
        ImageHeight = (int) typedArray.getDimension(R.styleable.NormalIndicator_imageHeight, 20);
        UndlineScrollTime = typedArray.getInteger(R.styleable.NormalIndicator_undlineScrollTime, 200);
        UserThird = typedArray.getBoolean(R.styleable.NormalIndicator_useThird, false);
        IndicatorKind = typedArray.getInt(R.styleable.NormalIndicator_indicatorKind, 0);
        ImageKind = typedArray.getInt(R.styleable.NormalIndicator_imageIndicatorKind, 0);
        imagePaddingLeft=(int)typedArray.getDimension(R.styleable.NormalIndicator_imagePaddingLeft,5);
        imagePaddingRight=(int)typedArray.getDimension(R.styleable.NormalIndicator_imagePaddingRight,5);
        imagePaddingTop=(int)typedArray.getDimension(R.styleable.NormalIndicator_imagePaddingTop,0);
        imagePaddingBottom=(int)typedArray.getDimension(R.styleable.NormalIndicator_imagePaddingBottom,0);
        DealImage=(int)typedArray.getInt(R.styleable.NormalIndicator_dealImage,1);
        containerPaddingLeft=(int)typedArray.getDimension(R.styleable.NormalIndicator_containerPaddingLeft,0);
        containerPaddingRight=(int)typedArray.getDimension(R.styleable.NormalIndicator_containerPaddingRight,0);
        containerPaddingTop=(int)typedArray.getDimension(R.styleable.NormalIndicator_containerPaddingTop,5);
        containerPaddingBottom=(int)typedArray.getDimension(R.styleable.NormalIndicator_containerPaddingBottom,5);
        ImageCorner=typedArray.getBoolean(R.styleable.NormalIndicator_imageCorner,false);
        ImageFrame=typedArray.getBoolean(R.styleable.NormalIndicator_imageFrame,false);
        ImageCornerRadius=(int)typedArray.getDimension(R.styleable.NormalIndicator_imageCornerRadius,5);
        ImageFrameColor=typedArray.getColor(R.styleable.NormalIndicator_imageFrameColor,Color.WHITE);
        ImageFrameSize=(int)typedArray.getDimension(R.styleable.NormalIndicator_imageFrameSize,5);
        loop_count=typedArray.getInteger(R.styleable.NormalIndicator_loop_count,-1);
    }
    //初始化
    public void init(Context context) {
        View.inflate(context, IndicatorLayout, this);
        mScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        indicator_container = (LinearLayout) findViewById(R.id.indicator_container);
        indicator_container.setPadding(containerPaddingLeft,containerPaddingTop,containerPaddingRight,containerPaddingBottom);
        indicator = (LinearLayout) findViewById(R.id.un_indicator);
        indicatorView = (IndicatorView) findViewById(R.id.indicatorView);
        line = (ImageView)findViewById(R.id.line);
        line.setImageResource(UndlineResource);
        if(IndicatorKind==0 || IndicatorKind==2){
            line.setVisibility(GONE);
            indicator.setVisibility(VISIBLE);
        }
        if (IndicatorKind == 0 || IndicatorKind==1) {
            initTextIndicator();
        }
        if(IndicatorKind==2 || IndicatorKind==3){
            initImageIndicator();
        }
        if(HideUndline){
            indicatorView.setVisibility(GONE);
            line.setVisibility(GONE);
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                setUndlineParam(msg.what);
            }
        };
    }
    //动态的设置指示器类型
    public void setIndicatorType(IndicatorType indicatorType){
         IndicatorKind=indicatorType.TYPE;
    }
    //动态设置图片指示器的图片格式
    public void setImageType(ImageType imageType){
        ImageKind=imageType.TYPE;
    }
    //动态设置处理图片的方式(目前只设定两种fitXy,centerCrop)
    public void setDealImageType(DealImageType dealImageType){
        DealImage=dealImageType.TYPE;
    }
    /*
     * 文字版指示器START
     *
     *
     *
     */

//设置文字指示器资源
    public void setTitleDatas(List<String> titleDatas) {
        if (mTextTitleInterface != null) {
            mTextTitleInterface.getTitleData(titleDatas);
        }
    }
//初始化文本指示器
    public void initTextIndicator(){
        mTextTitleView = new TextTitleView(mContext)
                .setSelectTextColor(SelectTextColor)
                .setUnSelectTextColor(UnSelectTextColor)
                .setTextBold(TextBold).setTextSize(TextSize)
                .setTextPadding(TextPaddingLeft,TextPaddingRight,TextPaddingTop,TextPaddingBottom)
                .setTitleClick(this);
        mTextTitleInterface=mTextTitleView;
        indicator_container.removeAllViews();
        indicator_container.addView(mTextTitleView);
    }
    //设置文字指示器的params
     public void setPaintUndlineParams(){
         mUndlineInterface=indicatorView;
         if (mUndlineInterface != null) {
             mUndlineInterface.GetPositionData(mPositionData);
         }
     }

    /*
     * 文字版指示器END
     *
     *
     * */


    /*
     * 图片做导航栏START
     *
     *
     *
     * */

    //动态设置下划线的属性
    public void setUndlineParam(int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line.getLayoutParams();
        Log.e("dhewudwfuiwf", mPositionData.get(position).width()+"");
        params.width = mPositionData.get(position).width();
        line.setPadding(undlinePaddingLeft, undlinePaddingTop, undlinePaddingRight, undlinePaddingBottom);
        line.setLayoutParams(params);
    }
    //平移动画
    public void translate(int x1, int y1, int x2, int y2, final int position) {
        Animation translateAnimation = new TranslateAnimation(x1, x2, y1, y2);//平移动画  从0,0,平移到100,100
        translateAnimation.setDuration(UndlineScrollTime);//动画持续的时间为1.5s
        line.setAnimation(translateAnimation);//给imageView添加的动画效果
        translateAnimation.setFillEnabled(true);//使其可以填充效果从而不回到原地
        translateAnimation.setFillAfter(true);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        translateAnimation.startNow();//动画开始执行 放在最后即可
    }
    //图片导航栏切换(目前没有实现随着页面切换底部导航栏跟着滑动的效果)
    public void LineScroll(int position){
        mHandler.sendEmptyMessageDelayed(position,100);
        translate(mPositionData.get(lastPosition).mLeft, 0, mPositionData.get(position).mLeft, 0, position);
       /* float scrollTo = mPositionData.get(position).horizontalCenter() - mScrollView.getWidth() * 0.5f;
        mScrollView.smoothScrollTo((int) (scrollTo), 0);*/
        lastPosition = position;
    }

    /*
     *
     *图片指示器END
     *
     *
     * */


    /*
     * 图片指示器START
     *
     *
     * */
//设置图片指示器资源
    public void setImageBeans(List<ImageBean> imageBeans) {
        if (mImageInterface != null) {
            mImageInterface.getImageData(imageBeans);
        }
    }
    //初始化图片指示器
    public void initImageIndicator(){
        mImageTitleView = new ImageTitleView(mContext)
                .setImageHeight(ImageHeight)
                .setImageWidth(ImageWidth)
                .setImageType(ImageKind)
                .setPaddingData(imagePaddingTop,imagePaddingBottom,imagePaddingLeft,imagePaddingRight)
                .setDealType(DealImage)
                .setImageCorner(ImageCorner)
                .setImageCornerRadius(ImageCornerRadius)
                .setImageFrame(ImageFrame)
                .setImageFrameColor(ImageFrameColor)
                .setImageFrameSize(ImageFrameSize)
                .setLoopCount(loop_count)
                .setImageClick(this);
        mImageInterface=mImageTitleView;
        //文本指示器的标题
        indicator_container.removeAllViews();
        indicator_container.addView(mImageTitleView);
    }


    /*
     * 图片指示器END
     *
     *
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (first) {
            switch (IndicatorKind){
                case 0:
                case 2:
                    LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    params.width = indicator_container.getMeasuredWidth();
                    params.height = indicatorView.getUndlineHeight();
                    indicatorView.setLayoutParams(params);
                    break;
            }
            first = false;
        }
    }

    //指示器绑定ViewPager;
    public void setViewPager(ViewPager pager) {
        mViewPager = pager;
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
    }

    //滑动监听事件
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(IndicatorKind==0 || IndicatorKind==1) {
                if (mTextTitleInterface != null) {
                    mTextTitleInterface.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
            if(IndicatorKind==0 || IndicatorKind==2) {
                if (mUndlineInterface != null) {
                    mUndlineInterface.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
            if(IndicatorKind==2 || IndicatorKind==3) {
                if (mImageInterface != null) {
                    mImageInterface.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
            ScrollByViewPager(position,positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            if(IndicatorKind==0 || IndicatorKind==2) {
                if (mUndlineInterface != null) {
                    mUndlineInterface.onPageSelected(position);
                }
            }
            if(IndicatorKind==0 || IndicatorKind==1) {
                if (mTextTitleInterface != null) {
                    mTextTitleInterface.onPageSelected(position);
                }
            }
            if(IndicatorKind==2 || IndicatorKind==3) {
                if (mImageInterface != null) {
                    mImageInterface.onPageSelected(position);
                }
            }
            if(line.getVisibility()==VISIBLE){
                LineScroll(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (IndicatorKind == 0 || IndicatorKind == 1) {
                if (mTextTitleInterface != null) {
                    mTextTitleInterface.onPageScrollStateChanged(state);
                }
            }
            if (IndicatorKind == 0 || IndicatorKind == 2) {
                if (mUndlineInterface != null) {
                    mUndlineInterface.onPageScrollStateChanged(state);
                }
            }
            if (IndicatorKind ==2 || IndicatorKind == 3) {
                if (mImageInterface != null) {
                    mImageInterface.onPageScrollStateChanged(state);
                }
            }
        }
    };
    //若使用第三方则把处理交由第三方
    public MagicIndicator getThirdIndicator() {
        removeAllViews();
        if (UserThird) {
            MagicIndicator magicIndicator = new MagicIndicator(mContext);
            addView(magicIndicator);
            return magicIndicator;
        } else {
            Toast.makeText(mContext, "未打开使用三方权限，导致获取的对象为空!", Toast.LENGTH_LONG).show();
            return null;
        }
    }
   //当标题的可见范围小于当前的ViewPager滚动范围时调用
    public void ScrollByViewPager(int position,float positionOffset){
        if (mScrollView != null && mPositionData.size() > 0 && position >= 0 && position < mPositionData.size()) {
            int currentPosition = Math.min(mPositionData.size() - 1, position);
            int nextPosition = Math.min(mPositionData.size() - 1, position + 1);
            PositionData current = mPositionData.get(currentPosition);
            PositionData next = mPositionData.get(nextPosition);
            float scrollTo = current.horizontalCenter() - mScrollView.getWidth() * 0.5f;
            float nextScrollTo = next.horizontalCenter() - mScrollView.getWidth() * 0.5f;
            mScrollView.scrollTo((int) (scrollTo + (nextScrollTo - scrollTo) * positionOffset), 0);
        }
    }
  //文字指示器的点击事件
    @Override
    public void titleClick(int position) {
        if(mViewPager!=null)
        mViewPager.setCurrentItem(position);
    }
//获取文字指示器的位置属性
    @Override
    public void setPositionData() {
        mPositionData=mTextTitleView.getPositionData();
        if(IndicatorKind==0) {
            setPaintUndlineParams();
            return;
        }
        if(IndicatorKind==1 || IndicatorKind==3){
            mHandler.sendEmptyMessageDelayed(lastPosition,100);
        }
    }
//图片指示器的点击事件
    @Override
    public void imageClick(int index) {
        if(mViewPager!=null)
        mViewPager.setCurrentItem(index);
    }
//获取图片指示器的坐标
    @Override
    public void setImagePositionData() {
        mPositionData=mImageTitleView.getPositionData();
        if(IndicatorKind==2) {
            setPaintUndlineParams();
            return;
        }
        if(IndicatorKind==1 || IndicatorKind==3){
            mHandler.sendEmptyMessageDelayed(lastPosition,100);
        }
    }
}
