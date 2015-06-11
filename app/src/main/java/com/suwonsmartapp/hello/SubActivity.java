
package com.suwonsmartapp.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suwonsmartapp.hello.activity.ActivityExamActivity;
import com.suwonsmartapp.hello.activity.EditTextActivity;
import com.suwonsmartapp.hello.activity.FirstActivity;
import com.suwonsmartapp.hello.activity.FrameLayoutActivity;
import com.suwonsmartapp.hello.activity.JNIActivity;
import com.suwonsmartapp.hello.activity.NavigationActivity;
import com.suwonsmartapp.hello.activity.RelativeLayoutExamActivity;
import com.suwonsmartapp.hello.activity.SecondActivity;
import com.suwonsmartapp.hello.activity.TableLayoutActivity;
import com.suwonsmartapp.hello.activity.ToolbarActivity;
import com.suwonsmartapp.hello.activity.ViewPagerActivity;
import com.suwonsmartapp.hello.bitmap.BitmapActivity;
import com.suwonsmartapp.hello.broadcast.BroadcastActivity;
import com.suwonsmartapp.hello.camera.builtin.CameraBuiltinActivity;
import com.suwonsmartapp.hello.camera.intent.CameraIntentActivity;
import com.suwonsmartapp.hello.challenge.challenge01.ImageExamActivity;
import com.suwonsmartapp.hello.challenge.challenge02.SMSActivity;
import com.suwonsmartapp.hello.challenge.challenge05.Mission05MainActivity;
import com.suwonsmartapp.hello.challenge.challenge06.Mission06MainActivity;
import com.suwonsmartapp.hello.challenge.challenge07_08.CalendarActivity;
import com.suwonsmartapp.hello.challenge.challenge17.MovieListActivity;
import com.suwonsmartapp.hello.chat.ClientActivity;
import com.suwonsmartapp.hello.contentprovider.ContactActivity;
import com.suwonsmartapp.hello.event.TouchEventActivity;
import com.suwonsmartapp.hello.filemanager.FileManagerActivity;
import com.suwonsmartapp.hello.fragment.FragmentMainActivity;
import com.suwonsmartapp.hello.graphic.GraphicActivity;
import com.suwonsmartapp.hello.listview.GridActivity;
import com.suwonsmartapp.hello.listview.ListViewExam01Activity;
import com.suwonsmartapp.hello.listview.ListViewExam02Activity;
import com.suwonsmartapp.hello.listview.SpinnerActivity;
import com.suwonsmartapp.hello.location.LocationActivity;
import com.suwonsmartapp.hello.location.SpeedActivity;
import com.suwonsmartapp.hello.multimedia.MediaPlayerActivity;
import com.suwonsmartapp.hello.notification.NotificationActivity;
import com.suwonsmartapp.hello.parsing_json.ParsingActivity;
import com.suwonsmartapp.hello.remotecontroller.client.RemoteControllerActivity;
import com.suwonsmartapp.hello.save.db.DbActivity;
import com.suwonsmartapp.hello.save.file.FileActivity;
import com.suwonsmartapp.hello.save.preference.SettingsActivity;
import com.suwonsmartapp.hello.save.preference.SharedPreferenceActivity;
import com.suwonsmartapp.hello.sensor.BallGameActivity;
import com.suwonsmartapp.hello.service.KitchenTimerActivity;
import com.suwonsmartapp.hello.thread.AsyncTaskActivity;
import com.suwonsmartapp.hello.thread.LoginActivity;
import com.suwonsmartapp.hello.thread.ThreadActivity;
import com.suwonsmartapp.hello.thread.TimerActivity;

/**
 * Created by junsuk on 15. 3. 19..
 */
