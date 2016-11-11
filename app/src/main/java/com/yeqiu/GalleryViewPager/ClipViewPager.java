package com.yeqiu.GalleryViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * project：tubatu-viewpager
 * describe：自定义的viewpager
 * author：yeqiu
 * date：2016/11/11 15:08
 */
public class ClipViewPager extends ViewPager {

    //默认距离
    private final static float DISTANCE = 10;
    private float downX;
    private float downY;
    private int position;

    public ClipViewPager(Context context) {
        super(context);
    }

    public ClipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ///获取手指落下的x y坐标值
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
            downY = ev.getY();
            //获取手指抬起的x y坐标值
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            float upX = ev.getX();
            float upY = ev.getY();

            //如果手指移动距离大于设置的距离,则让viewpager来决定拦截事件
            //如果手指移动距离小于设置的距离，那就认为是用户在进行点击操作。
            //默认距离尽量设置小点/*
            if (Math.abs(upX - downX) > DISTANCE || Math.abs(upY - downY) > DISTANCE) {
                //让viewpager来决定是否拦截实现
                return super.dispatchTouchEvent(ev);
            }
            //走到这一步默认是用户进行了点击操作，获取点击的那个图片设置成当前的展示图
            View view = viewOfClickOnScreen(ev);
            if (view != null) {
                //index其实就是postion。从acitivty传进来的
                int index = (Integer) view.getTag();
                //如果当前的view不是被点击的view就设置当前item
                if (getCurrentItem() != index) {
                    setCurrentItem(index);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 此方法能返回用户点击图片的view
     * 点击空白处返回null
     */
    private View viewOfClickOnScreen(MotionEvent ev) {
        //获取当前缓存的所有图片。
        //注意：getChildCount不是返回所有的子view。是返回当前viewpager缓存的view+当前展示的view
        //在这里，真实的图片只有5张，缓存10。加上当前展示的一张。所以是11，getCount是所有
        int childCount = getChildCount();
        //获取当前当前显示的view，在viewpager所有子view的索引。是所有，不是缓存
        int currentIndex = getCurrentItem();
        //
        int[] location = new int[2];
        //循环获得viewpager缓存中的所有view
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            //position是在activity中传进来的。上面获取的view就是imageview
            position = (Integer) v.getTag();
            //获取在整个屏幕内的绝对坐标，注意这个值是要从屏幕顶端算起，也就是包括了通知栏的高度
            v.getLocationOnScreen(location);
            int minX = location[0];
            int minY = location[1];
            //获取view右上角坐标和左小角坐标。画图就能明白
            int maxX = location[0] + v.getWidth();
            int maxY = location[1] + v.getHeight();
            //position是在activity中传进来的。如果小于当前view的所有。说明点击的是左边
           if (position < currentIndex) {
                //获取左边图片的坐标,从屏幕算起来的
                maxX -= v.getWidth() * (1 - ScalePageTransformer.MIN_SCALE) * 0.5 + v.getWidth()
                        * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
                minX -= v.getWidth() * (1 - ScalePageTransformer.MIN_SCALE) * 0.5 + v.getWidth()
                        * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
            } else if (position == currentIndex) {
                //感觉其实不用计算这个。这个代表选中的是当前已经展示的view
                //minX += v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE));
            } else if (position > currentIndex) {
               //获取右边图片的坐标,从屏幕算起来的
                maxX -= v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
                minX -= v.getWidth() * (Math.abs(1 - ScalePageTransformer.MAX_SCALE)) * 0.5;
            }
            //获取view在屏幕里的坐标
            float x = ev.getRawX();
            float y = ev.getRawY();
            //判断点击的点实在左边的view和右边的view身上。以上的操作都是回来获取左右两个view的坐标
            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }
}














