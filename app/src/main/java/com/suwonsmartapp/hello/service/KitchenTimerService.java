
package com.suwonsmartapp.hello.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class KitchenTimerService extends Service {
    public static final String ACTION = "com.suwonsmartapp.hello.service.KitchenTimer.ACTION";
    private Timer timer;

    public void schedule(long delay) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                sendBroadcast(new Intent(ACTION));
            }
        };
        timer.schedule(timerTask, delay);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Toast.makeText(getApplicationContext(), "onBind", Toast.LENGTH_SHORT).show();
        return new KichenTimerBinder();
    }

    class KichenTimerBinder extends Binder {
        KitchenTimerService getService() {
            return KitchenTimerService.this;
        }
    }

}
