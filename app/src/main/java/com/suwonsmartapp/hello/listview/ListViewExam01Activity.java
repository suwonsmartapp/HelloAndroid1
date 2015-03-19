
package com.suwonsmartapp.hello.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

/**
 * page. 345 ~ 355
 */
public class ListViewExam01Activity extends ActionBarActivity {

    private ArrayList<String> mNameList;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam01);

        mListView = (ListView) findViewById(R.id.listView);

        // Data 준비
        mNameList = new ArrayList<>();
        mNameList.add("오준석");
        mNameList.add("유준택");
        mNameList.add("손상문");
        mNameList.add("배꽃그리고솔");
        mNameList.add("신남교");
        mNameList.add("현기웅");
        mNameList.add("오준석");
        mNameList.add("유준택");
        mNameList.add("손상문");
        mNameList.add("배꽃그리고솔");
        mNameList.add("신남교");
        mNameList.add("현기웅");
        mNameList.add("오준석");
        mNameList.add("유준택");
        mNameList.add("손상문");
        mNameList.add("배꽃그리고솔");
        mNameList.add("신남교");
        mNameList.add("현기웅");
        mNameList.add("오준석");
        mNameList.add("유준택");
        mNameList.add("손상문");
        mNameList.add("배꽃그리고솔");
        mNameList.add("신남교");
        mNameList.add("현기웅");

        // Adapter 준비
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, mNameList);

        // View에 붙이기
        mListView.setAdapter(adapter);

        // 이벤트 처리
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        ListViewExam01Activity.this,
                        "position : " + position + ", id : " + id + ", data->text : "
                                + mNameList.get(position) + ", adapter->text : "
                                + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
