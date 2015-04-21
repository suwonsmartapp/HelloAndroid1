
package com.suwonsmartapp.hello.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.chat.client.ChatClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by junsuk on 15. 4. 17..
 */
public class ClientActivity extends Activity implements View.OnClickListener,
        ChatClient.ClientCallback {

    private ChatClient mChatClient;
    private EditText mMessageEditText;
    private ChatAdapter mChatAdapter;
    private ListView mChatListView;
    private ArrayList<ChatHistory> mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilent);

        init();

        connectServer(this);
    }

    private void init() {
        mHistory = new ArrayList<>();
        mChatAdapter = new ChatAdapter(getApplicationContext(), mHistory);

        mMessageEditText = (EditText) findViewById(R.id.et_message);
        mChatListView = (ListView) findViewById(R.id.lv_chat);
        mChatListView.setAdapter(mChatAdapter);

        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    private void connectServer(final ChatClient.ClientCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mChatClient = new ChatClient();
                mChatClient.setClientCallback(callback);
                mChatClient.connect();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                mChatClient.sendMessage(mMessageEditText.getText().toString());
                mMessageEditText.setText("");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mChatClient.disconnect();
    }

    private DateFormat mDateFormat = new SimpleDateFormat("a KK:mm");

    @Override
    public void onReceiveMessage(String message) {
        ChatHistory history = null;
        if ("[".equals(message.substring(0, 1))) {
            String nickname = message.substring(1, message.indexOf("]"));
            String realMsg = message.substring(message.indexOf("]") + 2);
            String time = mDateFormat.format(Calendar.getInstance().getTime());
            history = new ChatInfo(nickname, realMsg, time);

            if (getNickName().equals(nickname)) {
                ((ChatInfo)history).setIsMe(true);
            }
        } else {
            history = new Notice(message);
        }

        mHistory.add(history);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChatAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public String getNickName() {
        return "오준석";
    }
}
