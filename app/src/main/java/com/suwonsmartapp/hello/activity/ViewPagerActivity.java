package com.suwonsmartapp.hello.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

/**
 * http://developer.android.com/training/implementing-navigation/lateral.html
 *
 * 프래그먼트 어댑터 두 종류의 차이
 *
 * FragmentPagerAdapter : 화면에 안 보이는 Fragment도 메모리에 남아 있음. 빠름.
 *
 * FragmentStatePagerAdapter : 화면에 안 보이는 Fragment 는 메모리에서 해제. 대신 로드시 오버헤드가 있음.
 */
public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = (ViewPager)findViewById(R.id.viewPager);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.pic300_7);
        data.add(R.drawable.dmyo_2jpg_);

        mMyAdapter = new MyAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(mMyAdapter);

        findViewById(R.id.btn_change_adapter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ArrayList<Integer> data1 = new ArrayList<>();
        data1.add(R.mipmap.ic_launcher);
        data1.add(R.drawable.dmyo_2jpg_);

        mMyAdapter.setData(data1);
        mMyAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends FragmentPagerAdapter {

        private ArrayList<Integer> mData;

        public MyAdapter(FragmentManager fm, ArrayList<Integer> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.getInstance(mData.get(position));
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        public void setData(ArrayList<Integer> data) {
            mData = data;
        }
    }

    public static class ImageFragment extends Fragment {

        private ImageView mImageView;

        // 싱글턴 패턴
        public static Fragment getInstance(int resId) {
            ImageFragment fragment = new ImageFragment();

            Bundle args = new Bundle();
            args.putInt("resId", resId);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_image, container, false);
            mImageView = (ImageView)rootView.findViewById(R.id.iv_image);

            int resId = getArguments().getInt("resId");
            mImageView.setImageResource(resId);

            return rootView;
        }

    }

}
