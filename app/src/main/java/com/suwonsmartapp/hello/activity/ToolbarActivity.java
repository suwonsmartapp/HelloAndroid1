package com.suwonsmartapp.hello.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.loader.ContactFragment;

/**
 * Toolbar 사용
 *
 * http://googledevkr.blogspot.kr/2014/10/appcompat-v21-material-design-for-pre.html?m=1
 *
 * 1. xml 에 Toolbar를 추가
 * 2. Theme.AppCompat.NoActionBar 테마를 사용
 * 3. onCreate 에서 setSupportActionBar() 에 Toolbar 설정
 */
public class ToolbarActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    // Toolbar
    private Toolbar mToolbar;

    // Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

//    // SearchView
//    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // 연락처 화면 표시
        getSupportFragmentManager().beginTransaction().add(R.id.container, new ContactFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        // SearchView
//        mSearchView = (SearchView) mToolbar.getMenu().findItem(R.id.menu_search).getActionView();
//        mSearchView.setOnQueryTextListener(mContactFragment);

        // SearchView를 열린 상태로 한다
//        mSearchView.setIconified(true);

        // 쿼리 힌트 표시
//        mSearchView.setQueryHint("쿼리 입력");

        // 포커스 해제 (소프트 키보드를 닫을 때)
//        mSearchView.clearFocus();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ContactFragment()).commit();
                break;
            case R.id.navigation_item_2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new DragAndDropSampleFragment()).commit();
                break;
            case R.id.navigation_item_3:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PullToRefreshFragment()).commit();
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

}
