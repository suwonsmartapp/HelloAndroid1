
package com.suwonsmartapp.hello.service;

import com.suwonsmartapp.hello.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class KitchenTimerActivity extends Activity {

    private KitchenTimerService kitchenTimerService;
    private KitchenTimerReceiver receiver = new KitchenTimerReceiver();

    private ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            kitchenTimerService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            kitchenTimerService = ((KitchenTimerService.KichenTimerBinder) service).getService();
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_service);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        Button button = (Button) findViewById(R.id.button1);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                long hour = timePicker.getCurrentHour();
                long min = timePicker.getCurrentMinute();

                kitchenTimerService.schedule((hour * 60 + min) * 60 * 100);
                moveTaskToBack(true);
                // onBackPressed();
            }
        });

        Intent intent = new Intent(this, KitchenTimerService.class);
        startService(intent);

        IntentFilter filter = new IntentFilter(KitchenTimerService.ACTION);

        registerReceiver(receiver, filter);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        unbindService(serviceConnection);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver); //
        kitchenTimerService.stopSelf(); //
        super.onDestroy();
    }

}
