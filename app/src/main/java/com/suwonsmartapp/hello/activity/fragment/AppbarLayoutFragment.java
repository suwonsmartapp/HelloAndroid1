package com.suwonsmartapp.hello.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 16..
 */
public class AppbarLayoutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coordinator, container, false);

//        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
//        toolbar.setTitle("Coordinator");

        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.contents);

        for (int i = 0; i < 30; i++) {
            TextView tv = new TextView(getActivity());
            tv.setText("Item " + i);
            layout.addView(tv);
        }


        return rootView;
    }
}
