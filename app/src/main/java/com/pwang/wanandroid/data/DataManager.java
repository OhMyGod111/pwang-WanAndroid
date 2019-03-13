package com.pwang.wanandroid.data;

import com.pwang.wanandroid.data.network.HttpHelper;
import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Singleton
public class DataManager implements HttpHelper {

    private HttpHelper mHttpHelper;

    @Inject
    public DataManager(HttpHelper httpHelper) {
        Objects.requireNonNull(httpHelper,"httpHelper cannot be null ");
        this.mHttpHelper = httpHelper;
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getHomePageArticleList(int page) {
        return mHttpHelper.getHomePageArticleList(page);
    }

    @Override
    public Observable<BaseResponse<List<Banner>>> getHomePageBanner() {
        return mHttpHelper.getHomePageBanner();
    }
}
