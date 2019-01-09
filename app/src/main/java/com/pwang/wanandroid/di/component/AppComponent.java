package com.pwang.wanandroid.di.component;

import com.pwang.wanandroid.WanApplication;
import com.pwang.wanandroid.di.module.ActivityBindingModule;
import com.pwang.wanandroid.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Singleton
@Component(modules = {ApplicationModule.class, ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<WanApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(WanApplication application);

        AppComponent builder();
    }
}
