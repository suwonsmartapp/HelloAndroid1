
package com.suwonsmartapp.hello.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.listview.adapter.CustomAdapter;
import com.suwonsmartapp.hello.listview.data.Person;

import java.util.ArrayList;

/**
 * page. 345 ~ 355
 */
public class ListViewExam02Activity extends ActionBarActivity {

    private ArrayList<Person> mNameList;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam01);

        mListView = (ListView) findViewById(R.id.listView);

        // Data 준비
        mNameList = new ArrayList<>();
        mNameList.add(new Person("1219", "오준석", "Android"));
        mNameList.add(new Person("1015", "유준택", "fortran"));
        mNameList.add(new Person("1022", "손상문", "web"));
        mNameList.add(new Person("0529", "배꽃그리고솔", "Java"));
        mNameList.add(new Person("0405", "신남교", "부동산"));
        mNameList.add(new Person("1015", "현기웅", "web"));
        mNameList.add(new Person("1219", "오준석", "Android"));
        mNameList.add(new Person("1015", "유준택", "fortran"));
        mNameList.add(new Person("1022", "손상문", "web"));
        mNameList.add(new Person("0529", "배꽃그리고솔", "Java"));
        mNameList.add(new Person("0405", "신남교", "부동산"));
        mNameList.add(new Person("1015", "현기웅", "web"));
        mNameList.add(new Person("1219", "오준석", "Android"));
        mNameList.add(new Person("1015", "유준택", "fortran"));
        mNameList.add(new Person("1022", "손상문", "web"));
        mNameList.add(new Person("0529", "배꽃그리고솔", "Java"));
        mNameList.add(new Person("0405", "신남교", "부동산"));
        mNameList.add(new Person("1015", "현기웅", "web"));

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                0, mNameList);

        // View에 붙이기
        mListView.setAdapter(adapter);

        // 이벤트 처리
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        ListViewExam02Activity.this,
                        "position : " + position + ", id : " + id + ", data->text : "
                                + mNameList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // 줄 없애기
        mListView.setDivider(null);

    }

}
