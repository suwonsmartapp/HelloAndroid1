
package com.suwonsmartapp.hello.broadcast;

import com.suwonsmartapp.hello.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

// P. 239
public class BroadcastActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.btn_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_broadcast2).setOnClickListener(this);

        // 로컬 리시버
        // 이 앱이 실행되고 있는 동안에만 브로드캐스트 수신이 가능
        // MyReceiver myReceiver = new MyReceiver();
        // IntentFilter intentFilter = new
        // IntentFilter("com.suwonsmartapp.hello.TestBroadcast");
        // intentFilter.addAction("com.suwonsmartapp.hello.TestBroadcast2");
        // intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        // registerReceiver(myReceiver, intentFilter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_broadcast:
                Intent intent = new Intent("com.suwonsmartapp.hello.TestBroadcast");
                sendBroadcast(intent);
                break;

            case R.id.btn_broadcast2:
                sendBroadcast(new Intent("com.suwonsmartapp.hello.TestBroadcast2"));
                break;
        }
    }
}
