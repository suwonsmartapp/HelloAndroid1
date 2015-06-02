package com.suwonsmartapp.hello.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

/**
 * Toolbar 사용
 *
 * http://googledevkr.blogspot.kr/2014/10/appcompat-v21-material-design-for-pre.html?m=1
 *
 * 1. xml 에 Toolbar를 추가
 * 2. Theme.AppCompat.NoActionBar 테마를 사용
 * 3. onCreate 에서 setSupportActionBar() 에 Toolbar 설정
 */
public class ToolbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    // Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    // SearchView
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SearchView
        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // 토글 터치 이벤트
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // 토글 상태 동기
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mToggle.onConfigurationChanged(newConfig);
    }

    // Navigation Drawer 선택시 이벤트 발생
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_item_1:
                Toast.makeText(getApplicationContext(), "menu 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_item_2:
                Toast.makeText(getApplicationContext(), "menu 2", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    // SearchView 에 검색어 입력 완료시 호출
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // SearchView 에 검색어 입력시 호출
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
