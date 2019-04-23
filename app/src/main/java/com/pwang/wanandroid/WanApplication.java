package com.pwang.wanandroid;

import android.content.ComponentCallbacks2;
import android.os.StrictMode;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.pwang.wanandroid.di.component.DaggerAppComponent;
import com.pwang.wanandroid.util.Utils;

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
//            setStrictMode();
        }
        super.onCreate();

        // 初始化操作
        init();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).builder();
    }

    private void setStrictMode() {
        // thread 策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
//                .penaltyDialog()
                .build());
        // VM 策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void init() {
        initLogger();
        // 工具类初始化操作
        Utils.init(WanApplication.this);
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
