
package com.suwonsmartapp.hello.filemanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.mylibrary.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class FileManagerActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    public static final String sPathSdcard = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public static final String sPathPicture = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String sPathDownload = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();;
    public static final String sPathRoot = "/";

    private ListView mListView;

    // 첫 화면
    private ArrayList<Map<String, String>> mTitleList;
    private SimpleAdapter mAdapter;

    // 히스토리 관리 : 다음 화면으로 가기 전에 현재 mCurrentPath 를 push
    // mCurrentPath = 다음 화면 path
    private Stack<String> mFileStack;

    // 현재의 full path
    private String mCurrentPath = "";

    private TextView mTvCurrentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        mListView = (ListView) findViewById(R.id.lv_filetree);
        mTvCurrentPath = (TextView) findViewById(R.id.tv_currentPath);

        mFileStack = new Stack<>();

        setUpHome();
    }

    /**
     * 첫 번째 화면 구성
     */
    private void setUpHome() {
        Map<String, String> sdcard = new HashMap<>();
        sdcard.put("title", "SD Card");
        sdcard.put("path", sPathSdcard);

        Map<String, String> picture = new HashMap<>();
        picture.put("title", "사진");
        picture.put("path", sPathPicture);

        Map<String, String> download = new HashMap<>();
        download.put("title", "다운로드");
        download.put("path", sPathDownload);

        Map<String, String> root = new HashMap<>();
        root.put("title", "루트");
        root.put("path", sPathRoot);

        mTitleList = new ArrayList<>();
        mTitleList.add(sdcard);
        mTitleList.add(picture);
        mTitleList.add(download);
        mTitleList.add(root);

        // Pair<String, String> sdcard = new Pair<>("SD Card", sPathSdcard);
        // Pair<String, String> picture = new Pair<>("사진", sPathPicture);
        // Pair<String, String> download = new Pair<>("다운로드", sPathDownload);
        // Pair<String, String> root = new Pair<>("루트", sPathRoot);

        mAdapter = new SimpleAdapter(getApplicationContext(),
                mTitleList,
                android.R.layout.simple_list_item_2,
                new String[] {
                        "title", "path"
                },
                new int[] {
                        android.R.id.text1, android.R.id.text2
                });

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = mListView.getAdapter().getItem(position);

        if (item instanceof Map) {
            Map mapData = (Map) item;
            String path = (String) mapData.get("path");

            mFileStack.push("");
            setCurrentPath(path);

            showFileList(path);
        } else if (item instanceof File) {
            // 디렉토리를 클릭했을 때는 그 안으로 들어간다
            File fileData = (File) item;
            if (fileData.isDirectory()) {

                // 히스토리에 path 를 삽입
                mFileStack.push(mCurrentPath);
                setCurrentPath(fileData.getAbsolutePath());

                showFileList(fileData.getAbsolutePath());
            } else {
                try {
                    // 파일인 경우, 해당 파일의 MIME TYPE 을 설정하여 chooser를 호출
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(fileData), getMimeType(fileData.getAbsolutePath()));
                    startActivity(Intent.createChooser(intent, "파일선택..."));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "실행 할 앱이 없습니다.", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private void showFileList(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManagerActivity.this);
            builder.setTitle("오류")
                    .setMessage("파일/폴더를 열 수 없습니다!")
                    .setPositiveButton("확인", null)
                    .show();
            mFileStack.pop();
            return;
        }

        List<File> fileList = new ArrayList<>();

        // for (int i = 0; i < files.length; i++) {
        // File f = files[i];
        // }
        for (File f : files) {
            if (f != null) {
                fileList.add(f);
            }
        }

        // 파일명으로 정렬을 먼저 하고
        Collections.sort(fileList);
        // 디렉토리만
        Collections.sort(fileList, mFolderComparator);


        FileAdapter fileAdapter = new FileAdapter(getApplicationContext(), fileList);

        mListView.setAdapter(fileAdapter);
    }

    Comparator<File> mDescComparator = new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
            String left = lhs.getName();
            String right = rhs.getName();

            return right.compareTo(left);
        }
    };

    Comparator<File> mFolderComparator = new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
            if (!lhs.isDirectory() && rhs.isDirectory()) {
                return 1;
            } else if (lhs.isDirectory() && !rhs.isDirectory()) {
                return -1;
            }

            // 0 보다 크면 lhs가 큰 것, 0보다 작으면 rhs가 큰 것, 0 이면 같다
            return 0;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 스택이 비었으면 (뒤로 갈 게 없다) --> 죽인다
            if (!mFileStack.empty()) {
                // 스택이 안 비었으면, 뒤로 간다
                String prevPath = mFileStack.pop();
                setCurrentPath(prevPath);
                if (prevPath.equals("")) {
                    setUpHome();
                } else {
                    showFileList(prevPath);
                }
                return false;
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    private void setCurrentPath(String path) {
        mCurrentPath = path;
        mTvCurrentPath.setText(mCurrentPath);
    }

    public static String getMimeType(String url) {
        String type = null;

        // 파일명에 공백이 있을 때 확장자를 못 가져오는 이슈 해결책
        // https://code.google.com/p/android/issues/detail?id=5510
        String text = url.substring(url.lastIndexOf("."));
        String extension = MimeTypeMap.getFileExtensionFromUrl(text);

//        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension.toLowerCase());
        }
        return type;
    }
}
