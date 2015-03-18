
package com.suwonsmartapp.hello.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

public class ListViewExam01Activity extends ActionBarActivity {

    private ArrayList<String> mNameList;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam01);

        mListView = (ListView)findViewById(R.id.listView);

        // Data 준비
        mNameList = new ArrayList<>();
        mNameList.add("오준석");
        mNameList.add("유준택");
        mNameList.add("손상문");
        mNameList.add("배꽃그리고솔");
        mNameList.add("신남교");
        mNameList.add("현기웅");

        // Adapter 준비
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, mNameList);

        // View에 붙이기
        mListView.setAdapter(adapter);

    }

}
