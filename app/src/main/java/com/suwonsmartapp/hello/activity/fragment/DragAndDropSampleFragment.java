package com.suwonsmartapp.hello.activity.fragment;

import android.content.ClipData;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 5..
 */
public class DragAndDropSampleFragment extends Fragment implements View.OnLongClickListener, View.OnTouchListener, View.OnDragListener {
    private TextView mText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_drag, container, false);

        mText = (TextView) rootView.findViewById(R.id.tv_drag);
        mText.setOnTouchListener(this);
        mText.setOnDragListener(this);

        return rootView;
    }

    @Override
    public boolean onLongClick(View v) {
        startDrag(v);
        return true;
    }

    private void startDrag(View v) {
        View.DragShadowBuilder builder = new View.DragShadowBuilder(v) {
            @Override
            public void onDrawShadow(Canvas canvas) {
                super.onDrawShadow(canvas);

            }
        };


        ClipData data = ClipData.newPlainText("text", "text : " + v.toString());
        v.startDrag(data, new View.DragShadowBuilder(v), v, 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            startDrag(v);
        }
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                v.setVisibility(View.GONE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }
}
