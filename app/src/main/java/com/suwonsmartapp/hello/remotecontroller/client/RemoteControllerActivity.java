package com.suwonsmartapp.hello.remotecontroller.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.chat.client.ChatClient;

/**
 * 서버파일 위치 : app/Server.java
 */
public class RemoteControllerActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String SERVER_HOST = "192.168.0.4";
//    private final static String SERVER_HOST = "localhost";
//    private final static String SERVER_HOST = "127.0.0.1";
    private final static int SERVER_PORT = 6000;

    private ChatClient mClient;

    private Button mUp;
    private Button mDown;
    private Button mLeft;
    private Button mRight;
    private Button mEnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_controller);

        init();

        mClient = new ChatClient();
    }

    private void init() {
        mUp = (Button) findViewById(R.id.btn_up);
        mDown = (Button) findViewById(R.id.btn_down);
        mLeft = (Button) findViewById(R.id.btn_left);
        mRight = (Button) findViewById(R.id.btn_right);
        mEnter = (Button) findViewById(R.id.btn_enter);

        mUp.setOnClickListener(this);
        mDown.setOnClickListener(this);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mEnter.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 서버와 연결
                mClient.connect(SERVER_HOST, SERVER_PORT);
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 서버와 연결 종료
        mClient.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                mClient.sendMessage("u");
                break;
            case R.id.btn_down:
                mClient.sendMessage("d");
                break;
            case R.id.btn_left:
                mClient.sendMessage("l");
                break;
            case R.id.btn_right:
                mClient.sendMessage("r");
                break;
            case R.id.btn_enter:
                mClient.sendMessage("e");
                break;
        }
    }
}