public class SubActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String[] ACTIVITY_ITEMS = {
            "Activity 예제",
            "EditTextActivity",
            "FirstActivity",
            "FrameLayout",
            "RelativeLayout",
            "SecondActivity",
            "TableLayout",
            "ViewPager",
            "NavigationDrawer",
            "Facebook Login",
            "ToolBar + NavigationView",
            "JNI"
    };
    private static final Class[] ACTIVITY_CLASSES = {
            ActivityExamActivity.class,
            EditTextActivity.class,
            FirstActivity.class,
            FrameLayoutActivity.class,
            RelativeLayoutExamActivity.class,
            SecondActivity.class,
            TableLayoutActivity.class,
            ViewPagerActivity.class,
            NavigationActivity.class,
            com.suwonsmartapp.hello.activity.LoginActivity.class,
            ToolbarActivity.class,
            JNIActivity.class
    };

    private static final String[] CHALLENGE_ITEMS = {
            "Challenge01",
            "Challenge02",
            "Challenge04",
            "Challenge05",
            "Challenge06",
            "Challenge07~08",
            "Challenge17"
    };

    private static final Class[] CHALLENGE_CLASSES = {
            ImageExamActivity.class,
            SMSActivity.class,
            com.suwonsmartapp.hello.challenge.challenge04.MainActivity.class,
            Mission05MainActivity.class,
            Mission06MainActivity.class,
            CalendarActivity.class,
            MovieListActivity.class
    };

    private static final String[] EVENT_ITEMS = {
            "TouchEvent",
            "리모콘"
    };

    private static final Class[] EVENT_CLASSES = {
            TouchEventActivity.class,
            RemoteControllerActivity.class
    };

    private static final String[] LISTVIEW_ITEMS = {
            "ArrayAdapter",
            "CustomAdapter",
            "SpinnerActivity",
            "GridActivity"
    };

    private static final Class[] LISTVIEW_CLASSES = {
            ListViewExam01Activity.class,
            ListViewExam02Activity.class,
            SpinnerActivity.class,
            GridActivity.class
    };

    private static final String[] THREAD_ITEMS = {
            "Thread, Handler",
            "AsyncTask",
            "Login",
            "Timer",
            "KitchenTimer",
            "채팅 클라이언트"
    };

    private static final Class[] THREAD_CLASSES = {
            ThreadActivity.class,
            AsyncTaskActivity.class,
            LoginActivity.class,
            TimerActivity.class,
            KitchenTimerActivity.class,
            ClientActivity.class
    };

    private static final String[] BROADCASTRECEIVER_ITEMS = {
            "BroadcastReceiver"
    };

    private static final Class[] BROADCASTRECEIVER_CLASSES = {
            BroadcastActivity.class
    };

    private static final String[] PARSING_ITEMS = {
            "Json Parsing",
            "Html Parsing"
    };

    private static final Class[] PARSING_CLASSES = {
            ParsingActivity.class,
            com.suwonsmartapp.hello.parsing_xml.ParsingActivity.class,
    };

    private static final String[] MAP_ITEMS = {
            "Google Map"
    };

    private static final Class[] MAP_CLASSES = {
            com.suwonsmartapp.hello.map.MainActivity.class
    };

    private static final String[] GRAPHIC_ITEMS = {
            "Bitmap"
    };

    private static final Class[] GRAPHIC_CLASSES = {
            BitmapActivity.class
    };

    private static final String[] CUSTOM_ITEMS = {
            "Custom View"
    };

    private static final Class[] CUSTOM_CLASSES = {
            GraphicActivity.class
    };

    private static final String[] SAVE_ITEMS = {
            "SharedPrefence",
            "SettingsActivity",
            "File 저장",
            "Database"
    };

    private static final Class[] SAVE_CLASSES = {
            SharedPreferenceActivity.class,
            SettingsActivity.class,
            FileActivity.class,
            DbActivity.class
    };

    private static final String[] CAMERA_ITEMS = {
            "Intent 호출",
            "나만의 카메라 앱 "
    };

    private static final Class[] CAMERA_CLASSES = {
            CameraIntentActivity.class,
            CameraBuiltinActivity.class
    };

    private static final String[] MULTI_ITEMS = {
            "플레이어"
    };

    private static final Class[] MULTI_CLASSES = {
            MediaPlayerActivity.class
    };

    private static final String[] PROVIDER_ITEMS = {
            "주소록 가져오기"
    };

    private static final Class[] PROVIDER_CLASSES = {
            ContactActivity.class
    };

    private static final String[] FILEMANAGER_ITEMS = {
            "파일관리자"
    };

    private static final Class[] FILEMANAGER_CLASSES = {
            FileManagerActivity.class
    };

    private static final String[] LOCATION_ITEMS = {
            "현재 위치 알아내기",
            "속도 구하기"
    };

    private static final Class[] LOCATION_CLASSES = {
            LocationActivity.class,
            SpeedActivity.class
    };

    private static final String[] NOTIFICATION_ITEMS = {
            "Notification 예제"
    };

    private static final Class[] NOTIFICATION_CLASSES = {
            NotificationActivity.class
    };

    private static final String[] FRAGMENT_ITEMS = {
            "Fragment"
    };

    private static final Class[] FRAGMENT_CLASSES = {
            FragmentMainActivity.class
    };

    private static final String[] SENSOR_ITEMS = {
            "BallGame"
    };

    private static final Class[] SENSOR_CLASSES = {
            BallGameActivity.class
    };

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam01);

        mListView = (ListView) findViewById(R.id.listView);

        if (getItems() != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, getItems().first);

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);
        }
    }

    private Pair<String[], Class[]> getItems() {
        Pair<String[], Class[]> result = null;

        String menu = getIntent().getStringExtra("menu");
        switch (menu) {
            case "Activity":
                result = new Pair(ACTIVITY_ITEMS, ACTIVITY_CLASSES);
                break;
            case "Challenge":
                result = new Pair(CHALLENGE_ITEMS, CHALLENGE_CLASSES);
                break;
            case "Event":
                result = new Pair(EVENT_ITEMS, EVENT_CLASSES);
                break;
            case "ListView":
                result = new Pair(LISTVIEW_ITEMS, LISTVIEW_CLASSES);
                break;
            case "Thread":
                result = new Pair(THREAD_ITEMS, THREAD_CLASSES);
                break;
            case "BroadcastReceiver":
                result = new Pair(BROADCASTRECEIVER_ITEMS, BROADCASTRECEIVER_CLASSES);
                break;
            case "Parsing":
                result = new Pair(PARSING_ITEMS, PARSING_CLASSES);
                break;
            case "Map":
                result = new Pair(MAP_ITEMS, MAP_CLASSES);
                break;
            case "Graphic":
                result = new Pair(GRAPHIC_ITEMS, GRAPHIC_CLASSES);
                break;
            case "Custom":
                result = new Pair(CUSTOM_ITEMS, CUSTOM_CLASSES);
                break;
            case "Save":
                result = new Pair(SAVE_ITEMS, SAVE_CLASSES);
                break;
            case "Camera":
                result = new Pair(CAMERA_ITEMS, CAMERA_CLASSES);
                break;
            case "Multimedia":
                result = new Pair(MULTI_ITEMS, MULTI_CLASSES);
                break;
            case "ContentProvider":
                result = new Pair(PROVIDER_ITEMS, PROVIDER_CLASSES);
                break;
            case "파일관리자":
                result = new Pair(FILEMANAGER_ITEMS, FILEMANAGER_CLASSES);
                break;
            case "위치":
                result = new Pair(LOCATION_ITEMS, LOCATION_CLASSES);
                break;
            case "Notification":
                result = new Pair(NOTIFICATION_ITEMS, NOTIFICATION_CLASSES);
                break;
            case "Fragment":
                result = new Pair(FRAGMENT_ITEMS, FRAGMENT_CLASSES);
                break;
            case "Sensor":
                result = new Pair(SENSOR_ITEMS, SENSOR_CLASSES);
                break;
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getApplicationContext(), getItems().second[position]));

        // Class.forName 으로 정보 가져오는 방법
        // Class c = null;
        // try {
        // c =
        // Class.forName("com.suwonsmartapp.hello.activity.ActivityExamActivity");
        // startActivity(new Intent(getApplicationContext(), c));
        // } catch (ClassNotFoundException e) {
        // Toast.makeText(getApplicationContext(), "없다",
        // Toast.LENGTH_SHORT).show();
        // }
    }
}
