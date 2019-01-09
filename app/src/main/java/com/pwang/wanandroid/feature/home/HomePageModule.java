package com.pwang.wanandroid.feature.home;

import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.di.scoped.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public abstract class HomePageModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomePageFragment homePageFragment();

    @ActivityScoped
    @Binds
    abstract HomePageContract.Presenter homePagePresenter(HomePagePresenter presenter);
}
