package com.pwang.wanandroid.di.module;

import com.pwang.wanandroid.data.DataManager;
import com.pwang.wanandroid.data.network.ApiService;
import com.pwang.wanandroid.data.network.HttpHelper;
import com.pwang.wanandroid.data.network.HttpHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public class DataManagerModule {

    @Singleton
    @Provides
    DataManager provideDataManager(HttpHelper httpHelper) {
        return new DataManager(httpHelper);
    }

    @Singleton
    @Provides
    HttpHelper provideHttpHelper(ApiService apiService) {
        return new HttpHelperImpl(apiService);
    }
}
