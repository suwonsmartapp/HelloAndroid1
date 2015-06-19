package com.suwonsmartapp.hello.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.suwonsmartapp.hello.activity.event.ButtonClickEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by junsuk on 15. 6. 11..
 */
public class EventBusActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("이벤트 발송");
        setContentView(button);

        button.setOnClickListener(this);
    }

    public void onEvent(ButtonClickEvent event) {
        Log.d("Event", "click : " + event.getClickCount());
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new ButtonClickEvent(10));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
