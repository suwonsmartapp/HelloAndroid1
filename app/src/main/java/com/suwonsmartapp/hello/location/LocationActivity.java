
package com.suwonsmartapp.hello.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.suwonsmartapp.hello.R;

/**
 * http://developer.android.com/training/location/index.html
 *
 * http://developer.android.com/training/location/retrieve-current.html
 */
public class LocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private boolean mIsConneted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        buildGoogleApiClient();

        // Google Play Service 에 연결
        mGoogleApiClient.connect();

        // 버튼 이벤트 연결
        findViewById(R.id.btn_location).setOnClickListener(this);
    }

    /**
     * Google Play Service 에 연결하는 객체를 생성
     * http://developer.android.com/training/location/retrieve-current.html#play-services
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getApplicationContext(), "onConnected : " + bundle, Toast.LENGTH_SHORT).show();
        mIsConneted = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "onConnectionSuspended : " + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed : " + connectionResult, Toast.LENGTH_SHORT).show();
        mIsConneted = false;
    }

    @Override
    public void onClick(View v) {
        if (mIsConneted) {
            // 새로운 방식
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        } else {
            // Play Service 가 안 깔려 있는 경우, 기존 방식 사용
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (mLastLocation != null) {
            Toast.makeText(getApplicationContext(),
                    "lat : " + mLastLocation.getLatitude() +
                            ", lon : " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }
}
