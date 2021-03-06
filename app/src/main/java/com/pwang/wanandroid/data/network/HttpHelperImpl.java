package com.pwang.wanandroid.data.network;

import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;
import com.pwang.wanandroid.util.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class HttpHelperImpl implements HttpHelper {

    private ApiService apiService;

    @Inject
    public HttpHelperImpl(ApiService apiService) {
        Utils.requireNonNull(apiService, "apiService cannot be null");
        this.apiService = apiService;
    }

    @Override
    public Observable<BaseResponse<ArticleList>> getHomePageArticleList(int page) {
        return apiService.getHomePageArticleList(page);
    }

    @Override
    public Observable<BaseResponse<List<Banner>>> getHomePageBanner() {
        return apiService.getHomePageBanner();
    }
}
