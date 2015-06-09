package com.suwonsmartapp.hello.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 9..
 */
public class PullToRefreshFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    // 변경 전 데이타
    private String[] objects = { "abc", "def", "ghi" };

    // 변경 후 데이타
    private String[] objects2 = { "abc", "def", "ghi", "jkl", "mno" };


    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pulltorefresh, container, false);

        mListView = (ListView) rootView.findViewById(R.id.lv_pulltorefresh);
        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, objects);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onRefresh() {
        // Pull 했을 때 3초 후에 데이타를 갱신
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, objects2);
                mListView.setAdapter(mAdapter);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
