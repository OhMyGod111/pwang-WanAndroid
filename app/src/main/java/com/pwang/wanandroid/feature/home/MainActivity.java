package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.pwang.wanandroid.feature.knowledge.KnowledgeFragment;
import com.pwang.wanandroid.feature.navigation.NavigationFragment;
import com.pwang.wanandroid.feature.project.ProjectFragment;
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
    @Inject
    NavigationFragment mNavigationFragment;
    @Inject
    KnowledgeFragment mKnowledgeFragment;
    @Inject
    ProjectFragment mProjectFragment;

    private static final int DEFAULT_HOME_PAGE_FRAGMENT = R.id.tab_home_page;

    // 用户期望看见的Fragment
    private Fragment mTargetFragment;
    // 如果存在 即是当前显示的Fragment
    private Fragment mCurrentFragment;


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
            bottomNav.setSelectedItemId(DEFAULT_HOME_PAGE_FRAGMENT);
        }
        setupToolbar();
    }

    private void switchFragment(int itemId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (itemId) {
            case R.id.tab_home_page:
                HomePageFragment homePageFragment = (HomePageFragment) supportFragmentManager.findFragmentByTag(HomePageFragment.class.getName());
                if (homePageFragment == null) {
                    homePageFragment = mHomePageFragment;
                    fragmentTransaction.add(R.id.fl_container, homePageFragment, HomePageFragment.class.getName());
                }
                break;
            case R.id.tab_knowledge_hierarchy:
                KnowledgeFragment knowledgeFragment = (KnowledgeFragment) supportFragmentManager.findFragmentByTag(KnowledgeFragment.class.getName());
                if (knowledgeFragment == null) {
                    knowledgeFragment = mKnowledgeFragment;
                    fragmentTransaction.add(R.id.fl_container, knowledgeFragment, KnowledgeFragment.class.getName());
                }
                break;
            case R.id.tab_navigation:
                NavigationFragment navigationFragment = (NavigationFragment) supportFragmentManager.findFragmentByTag(NavigationFragment.class.getName());
                if (navigationFragment == null) {
                    navigationFragment = mNavigationFragment;
                    fragmentTransaction.add(R.id.fl_container, navigationFragment, NavigationFragment.class.getName());
                }
                break;
            case R.id.tab_project:
                ProjectFragment projectFragment = (ProjectFragment) supportFragmentManager.findFragmentByTag(ProjectFragment.class.getName());
                if (projectFragment == null) {
                    projectFragment = mProjectFragment;
                    fragmentTransaction.add(R.id.fl_container, projectFragment, ProjectFragment.class.getName());
                }
                break;
        }

        if (mCurrentFragment != null && mCurrentFragment != mTargetFragment) {
            fragmentTransaction.hide(mCurrentFragment).show(mTargetFragment).commit();
        } else {
            fragmentTransaction.show(mTargetFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_item, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView view = (SearchView) item.getActionView();
        view.setQueryHint(getString(R.string.main_toolbar_search));
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NewApi")
    private void setupToolbar() {
        View titleView = toolbar.getChildAt(0);
        if (Objects.nonNull(titleView) && titleView instanceof TextView) {
            ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            ((TextView) titleView).setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    private void setupBottomDrawerContent(BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.tab_home_page:
                    mTargetFragment = mHomePageFragment;
                    switchFragment(menuItem.getItemId());
                    mCurrentFragment = mHomePageFragment;
                    break;
                case R.id.tab_knowledge_hierarchy:
                    mTargetFragment = mKnowledgeFragment;
                    switchFragment(menuItem.getItemId());
                    mCurrentFragment = mKnowledgeFragment;
                    break;
                case R.id.tab_navigation:
                    mTargetFragment = mNavigationFragment;
                    switchFragment(menuItem.getItemId());
                    mCurrentFragment = mNavigationFragment;
                    break;
                case R.id.tab_project:
                    mTargetFragment = mProjectFragment;
                    switchFragment(menuItem.getItemId());
                    mCurrentFragment = mProjectFragment;
                    break;
            }
            toolbar.setTitle(menuItem.getItemId() == R.id.tab_home_page ?
                    getResources().getString(R.string.app_name) : menuItem.getTitle());
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
