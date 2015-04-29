
package com.suwonsmartapp.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Main Activity - ListView 로 메뉴 구성
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String[] ITEMS = {
            "Activity",
            "Challenge",
            "Event",
            "ListView",
            "Thread",
            "BroadcastReceiver",
            "Parsing",
            "Map",
            "Graphic",
            "Custom",
            "Save",
            "Camera",
            "Multimedia",
            "ContentProvider"
    };

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam01);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, ITEMS);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
        intent.putExtra("menu", ITEMS[position]);
        startActivity(intent);
    }
}
