package com.yeqiu.GalleryViewPager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * project：tubatu-viewpager
 * describe：缩放的动画
 * author：yeqiu
 * date：2016/11/11 15:08
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {

    //最大缩放
    public static final float MAX_SCALE = 1.2f;
    //最小缩放
    public static final float MIN_SCALE = 1.0f;

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        //一个公式
        float scaleValue = MIN_SCALE + tempScale * slope;
        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }
}
