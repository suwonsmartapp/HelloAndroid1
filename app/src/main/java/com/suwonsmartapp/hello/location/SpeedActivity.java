
package com.suwonsmartapp.hello.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class SpeedActivity extends ActionBarActivity implements LocationListener {

    private long startTime = -1;

    private Location beforeLocation;

    private Location curLocation;

    private TextView mSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        mSpeed = (TextView)findViewById(R.id.tv_speed);

        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        criteria.setPowerRequirement(Criteria.POWER_LOW);

        criteria.setAltitudeRequired(true);

        criteria.setBearingRequired(false);

        criteria.setSpeedRequired(false);

        criteria.setCostAllowed(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(criteria, true);

        if (!locationManager.isProviderEnabled(provider)
                && locationManager.getLastKnownLocation(provider) != null) {

            locationManager.requestLocationUpdates(
                    provider,
                    1000,
                    10, this);

        } else {

            criteria.setAccuracy(Criteria.ACCURACY_COARSE);

            provider = locationManager.getBestProvider(criteria, true);

            locationManager.requestLocationUpdates(

                    provider,

                    1000,

                    10, this);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (beforeLocation == null) {
            beforeLocation = location;
            Toast.makeText(getApplicationContext(), beforeLocation.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        // GPS 변경에 따른 코딩 구현.

        if (startTime == -1) {

            startTime = location.getTime();

        }

        // 현재 위치 거리 및 속도 구하기.

        float distance[] = new float[1];

        Location.distanceBetween(beforeLocation.getLatitude(), beforeLocation.getLongitude(),

                location.getLatitude(), location.getLongitude(), distance);

        long delay = location.getTime() - startTime;

        double speed = distance[0] / delay;

        double speedKMH = speed * 3600;

        mSpeed.setText(String.valueOf(speedKMH) + " Km");

        // 전 위치 저장.

        beforeLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
