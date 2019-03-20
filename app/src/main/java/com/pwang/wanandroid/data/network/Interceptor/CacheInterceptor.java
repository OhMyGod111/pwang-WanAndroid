package com.pwang.wanandroid.data.network.Interceptor;

import android.support.annotation.NonNull;

import com.pwang.wanandroid.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CacheInterceptor implements Interceptor {

    // 没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
    private static final int MAX_AGE = 0;
    // 没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败
    private static final int MAX_STALE = 60 * 60 * 24 * 7 * 4;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isNetConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtils.isNetConnected()) {
            // 有网络时, 不缓存, 最大保存时长为0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + MAX_AGE)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为4周
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
