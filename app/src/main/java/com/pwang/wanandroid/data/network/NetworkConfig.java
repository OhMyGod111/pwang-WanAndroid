package com.pwang.wanandroid.data.network;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class NetworkConfig {

    private NetworkConfig() {
    }

    public static String HOST = "http://www.wanandroid.com";


    public static final long CONNECT_TIMEOUT= 10L;

    public static final long READ_TIMEOUT= 20L;

    public static final long WRITE_TIMEOUT= 20L;

    public static final String CACHE_FILE = "NetCache";

    public static final int CACHE_SIZE = 1024 * 1024 * 50;
}
