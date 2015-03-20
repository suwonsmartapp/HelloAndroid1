
package com.suwonsmartapp.hello.challenge;

import com.suwonsmartapp.hello.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();

    private Calendar mCalendar;

    private int mYear;
    private int mMonth;
    private int mDay;

    private ArrayList<String> mItems;

    private GridView mGridView;

    private TextView mDisplayMonthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mGridView = (GridView) findViewById(R.id.gridview);
        mDisplayMonthTextView = (TextView) findViewById(R.id.tv_month);

        // 오늘 날짜
        recalculate();

        // 캘린더 로드
        loadCalendar(mYear, mMonth);

        // 버튼 이벤트
        findViewById(R.id.btn_prev_month).setOnClickListener(this);
        findViewById(R.id.btn_next_month).setOnClickListener(this);
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
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prev_month:
                mCalendar.add(Calendar.MONTH, -1);
                break;
            case R.id.btn_next_month:
                mCalendar.add(Calendar.MONTH, 1);
                break;
        }
        Log.d(TAG, mCalendar.toString());

        recalculate();
        loadCalendar(mYear, mMonth);
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
            mItems.add("");
        }
        // 날짜
        for (int i = 1; i <= lastDay; i++) {
            mItems.add(String.valueOf(i));
        }

        // 어댑터
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, mItems);

        // GridView에 어댑터 설정
        mGridView.setAdapter(adapter);

        // TextView 변경
        mDisplayMonthTextView.setText(mYear + "년 " + mMonth + "월");
    }
}
