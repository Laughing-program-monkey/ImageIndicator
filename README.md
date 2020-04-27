# ImageIndicator
简介：
图片指示器是在文字作为指示器的基础的拓展出来的一个小的功能，里面包含了：
1：用文字做指示器，通过Paint画出底部滑动的导航栏；
2：还可以以文字作为指示器，用图片资源作为底部导航栏；
3：用图片作为指示器，底部通过Paint画出导航栏；
4：用图片作为指示器，底部用图片作为导航栏；
5：引入第三方指示器(implementation 'com.github.hackware1993:MagicIndicator:1.5.0')
注：用图片作为指示器的图片格式目前支持四种
   a:一般格式 如：png,jpeg,svg等
   b:gif动图格式,引用第三方库(implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.7')
   c:svga动图,播放引用第三方库(implementation 'com.github.yyued:SVGAPlayer-Android:2.1.1')
   d:json格式图，播放引用第三方库(implementation 'com.airbnb.android:lottie:1.0.1')
另外，图片指示器有许多属性可以灵活的控制，待用户使用时再挖掘。

HOW TO USE ?
 首先 ：把
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   添加到你的项目的根目录下的build.gradle文件中(Add it in your root build.gradle at the end of repositories)

 其次添加依赖：把
 dependencies {
	        implementation 'com.github.Laughing-program-monkey:ImageIndicator:1.0.0'
	}
放到项目的build.gradle文件中。

 重点：gradle版本------》（classpath 'com.android.tools.build:gradle:3.5.2'）
 
