package com.hhl.tubatu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hhl.tubatu.adapter.RecyclingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClipViewPager mViewPager;
    private TubatuAdapter mPagerAdapter;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ClipViewPager) findViewById(R.id.viewpager);
        mViewPager.setPageTransformer(true, new ScalePageTransformer());

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

        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(Math.min(list.size(), 5));
        mPagerAdapter.addAll(list);
    }

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
            //position = position%mList.size();
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setTag(position);
            imageView.setImageResource(mList.get(position%mList.size()));
            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size()*1000;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
