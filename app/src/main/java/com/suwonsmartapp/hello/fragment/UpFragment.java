package com.suwonsmartapp.hello.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suwonsmartapp.hello.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpFragment extends Fragment implements View.OnClickListener {

    private Button mButton;

    // Interface 만들어 쓸 때 정형화 된 코드 시작
    public interface OnImageChangeListener {
        void onImageChanged();
    }

    private OnImageChangeListener mListener;

    public void setOnImageChangeListener(OnImageChangeListener listener) {
        mListener = listener;
    }
    // Interface 만들어 쓸 때 정형화 된 코드 끝

    public UpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_main, container, false);

        mButton = (Button)view.findViewById(R.id.btn_button);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onImageChanged();
        }
    }
}
