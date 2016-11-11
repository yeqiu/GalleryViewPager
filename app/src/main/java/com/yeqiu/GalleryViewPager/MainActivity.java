package com.yeqiu.GalleryViewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yeqiu.GalleryViewPager.adapter.RecyclingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * project：tubatu-viewpager
 * describe：viewpager实现画廊，中间放大效果。未封装
 * author：yeqiu
 * date：2016/11/11 15:08
 */
public class MainActivity extends AppCompatActivity {

    private ClipViewPager mViewPager;
    private TubatuAdapter mPagerAdapter;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ClipViewPager) findViewById(R.id.viewpager);
        //设置滑动的速度
        SpeedScroller scroller = new SpeedScroller(this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(mViewPager);

        //此方法用于设置viepager滑动的动画。这里可以缩放
        mViewPager.setPageTransformer(true, new ScalePageTransformer());

        //设置间距在dimen声明的间距，可以适配
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_magin));

        //把viewpager父控件的事件交给viewpager处理。viewpager只有一张图，其他两个还是在父控件里
        findViewById(R.id.page_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mPagerAdapter = new TubatuAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
        initData();
        mViewPager.setCurrentItem(500);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(R.drawable.a01);
        list.add(R.drawable.a02);
        list.add(R.drawable.a03);
        list.add(R.drawable.a04);
        list.add(R.drawable.a05);


        //设置viewpagere的缓存
        mViewPager.setOffscreenPageLimit(list.size());
        //把图片的集合给适配器
        mPagerAdapter.addAll(list);
    }

    //适配器使用的是别人封装好的适配器
    public static class TubatuAdapter extends RecyclingPagerAdapter {

        private final List<Integer> mList;
        private final Context mContext;

        public TubatuAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
        }

        public List<Integer> getmList() {
            return mList;
        }

        public void addAll(List<Integer> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            //注意这个position不能取余，以为在自定义viewpager要使用这个postion
            imageView.setTag(position);
            imageView.setImageResource(mList.get(position % mList.size()));
            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size() * 1000;
        }
    }


}
