package com.suwonsmartapp.hello.event;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

public class EventActivity extends ActionBarActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mTextView.setText(event.toString());

        return super.onTouchEvent(event);
    }
}
