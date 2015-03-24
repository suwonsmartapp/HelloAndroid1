
package com.suwonsmartapp.hello.challenge.challenge07_08;

import com.suwonsmartapp.hello.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CalendarActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();

    // 달력과 일정을 연결하는 Map
    private HashMap<Calendar, ArrayList<String>> mScheduleMap;

    private CalendarView mCalendarView;
    private ListView mScheduleListView;
    private TextView mDisplayMonthTextView;

    // 달력 표시용 어댑터
    private CalendarAdapter mCalendarAdapter;

    // 일정 표시용 어댑터
    private ArrayAdapter<String> mScheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = (CalendarView) findViewById(R.id.gridview);
        mDisplayMonthTextView = (TextView) findViewById(R.id.tv_month);
        mScheduleListView = (ListView) findViewById(R.id.lv_schedule);

        // 오늘 날짜
        mCalendarAdapter = new CalendarAdapter(getApplicationContext());
        mCalendarView.setAdapter(mCalendarAdapter);
        mCalendarView.setOnItemClickListener(this);

        // TextView 변경
        setText();

        // 버튼 이벤트
        findViewById(R.id.btn_prev_month).setOnClickListener(this);
        findViewById(R.id.btn_next_month).setOnClickListener(this);

        // Map 초기화
        mScheduleMap = new HashMap<>();
    }

    private void setText() {
        mDisplayMonthTextView.setText(mCalendarAdapter.getYear() + "년 "
                + mCalendarAdapter.getMonth() + "월");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prev_month:
                mCalendarAdapter.setPrevMonth();
                mCalendarAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_next_month:
                mCalendarAdapter.setNextMonth();
                mCalendarAdapter.notifyDataSetChanged();
                break;
        }
        setText();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 선택. 배경색 변경
        mCalendarAdapter.setSelectedPosition(position);

        // 일정 가져오기
        final Calendar key = (Calendar) mCalendarAdapter.getItem(position);
        ArrayList<String> scheduleList = mScheduleMap.get(key);
        if (scheduleList == null) {
            mScheduleListView.setAdapter(null);
        } else {
            mScheduleAdapter = new ArrayAdapter<String>(CalendarActivity.this,
                    android.R.layout.simple_list_item_1, scheduleList);
            mScheduleListView.setAdapter(mScheduleAdapter);
        }
    }

    private void showScheduleInputDialog(final Calendar key) {
        // 일정 입력 다이얼로그 보이기
        // Custom Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);

        // Get the layout inflater
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_schedule_input, null);
        builder.setView(rootView);

        final EditText schedule = (EditText) rootView.findViewById(R.id.dialog_edittext_schedule);
        final EditText time = (EditText) rootView.findViewById(R.id.dialog_edittext_schedule_time);

        // Add action buttons
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 저장 처리
                if (!TextUtils.isEmpty(schedule.getText().toString())
                        && !TextUtils.isEmpty(time.getText().toString())) {
                    ArrayList<String> value = mScheduleMap.get(key);
                    if (value == null) {
                        value = new ArrayList<String>();
                    }
                    value.add(time.getText().toString() + " " + schedule.getText().toString());

                    mScheduleAdapter = new ArrayAdapter<String>(CalendarActivity.this,
                            android.R.layout.simple_list_item_1, value);

                    mScheduleListView.setAdapter(mScheduleAdapter);

                    mScheduleMap.put(key, value);
                }
            }
        });
        builder.setNegativeButton("닫기", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.menu_schedule_input) {
            Calendar selectedItem = (Calendar) mCalendarAdapter.getItem(mCalendarAdapter
                    .getSelectedPosition());
            showScheduleInputDialog(selectedItem);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
