package com.suwonsmartapp.hello.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 15..
 */
public class FloatingActionButtonExamFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_fab, container, false);

        final View coordinatorLayout = rootView.findViewById(R.id.fab_position);

        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "스낵바입니다", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "버튼 눌림", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        return rootView;
    }
}
