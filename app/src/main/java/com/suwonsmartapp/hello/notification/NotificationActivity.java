package com.suwonsmartapp.hello.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.multimedia.MediaPlayerActivity;

/**
 * http://developer.android.com/training/notify-user/index.html
 */
public class NotificationActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.btn_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MediaPlayerActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 현재 설정되어 착신음
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        // res/raw 폴더에 있는 mp3 파일 uri 로 가져오기
        Uri uri = Uri.parse("android.resource://" + getPackageName() +"/" + R.raw.alarm);

        // Notification.Builder 작성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle("타이틀");
        builder.setContentText("내용");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSound(uri);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("안녕하세요"));

//        <uses-permission android:name="ANDROID.PERMISSION.VIBRATE" /> 필요
        // 진동 패턴 배열 : 짝수 번째가 대기시간, 홀수 번째가 진동시간
//        builder.setVibrate(new long[] { 1000, 200, 500});

        // 진동, 소리, 알림 불빛
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(0, builder.build());
    }
}
