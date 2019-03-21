package com.pwang.wanandroid.data.network.cookie;

import android.support.annotation.NonNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * <pre>
 *    @author :WangPan
 *    @e-mail :501098462@qq.con
 *    @time   :2019/03/21
 *    @desc   :
 *    @version:1.0
 * </pre>
 */
public final class CookieManager implements CookieJar {
    private static final PersistentCookieStore COOKIE_STORE = new PersistentCookieStore();

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                COOKIE_STORE.add(url, item);
            }
        }
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return COOKIE_STORE.get(url);
    }

    /**
     * 清除所有cookie
     */
    public static void clearAllCookies() {
        COOKIE_STORE.removeAll();
    }

    /**
     * 清除指定cookie
     *
     * @param url HttpUrl
     * @param cookie Cookie
     * @return if clear cookies
     */
    public static boolean clearCookies(HttpUrl url, Cookie cookie) {
        return COOKIE_STORE.remove(url, cookie);
    }

    /**
     * 获取cookies
     *
     * @return List<Cookie>
     */
    public static List<Cookie> getCookies() {
        return COOKIE_STORE.getCookies();
    }
}
