package com.suwonsmartapp.hello.contentprovider;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * ListActivity : 전체 ListView 로 꽉찬 Activity
 *
 * <uses-permission android:name="android.permission.WRITE_CONTACTS" />
 * <uses-permission android:name="android.permission.READ_CONTACTS" />
 */
public class ContactActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 비디오 : MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        // 오디오 : MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        // 주소록 : ContactsContract.Contacts.CONTENT_URI
        // 폰 : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 이미지 : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 파일 : MediaStore.Files.getContentUri(uri)
        // 캘린더 : CalendarContract.Calendars.CONTENT_URI

        // 전체 주소록 가져오기
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " + String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE),    // Where 절 : WHERE data2=2
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC"    // ASC : 오름차순, DESC : 내림차순
        );

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[] { android.R.id.text1, android.R.id.text2 },
                0);

        setListAdapter(adapter);
    }

}
