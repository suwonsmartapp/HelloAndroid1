package com.suwonsmartapp.hello.activity.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

/**
 * Created by junsuk on 15. 6. 19..
 */
public class DbExamActivity extends Activity {

    DbHelper 디비헬퍼;
    private ListView 리스트뷰;
    private Button 검색버튼;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_db2);

        리스트뷰 = (ListView)findViewById(R.id.listview);

        디비헬퍼 = new DbHelper(getApplicationContext());
        디비헬퍼.getWritableDatabase();

        검색버튼 = (Button)findViewById(R.id.btn_search);
        검색버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String 검색할이름 = ((EditText)findViewById(R.id.search)).getText().toString();

                조회결과리스트뷰에표시(디비헬퍼.검색(검색할이름));
            }
        });

        Button 등록버튼 = (Button)findViewById(R.id.btn_submit);
        등록버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEt = (EditText) findViewById(R.id.name);
                String 이름 = nameEt.getText().toString();
                String 이메일 = ((EditText) findViewById(R.id.email)).getText().toString();
                String 전화번호 = ((EditText) findViewById(R.id.phone)).getText().toString();

                // 성공하면 추가된 값의 ID, 실패하면 -1
                long 결과값 = 디비헬퍼.추가(이름, 이메일, 전화번호);

                if (결과값 == -1) {
                    Toast.makeText(getApplicationContext(), "삽입 에러", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "삽입 성공", Toast.LENGTH_SHORT).show();



                    // ListView 에 표시

                    // 가짜 데이터
//                    ArrayList<String> 데이터묶음 = new ArrayList<String>();
//                    데이터묶음.add("첫번째 데이터");
//                    데이터묶음.add("두번째 데이터");
//                    데이터묶음.add("세번째 데이터");
                    // 진짜 데이터
                    조회결과리스트뷰에표시(디비헬퍼.검색());
                }
            }
        });


        조회결과리스트뷰에표시(디비헬퍼.검색());
    }

    private void 조회결과리스트뷰에표시(ArrayList<String> 데이터) {
        ArrayList<String> 데이터묶음 = 데이터;

        // 어댑터
        ArrayAdapter<String> 어댑터 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, 데이터묶음);

        리스트뷰.setAdapter(어댑터);
    }
}
