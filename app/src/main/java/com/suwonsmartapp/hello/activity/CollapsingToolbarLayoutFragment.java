package com.suwonsmartapp.hello.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 15. 6. 16..
 */
public class CollapsingToolbarLayoutFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collapsing, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MyAdapter());

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("CollapsingToolbarLayout Title");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        return rootView;
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        private List<String> mList;

        public MyAdapter() {
            mList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                mList.add("Item " + i);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyHolder(View itemView) {
                super(itemView);

                textView = (TextView)itemView.findViewById(android.R.id.text1);
            }
        }
    }
}

