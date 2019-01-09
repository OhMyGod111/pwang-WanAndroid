package com.pwang.wanandroid.di.module;

import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.feature.WelcomeActivity;
import com.pwang.wanandroid.feature.home.HomePageModule;
import com.pwang.wanandroid.feature.home.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = HomePageModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WelcomeActivity welcomeActivity();
}
