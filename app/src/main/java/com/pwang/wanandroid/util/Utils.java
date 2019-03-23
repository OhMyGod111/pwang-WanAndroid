package com.pwang.wanandroid.util;

import android.content.Context;

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
     * 必须优先调用此初始化方法，才能使用相关的工具方法
     *
     * @param context
     */
    public static void init(Context context) {
        sInstance = Utils.requireNonNull(context, "context cannot be null");
    }

    /**
     * 获取全局的Context
     *
     * @return 全局的Context
     */
    public static Context getAppContext() {
        return Utils.requireNonNull(sInstance, "init method must be invoked first");
    }

    /*** 根据手机的分辨率从 dp 的单位 转成为 px(像素)*/
    public static int dip2px(float dpValue) {
        final float scale = getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors with multiple parameters, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Utils.requireNonNull(bar, "bar must not be null");
     *     this.baz = Utils.requireNonNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

}
