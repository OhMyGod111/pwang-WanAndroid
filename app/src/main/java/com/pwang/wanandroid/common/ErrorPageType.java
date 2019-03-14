package com.pwang.wanandroid.common;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/03/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@IntDef({
        ErrorPageType.NET_ERROR_TYPE,
        ErrorPageType.NO_DATA_TYPE
})

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface ErrorPageType {
    int NET_ERROR_TYPE = 0;
    int NO_DATA_TYPE = 1;
}
