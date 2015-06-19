package com.suwonsmartapp.hello.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 16..
 */
public class TabLayoutFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private View mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tablayout, container, false);

        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tablayout);

        for (int i = 0; i < 10; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("tab" + i));
        }
        tabLayout.setOnTabSelectedListener(this);

        // 스크롤, 고정 모드 설정
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        mContainer = rootView.findViewById(R.id.container);

        return rootView;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            mContainer.setBackgroundColor(Color.RED);
        } else if (tab.getPosition() == 1) {
            mContainer.setBackgroundColor(Color.BLACK);
        } else if (tab.getPosition() == 2) {
            mContainer.setBackgroundColor(Color.YELLOW);
        } else {
            mContainer.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
