package com.pwang.wanandroid.di.module;

import android.app.Application;

import dagger.Binds;
import dagger.Module;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public abstract class ApplicationModule {

    @Binds
    abstract Application bindContext(Application application);
}
