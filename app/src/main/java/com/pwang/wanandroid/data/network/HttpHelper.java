package com.pwang.wanandroid.data.network;

import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface HttpHelper {

    Observable<BaseResponse<ArticleList>> getHomePageArticleList(int page);

    Observable<BaseResponse<Banner>> getHomePageBanner();
}
