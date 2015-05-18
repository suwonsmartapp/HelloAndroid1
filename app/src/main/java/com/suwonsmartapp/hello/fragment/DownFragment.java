package com.suwonsmartapp.hello.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suwonsmartapp.hello.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DownFragment extends Fragment {

    private ImageView mImageView;

    public DownFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down, container, false);

        mImageView = (ImageView)view.findViewById(R.id.iv_image);

        return view;
    }

    public void setChangeImage(int res) {
        mImageView.setImageResource(res);
    }
}
