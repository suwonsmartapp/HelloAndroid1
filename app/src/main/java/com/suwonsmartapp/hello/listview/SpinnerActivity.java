
package com.suwonsmartapp.hello.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class SpinnerActivity extends ActionBarActivity implements
        AdapterView.OnItemSelectedListener {

    // 1. Spinner에 보여 줄 데이터 준비
    private final String[] COLORS = {
            "Black", "Blue", "Red", "White", "Orange", "Yellow"
    };

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        mSpinner = (Spinner) findViewById(R.id.spinner);

        // 2. Adapter에 데이터 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, COLORS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 3. Spinner에 Adapter 설정
        mSpinner.setAdapter(adapter);

        // 4. 이벤트 리스너 연결
        mSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                "position : " + position + ", id : " + id + ", value : " + COLORS[position],
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),
                "선택 한 것이 없음",
                Toast.LENGTH_SHORT).show();
    }
}
