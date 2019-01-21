package com.pwang.wanandroid.di.module;

import com.pwang.wanandroid.BuildConfig;
import com.pwang.wanandroid.data.network.ApiService;
import com.pwang.wanandroid.data.network.Interceptor.CacheInterceptor;
import com.pwang.wanandroid.data.network.Interceptor.HttpLoggingInterceptor;
import com.pwang.wanandroid.data.network.NetworkConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return createRetrofit(okHttpClient, NetworkConfig.HOST);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(logging);
            // 此拦截器可以通过Chrome 浏览器调试接口， 目前没有添加此框架依赖，感觉还不错
//            .addNetworkInterceptor(new StethoInterceptor());
        }

        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        builder.addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(cacheInterceptor)
//                .cache(null)
                .connectTimeout(NetworkConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .cookieJar(null)
                .retryOnConnectionFailure(true);  // 错误重连
        return builder.build();
    }


    private Retrofit createRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }


}
