package com.suwonsmartapp.hello.save.db;

import com.suwonsmartapp.hello.R;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DbActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemLongClickListener {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private Button mSubmitBtn;
    private ListView mPersonListView;

    private PersonHelper mDbHelper;

    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        mNameEditText = (EditText) findViewById(R.id.et_name);
        mEmailEditText = (EditText) findViewById(R.id.et_email);
        mSubmitBtn = (Button) findViewById(R.id.btn_submit);
        mPersonListView = (ListView) findViewById(R.id.lv_person);

        mDbHelper = new PersonHelper(getApplicationContext());
        // // DB가 없을 때 mDbHelper.onCreate 가 호출되며 DB가 생성 됨
        // SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // //mDbHelper.getReadableDatabase();
        //
        // //
        // db.execSQL("INSERT INTO Person (name, email) VALUES ('오준석', 'a811219@gmail.com')");
        // ContentValues values = new ContentValues();
        // values.put("name", "오준석");
        // values.put("email", "a811219@gmail.com");
        // long id = db.insert("Person", null, values);
        //

        // long id = mDbHelper.insert(new Person("손상문", "abc@gamil.com"));
        // Toast.makeText(getApplicationContext(), "insert id : " + id,
        // Toast.LENGTH_SHORT).show();

        mSubmitBtn.setOnClickListener(this);

        mPersonListView.setOnItemLongClickListener(this);

        // 롱클릭시 ContextMenu 호출되도록 등록
        registerForContextMenu(mPersonListView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 모든 정보를 Cursor 로 받음
        Cursor cursor = mDbHelper.selectAll();

        mAdapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {
                        "name", "email"
                },
                new int[] {
                        android.R.id.text1, android.R.id.text2
                },
                0);
        mPersonListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        Person person = new Person();
        person.setName(mNameEditText.getText().toString());
        person.setEmail(mEmailEditText.getText().toString());

        // person 정보를 삽입
        long id = mDbHelper.insert(person);
        Toast.makeText(getApplicationContext(), "insert id : " + id, Toast.LENGTH_SHORT).show();

        // 모든 정보를 Cursor 로 받음
        Cursor cursor = mDbHelper.selectAll();
        mAdapter.swapCursor(cursor);
    }

    int mSeletedPosition = -1;
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "longclick : " + position, Toast.LENGTH_SHORT).show();

        mSeletedPosition = position;

        // return true;         ->> 여기서 이벤트 종료
        return false;       // 처리가 안 된 것으로 알려주고, 이벤트를 흘려서 컨텍스트 메뉴가 동작 되도록
    }

    private void dbDelete(int position) {
        long id;// 모든 data 를 얻어 옴
        Cursor cursor = mDbHelper.selectAll();

        // 롱클릭 한 data 로 cursor 이동
        cursor.moveToPosition(position);

        // _id 컬럼 값 얻어오기
        // id = cursor.getInt(0);
        id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));

        // id 를 가진 data 삭제
        mDbHelper.delete(id);

        // 어댑터 갱신
        cursor = mDbHelper.selectAll();
        mAdapter.swapCursor(cursor);

        Toast.makeText(getApplicationContext(), "삭제 되었습니다 : " + position, Toast.LENGTH_SHORT)
                .show();
    }

    // 롱 클릭시 보여줄 menu 파일 inflate
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_db, menu);
    }

    // 컨텍스트 메뉴 이벤트 처리
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_update:
                dbUpdate();
                return true;
            case R.id.action_delete:
                dbDelete(mSeletedPosition);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void dbUpdate() {
        // 현재 선택 된 위치로 cursor 이동
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(mSeletedPosition);

        int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));

        Person person = new Person();
        person.setName(mNameEditText.getText().toString());
        person.setEmail(mEmailEditText.getText().toString());

        int updatedCount = mDbHelper.update(id, person);

        if (updatedCount > 0) {
            mAdapter.swapCursor(mDbHelper.selectAll());
        }
    }
}
