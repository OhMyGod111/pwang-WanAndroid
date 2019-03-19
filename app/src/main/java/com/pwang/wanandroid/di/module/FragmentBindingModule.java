package com.pwang.wanandroid.di.module;

import com.pwang.wanandroid.di.scoped.FragmentScoped;
import com.pwang.wanandroid.feature.home.HomePageFragment;
import com.pwang.wanandroid.feature.home.HomePageModule;
import com.pwang.wanandroid.feature.knowledge.KnowledgeFragment;
import com.pwang.wanandroid.feature.navigation.NavigationFragment;
import com.pwang.wanandroid.feature.project.ProjectFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * <pre>
 *     author : WangPan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = HomePageModule.class)
    abstract HomePageFragment homePageFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract KnowledgeFragment knowledgeFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NavigationFragment navigationFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProjectFragment projectFragment();
}
