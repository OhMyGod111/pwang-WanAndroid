package com.pwang.wanandroid;

import android.os.StrictMode;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.pwang.wanandroid.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WanApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            setStrictMode();
        }
        super.onCreate();

        init();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).builder();
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void init() {
        initLogger();
    }

    private void initLogger() {
        // debug 版本打印到控制台
        if (BuildConfig.DEBUG){
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                    tag(getString(R.string.app_name)).build()));
        }

        // release 版本日志保存到文件
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
