package com.pwang.wanandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) Utils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null;
        }
        return false;
    }
}
