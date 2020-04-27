## 简介 
### 图片指示器是在文字作为指示器的基础的拓展出来的一个小的功能，里面包含了：
> 1：用文字做指示器，通过Paint画出底部滑动的导航栏；

> 2: 用文字作为指示器，用图片资源作为底部导航栏；

> 3：用图片作为指示器，底部通过Paint画出导航栏；

> 4：用图片作为指示器，底部用图片作为导航栏；

> 5：引入第三方指示器(implementation 'com.github.hackware1993:MagicIndicator:1.5.0')
### 注：用图片作为指示器的图片格式目前支持四种
> a:  一般格式 如：png,jpeg,svg等;

> b:  gif动图格式,播放引用(implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.7');

> c:  svga动图,播放引用(implementation 'com.github.yyued:SVGAPlayer-Android:2.1.1');

> d:  json格式图，播放引用(implementation 'com.airbnb.android:lottie:1.0.1');

> 另外，图片指示器有许多属性可以灵活的控制，待用户使用时再挖掘。
### HOW TO USE ?
#### first（添加maven）
` allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}`  

#### next(添加依赖)
`  dependencies {
	        implementation 'com.github.Laughing-program-monkey:ImageIndicator:版本号(如：1.0.7)'
	}` 
	
> 重点：gradle版本------》（classpath 'com.android.tools.build:gradle:3.5.+'）
### Introducing attributes
Attribute  | Describe  | Type | Default value | Must
---- | ----- |  --- | ---- | -----
indicatorKind  | 指示器的种类 |  enum  | TEXT_PUNDINE(0) | NO
imageIndicatorKind  | 图片指示器的图片格式 |  enum  | NORMAL(0) | NO
textBold  | 字体是否加粗 |  boolean  | false | NO
selectTextColor  | 选中字体颜色 |  color  | Color.RED | NO
unSelectTextColor  | 未选中字体颜色 |  color  | Color.GRAY | NO
textSize  | 字体大小 |  integer  | 15 | NO
textPaddingLeft  | 字体的左内边距(右，上，下类似) |  dimension  | 10 | NO
unlineImageResource  | 底部导航栏的资源(可以用图片) |  reference  | ---- | NO
indicatorLayout  | 指示器得到样式布局 |  reference  | ---- | NO
hideUndline  | 是否隐藏低部导航栏 |  boolean  | false | NO
undlinePaddingLeft  | 底部导航栏的左内边距(右，上，下类似) |  dimension  | 20 | NO
imageWidth  | 图片指示器的图片宽度 |  dimension  | 50 | NO
imageHeight  | 图片指示器的图片高度 |  dimension  | 20 | NO
undlineScrollTime  | 底部导航栏一次滑动的时间 |  integer  | 200ms | NO
imageCorner  | 图片指示器是否需要圆角 |  boolean  | false | NO
imageFrame  | 图片指示器是否需要边框 |  boolean  | false | NO
imageCornerRadius  | 图片指示器的圆角大小 |  dimension  | 5 | NO
imageFrameSize  | 图片指示器的边框大小 |  dimension  | 5 | NO
imageFrameColor  | 图片指示器的边框颜色 |  color  | Color.WHITE | NO
loop_count  | 特殊图片指示器的播放次数 |  integer  | -1（循环） | NO
imagePaddingLeft  | 图片指示器的左内边距(右，上，下类似) |  dimension  | 5 | NO
containerPaddingLeft | 指示器容器的左内边距(右，上，下类似) | dimension | 0 | NO 
useThird  | 是否使用第三方 |  boolean  | false | NO

### Layout（以图片指示器为例）
```
  <com.indicator.indicator.ImageIndicatorView
                    android:id="@+id/indicator2"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:containerPaddingBottom="5dp"
                    app:containerPaddingTop="5dp"
                    app:imageHeight="40dp"
                    app:imageIndicatorKind="NORMAL"
                    app:imageWidth="80dp"
                    app:indicatorKind="IMAGE_PUNDLINE" />
```
### Code
``` 
List<ImageBean> mImageBeans = new ArrayList<>();
ImageBean<T> imageBean = new ImageBean().setSelectResorce("图片名称").setUnselectResorce(图片资源).setThumResorce(图片资源);
mImageBeans3.add(imageBean);
mImageBeans.add(imageBean);
indicator.setImageBeans(mImageBeans);
indicator.setViewPager(mViewPager); 
```
