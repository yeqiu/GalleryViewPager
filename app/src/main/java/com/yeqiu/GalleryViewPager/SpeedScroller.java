package com.yeqiu.GalleryViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * project：GalleryViewPager
 * describe：实现veipager的滑动速度
 * author：yeqiu
 * date：2016/11/11 14:52
 */
public class SpeedScroller extends Scroller{
    private int mScrollDuration = 2000;             // 滑动速度


    public void setScrollDuration(int duration){
        this.mScrollDuration = duration;
    }

    public SpeedScroller(Context context) {
        super(context);
    }

    public SpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public SpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }



    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
