package com.suwonsmartapp.hello.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.chat.client.ChatClient;

/**
 * Created by junsuk on 15. 4. 17..
 */
public class ClientActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilent);

        findViewById(R.id.btn_connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                new ChatClient().connect();
            }
        }).start();
    }
}
