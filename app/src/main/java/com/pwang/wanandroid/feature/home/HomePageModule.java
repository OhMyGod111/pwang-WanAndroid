package com.pwang.wanandroid.feature.home;

import com.pwang.wanandroid.di.module.FragmentBindingModule;
import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.di.scoped.FragmentScoped;
import com.pwang.wanandroid.feature.knowledge.KnowledgeContract;
import com.pwang.wanandroid.feature.knowledge.KnowledgeFragment;
import com.pwang.wanandroid.feature.knowledge.KnowledgePresenter;
import com.pwang.wanandroid.feature.navigation.NavigationContract;
import com.pwang.wanandroid.feature.navigation.NavigationFragment;
import com.pwang.wanandroid.feature.navigation.NavigationPresenter;
import com.pwang.wanandroid.feature.project.ProjectContract;
import com.pwang.wanandroid.feature.project.ProjectFragment;
import com.pwang.wanandroid.feature.project.ProjectPresenter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module()
public abstract class HomePageModule {

//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract HomePageFragment homePageFragment();

    @Binds
    abstract HomePageContract.Presenter homePagePresenter(HomePagePresenter presenter);


//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract KnowledgeFragment knowledgeFragment();

    @Binds
    abstract KnowledgeContract.Presenter knowledgePresenter(KnowledgePresenter presenter);


//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract NavigationFragment navigationFragment();

    @Binds
    abstract NavigationContract.Presenter navigationPresenter(NavigationPresenter presenter);


//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract ProjectFragment projectFragment();

    @Binds
    abstract ProjectContract.Presenter projectPresenter(ProjectPresenter presenter);
}
