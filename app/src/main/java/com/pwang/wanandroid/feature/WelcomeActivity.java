package com.pwang.wanandroid.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.pwang.wanandroid.base.BaseActivity;
import com.pwang.wanandroid.feature.home.MainActivity;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gotoMainPage();
    }

    private void gotoMainPage(){
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        ActivityCompat.startActivity(WelcomeActivity.this, intent, null);
        finish();
    }
}
