package com.pwang.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.DaggerActivity;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * <pre>
 *     author : WangPan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initToolBar(){

    }
}
