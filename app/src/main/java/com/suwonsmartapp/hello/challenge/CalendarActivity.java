
package com.suwonsmartapp.hello.challenge;

import com.suwonsmartapp.hello.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CalendarActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();

    private Calendar mCalendar;

    private int mYear;
    private int mMonth;

    // 달력에 표시될 데이터
    private ArrayList<Calendar> mItems;

    // 일정 저장
    private HashMap<Calendar, String> mScheduleMap;

    private GridView mGridView;

    private TextView mDisplayMonthTextView;

    private CalendarAdapter mAdapter;
    private EditText mScheduleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mGridView = (GridView) findViewById(R.id.gridview);
        mDisplayMonthTextView = (TextView) findViewById(R.id.tv_month);
        mScheduleEditText = (EditText) findViewById(R.id.edittext_schedule);

        // 오늘 날짜
        recalculate();

        // 캘린더 로드
        loadCalendar(mYear, mMonth);

        // 버튼 이벤트
        findViewById(R.id.btn_prev_month).setOnClickListener(this);
        findViewById(R.id.btn_next_month).setOnClickListener(this);
        mGridView.setOnItemClickListener(this);

        // 일정
        mScheduleMap = new HashMap<>();
        findViewById(R.id.btn_save_schedule).setOnClickListener(this);
    }

    /**
     * 전역변수들 다시 계산
     */
    private void recalculate() {
        if (mCalendar == null) {
            mCalendar = GregorianCalendar.getInstance();
        }
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prev_month:
                mCalendar.add(Calendar.MONTH, -1);
                recalculate();
                loadCalendar(mYear, mMonth);
                mScheduleEditText.setText("");
                break;
            case R.id.btn_next_month:
                mCalendar.add(Calendar.MONTH, 1);
                recalculate();
                loadCalendar(mYear, mMonth);
                mScheduleEditText.setText("");
                break;
            case R.id.btn_save_schedule:
                mScheduleMap.put((Calendar) mAdapter.getItem(mAdapter.getSelectedPosition()),
                        mScheduleEditText.getText().toString());
                break;
        }
    }

    /**
     * 캘린더를 화면에 표시
     * 
     * @param year 년
     * @param month 월
     */
    private void loadCalendar(int year, int month) {
        // year년 month월로 캘린더 설정
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.DAY_OF_MONTH, month - 1);

        // 이번달 1일이 몇 번째에 있는지 1 ~ 7
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        // 이번달 마지막 날
        int lastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        mItems = new ArrayList<>();
        // 공백
        for (int i = 1; i < dayOfWeek; i++) {
            mItems.add(null);
        }
        // 날짜
        for (int i = 1; i <= lastDay; i++) {
            mItems.add(new GregorianCalendar(year, month, i));
        }

        // 어댑터
        mAdapter = new CalendarAdapter(getApplicationContext(), mItems);

        // GridView에 어댑터 설정
        mGridView.setAdapter(mAdapter);

        // TextView 변경
        mDisplayMonthTextView.setText(year + "년 " + month + "월");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.setSelectedPosition(position);
        String schedule = mScheduleMap.get(mAdapter.getItem(position));
        mScheduleEditText.setText(schedule);

        // 일정 입력 다이얼로그 보이기
        showScheduleInputDialog();
    }

    private void showScheduleInputDialog() {
        // Custom Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_schedule_input, null))
                // Add action buttons
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 저장 처리
                    }
                })
                .setNegativeButton("닫기", null);
        builder.show();
    }
}
