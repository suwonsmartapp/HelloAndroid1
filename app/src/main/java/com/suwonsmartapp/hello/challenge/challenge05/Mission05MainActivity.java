
package com.suwonsmartapp.hello.challenge.challenge05;

import com.suwonsmartapp.hello.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mission05MainActivity extends ActionBarActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private Button mSaveBtn;
    private Button mBirthdayBtn;

    private int year, month, day, hour, minute;
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            // String msg = String.format("%d 년 %d 월 %d 일", year, monthOfYear +
            // 1, dayOfMonth);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
            GregorianCalendar tmpCalendar = new GregorianCalendar();
            tmpCalendar.set(year, monthOfYear, dayOfMonth);

            mBirthdayBtn.setText(sf.format(tmpCalendar.getTime()));
            Toast.makeText(getApplicationContext(), sf.format(tmpCalendar.getTime()),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission05_main);

        mNameEditText = (EditText) findViewById(R.id.textView_name);
        mAgeEditText = (EditText) findViewById(R.id.textView_age);
        mBirthdayBtn = (Button) findViewById(R.id.btn_birthday);
        mSaveBtn = (Button) findViewById(R.id.btn_save);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        mBirthdayBtn.setText(year + "년 " + (month + 1) + "월 " + day + "일");
        mBirthdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Mission05MainActivity.this, dateSetListener, year, month, day)
                        .show();
            }
        });
    }

    // TimePickerDialog 참고
    /*
     * private TimePickerDialog.OnTimeSetListener timeSetListener = new
     * TimePickerDialog.OnTimeSetListener() {
     * @Override public void onTimeSet(TimePicker view, int hourOfDay, int
     * minute) { String am_pm = ""; Calendar datetime = Calendar.getInstance();
     * datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
     * datetime.set(Calendar.MINUTE, minute); if (datetime.get(Calendar.AM_PM)
     * == Calendar.AM) am_pm = "AM"; else if (datetime.get(Calendar.AM_PM) ==
     * Calendar.PM) am_pm = "PM"; String strHrsToShow =
     * (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime .get(Calendar.HOUR)
     * + ""; strHrsToShow += " 시 " + datetime.get(Calendar.MINUTE) + " 분 " +
     * am_pm; } };
     */

}
