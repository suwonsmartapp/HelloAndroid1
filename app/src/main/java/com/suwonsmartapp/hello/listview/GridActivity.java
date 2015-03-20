
package com.suwonsmartapp.hello.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

public class GridActivity extends ActionBarActivity implements
        AdapterView.OnItemClickListener {

    private GridView mGridView;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        mGridView = (GridView) findViewById(R.id.gridview);

        // 데이터 준비
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }

        // 어댑터 준비
        adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, list);

        mGridView.setAdapter(adapter);

        // 이벤트
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                "position : " + position + ", data : " + list.get(position), Toast.LENGTH_SHORT)
                .show();

        // 데이타를 변경 후, 어댑터에 알려주고 화면을 갱신
        list.set(position, list.get(position) + "0");
        adapter.notifyDataSetChanged();
    }
}
