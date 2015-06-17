package com.suwonsmartapp.hello.activity;

import android.app.Activity;
import android.os.Bundle;

import com.suwonsmartapp.hello.R;

/**
 * Created by junsuk on 15. 6. 17..
 */
public class NextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_transition_to);
    }
}
