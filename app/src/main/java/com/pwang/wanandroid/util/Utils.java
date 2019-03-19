package com.pwang.wanandroid.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.Objects;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/28
 *     desc   : 统一Util配置
 *     version: 1.0
 * </pre>
 */
public final class Utils {

    private static volatile Context sInstance;

    private Utils() {
    }

    /**
     *  必须优先调用此初始化方法，才能使用相关的工具方法
     * @param context
     */
    public static void init(Context context){
        sInstance = Objects.requireNonNull(context, "context cannot be null");
    }

    /**
     *  获取全局的Context
     * @return 全局的Context
     */
    public static Context getAppContext() {
        return Objects.requireNonNull(sInstance, "init method must be invoked first");
    }
}
