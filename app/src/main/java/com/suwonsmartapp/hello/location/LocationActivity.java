
package com.suwonsmartapp.hello.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.suwonsmartapp.hello.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * http://developer.android.com/training/location/index.html
 * http://developer.android.com/training/location/retrieve-current.html
 */
public class LocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, LocationListener, ResultCallback<Status> {

    private String REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY";
    private String LOCATION_KEY = "LOCATION_KEY";
    private String LAST_UPDATED_TIME_STRING_KEY = "LAST_UPDATED_TIME_STRING_KEY";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private boolean mIsConneted = false;

    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = false;

    // 현재 위치
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private Geocoder mGeocoder;
    private List<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        buildGoogleApiClient();

        // Google Play Service 에 연결
        mGoogleApiClient.connect();

        // 주기적으로 위치 정보를 받는 객체 초기화
        createLocationRequest();

        // 버튼 이벤트 연결
        findViewById(R.id.btn_location).setOnClickListener(this);

        // 임시로 저장한 데이터 복원
        updateValuesFromBundle(savedInstanceState);

        // 현재 주소를 얻기 위한 객체
        mGeocoder = new Geocoder(this, Locale.getDefault());

        // Geofence리스트 생성
        createGeofenceList();
    }

    private void createGeofenceList() {
        mGeofenceList = new ArrayList<>();
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId("test")

                .setCircularRegion(
                        37.27425,
                        127.0226286,
                        30
                )
                .setExpirationDuration(60 * 60)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    /**
     * 주기적으로 위치 정보를 받는 객체 초기화
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * 위치 정보 업데이트를 시작
     */
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void startGeofence() {
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }


    /**
     * Google Play Service 에 연결하는 객체를 생성
     * http://developer.android.com/training/location
     * /retrieve-current.html#play-services
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
        Toast.makeText(getApplicationContext(), "onConnected : " + bundle, Toast.LENGTH_SHORT)
                .show();
        mIsConneted = true;

        // 위치 정보를 주기적으로 받는 객체
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

        // GeofencingApi 에 내가 지정한 Geofences 추가
        startGeofence();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "onConnectionSuspended : " + i, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed : " + connectionResult,
                Toast.LENGTH_SHORT).show();
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
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (mLastLocation == null) {
                mLastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (mLastLocation != null) {
            // 위도, 경도로부터 주소를 얻는 방법
            List<Address> addressList;
            Address address = null;
            try {
                addressList = mGeocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                address = addressList.get(0);
                Toast.makeText(getApplicationContext(),
                        "address : " + address.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mCurrentLocation = mLastLocation;
            mLastUpdateTime = String.valueOf(mLastLocation.getTime());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        // 10초 간격으로 위치값이 넘어오는 콜백
        Toast.makeText(getApplicationContext(),
                "lat : " + location.getLatitude() +
                        ", lon : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        mCurrentLocation = location;
        mLastUpdateTime = String.valueOf(location.getTime());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
            startGeofence();
        }
    }

    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(), "onPause!!!!!!", Toast.LENGTH_SHORT).show();
        super.onPause();
        stopLocationUpdates();
        stopGeofence();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    protected void stopGeofence() {
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                // This is the same pending intent that was used in addGeofences().
                getGeofencePendingIntent()
        ).setResultCallback(this); // Result processed in onResult().
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "onSaveInstaceState!!!!!!", Toast.LENGTH_SHORT).show();

        // 임시 저장할 데이터를 저장
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
//                setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(
                        LAST_UPDATED_TIME_STRING_KEY);
            }
//            updateUI();
            Toast.makeText(getApplicationContext(), "mLastUpdateTime : " + mLastUpdateTime, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(Status status) {
        // Geofence 결과가 오는 콜백
        Toast.makeText(getApplicationContext(), "geofence status : " + status, Toast.LENGTH_SHORT).show();
    }


}
