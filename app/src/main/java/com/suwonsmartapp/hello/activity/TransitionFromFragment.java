package com.suwonsmartapp.hello.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suwonsmartapp.hello.R;

import de.greenrobot.event.EventBus;

/**
 * Created by junsuk on 15. 6. 17..
 */
public class TransitionFromFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transition_from, container, false);
        View target = rootView.findViewById(R.id.image);
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(v);
                Log.d("viewid", "viewid " + v.getId());
            }
        });
        return rootView;
    }

}
