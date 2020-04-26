package com.indicator.bean;

/**
 * project : AnimationImageTest
 * package : com.user.bean
 * author  ：Administrator
 * date    : 2020/4/23 10:27
 */
/*构建一个图片指示器资源Bean*/
public class ImageBean<T> {
    private T selectResorce;//选择状态的图片资源
    private int unselectResorce;//未选择状态的图片资源
    private int thumResorce;//动图播放完成之后显示的缩略图

    public T getSelectResorce() {
        return selectResorce;
    }

    public ImageBean setSelectResorce(T selectResorce) {
        this.selectResorce = selectResorce;
        return  this;
    }

    public int getUnselectResorce() {
        return unselectResorce;
    }

    public ImageBean setUnselectResorce(int unselectResorce) {
        this.unselectResorce = unselectResorce;
        return this;
    }

    public ImageBean setThumResorce(int thumResorce) {
        this.thumResorce = thumResorce;
        return this;
    }

    public int getThumResorce() {
        return thumResorce;
    }
}
