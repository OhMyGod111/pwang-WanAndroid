package com.pwang.wanandroid.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class RxUtils {
    private RxUtils() {
    }

    /**
     *  统一处理线程调度
     * @param <T>
     * @return
     */
    public static  <T> ObservableTransformer<T, T> schedulersTransformer() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
