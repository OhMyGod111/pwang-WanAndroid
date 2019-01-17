package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseActivity;
import com.pwang.wanandroid.util.ActivityUtils;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNav;
    @BindView(R.id.nav_menu)
    NavigationView navMenu;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Inject
    HomePageFragment mHomePageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
                toolbar, R.string.nav_open, R.string.nav_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //滑动过程中不断回调
                super.onDrawerSlide(drawerView, slideOffset);
                View content = drawerLayout.getChildAt(0);
                content.setTranslationX(drawerView.getMeasuredWidth() * slideOffset);//slideOffset:0~1
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (navMenu != null) {
            setupDrawerContent(navMenu);
        }

        if (bottomNav != null) {
            setupBottomDrawerContent(bottomNav);
        }

        HomePageFragment homePageFragment = (HomePageFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);

        if (homePageFragment == null) {
            homePageFragment = mHomePageFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homePageFragment, R.id.fl_container);
        }

        setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_item, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView view = (SearchView) item.getActionView();
        view.setQueryHint("搜索更多内容");
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NewApi")
    private void setupToolbar() {
        View titleView = toolbar.getChildAt(0);
        if (Objects.nonNull(titleView) && titleView instanceof TextView){
            ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            ((TextView) titleView).setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    private void setupBottomDrawerContent(BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.tab_home_page:
                    Log.e("TAG", "tab_home_page:");
                    break;
                case R.id.tab_knowledge_hierarchy:
                    Log.e("TAG", "tab_knowledge_hierarchy:");
                    break;
                case R.id.tab_navigation:
                    Log.e("TAG", "tab_navigation:");
                    break;
                case R.id.tab_project:
                    Log.e("TAG", "tab_project:");
                    break;
            }
            return true;
        });
    }

    private void setupDrawerContent(NavigationView navMenu) {
        navMenu.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_favorite:
                    Log.e("TAG", "nav_favorite:");
                    break;
                case R.id.nav_night_mode:
                    Log.e("TAG", "nav_night_mode:");
                    break;
                case R.id.nav_settings:
                    Log.e("TAG", "nav_settings:");
                    break;
                case R.id.nav_about_me:
                    Log.e("TAG", "nav_about_me:");
                    break;
                case R.id.nav_log_out:
                    Log.e("TAG", "nav_log_out:");
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }
}
