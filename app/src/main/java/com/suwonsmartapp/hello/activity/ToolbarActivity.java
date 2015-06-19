package com.suwonsmartapp.hello.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.activity.event.ButtonClickEvent;
import com.suwonsmartapp.hello.activity.fragment.AppbarLayoutFragment;
import com.suwonsmartapp.hello.activity.fragment.CollapsingToolbarLayoutFragment;
import com.suwonsmartapp.hello.activity.fragment.DragAndDropSampleFragment;
import com.suwonsmartapp.hello.activity.fragment.FloatingActionButtonExamFragment;
import com.suwonsmartapp.hello.activity.fragment.MultiChoiceListFragment;
import com.suwonsmartapp.hello.activity.fragment.PullToRefreshFragment;
import com.suwonsmartapp.hello.activity.fragment.TabLayoutFragment;
import com.suwonsmartapp.hello.activity.fragment.TextInputLayoutFragment;
import com.suwonsmartapp.hello.activity.fragment.TransitionFromFragment;
import com.suwonsmartapp.hello.challenge.challenge17.MovieListFragment;
import com.suwonsmartapp.hello.challenge.challenge17.MyEvent;
import com.suwonsmartapp.hello.loader.ContactFragment;

import de.greenrobot.event.EventBus;

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
    private static final String TAG = ToolbarActivity.class.getSimpleName();
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
        // Transition 기능을 사용
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet set = new TransitionSet();
            set.addTransition(new ChangeImageTransform());
            getWindow().setExitTransition(set);
            getWindow().setEnterTransition(set);
        }

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

        if (savedInstanceState == null) {   // 화면 회전시 또 호출되는 것 방지
            // 연락처 화면 표시
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ContactFragment()).commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
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
            case R.id.navigation_item_4:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MovieListFragment()).commit();
                break;
            case R.id.navigation_item_5:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FloatingActionButtonExamFragment()).commit();
                break;
            case R.id.navigation_item_6:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TabLayoutFragment()).commit();
                break;
            case R.id.navigation_item_7:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AppbarLayoutFragment()).commit();
                break;
            case R.id.navigation_item_8:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CollapsingToolbarLayoutFragment()).commit();
                break;
            case R.id.navigation_item_9:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TransitionFromFragment()).commit();
                break;
            case R.id.navigation_item_10:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MultiChoiceListFragment()).commit();
                break;
            case R.id.navigation_item_11:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TextInputLayoutFragment()).commit();
                break;

        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    public void onEvent(MyEvent event) {
//        Log.d(TAG, "event : " + event.toString());
        if (event instanceof ButtonClickEvent) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else {
            Toast.makeText(getApplicationContext(), "커스텀 이벤트다", Toast.LENGTH_SHORT).show();
        }

    }


    public void onEvent(Integer number) {
        Log.d(TAG, "event : " + number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(View v) {

        // Fragment 는 Transition 이 잘 안 됨;;;
//        TransitionSet set = new TransitionSet();
//        set.addTransition(new ChangeImageTransform());
//        set.addTransition(new Fade());
//
//        Fragment frag = new TransitionToFragment();
//        frag.setEnterTransition(set);
//        frag.setSharedElementEnterTransition(set);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, frag)
//                .addToBackStack(null)
//                .addSharedElement(v, "share")
//                .commit();

        // Activity Transition 은 롤리팝 전용
        Intent intent = new Intent(this, NextActivity.class);
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, "share");
        ActivityCompat.startActivity(this, intent, option.toBundle());

    }
}
