
package com.suwonsmartapp.hello.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "com.suwonsmartapp.hello.TestBroadcast") {
            Toast.makeText(context, "1번 브로드캐스트 잘 왔습니다.", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction() == "com.suwonsmartapp.hello.TestBroadcast2") {
            Toast.makeText(context, "2번 브로드캐스트 잘 왔습니다.", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction() == Intent.ACTION_BATTERY_LOW) {
            Toast.makeText(context, "베터리가 없습니다", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED) {
            Toast.makeText(context, "베터리가 만땅", Toast.LENGTH_SHORT).show();
        }

    }
}
