package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.base.AbstractPresenter;
import com.pwang.wanandroid.common.BaseObserver;
import com.pwang.wanandroid.data.DataManager;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;
import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.util.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@ActivityScoped
public class HomePagePresenter extends AbstractPresenter<HomePageContract.View> implements HomePageContract.Presenter {

    private DataManager dataManager;

    private int mCurrentPage;
    private boolean mFirstLoad = true;

    public static final String KEY_BANNER_DATA = "banner_data";
    public static final String KEY_ARTICLE_DATA = "article_data";

    @Inject
    HomePagePresenter(DataManager dataManager) {
        Objects.requireNonNull(dataManager,"dataManager cannot be null");
        this.dataManager = dataManager;
    }

    @SuppressLint("CheckResult")
    @Override
    public void takeView(HomePageContract.View view) {
        super.takeView(view);
        Logger.d("takeView");

        loadHomePageData(false);
    }

    @Override
    public void dropView() {
        Logger.d("dropView");
    }

    @Override
    public void loadHomePageData(boolean forceUpdate) {
        loadHomePageData(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadHomePageData(boolean forceUpdate, boolean showLoadingUI) {
        if (showLoadingUI) {
            if (mView != null) {
                mView.setLoadingIndicator(true);
            }
        }

        // 强制更新，用户手动操作的
        if (forceUpdate){
            mCurrentPage ++;
        }

        // 处理数据
        Observable<BaseResponse<List<Banner>>> homePageBanner = dataManager.getHomePageBanner();
        Observable<BaseResponse<ArticleList>> homePageArticleList = dataManager.getHomePageArticleList(mCurrentPage);

        addDisposable(Observable.zip(homePageBanner, homePageArticleList, this::createResponseMap)
                .compose(RxUtils.schedulersTransformer()).subscribeWith(new BaseObserver<Map<String, Object>>() {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        BaseResponse<List<Banner>> bannerBaseResponse = (BaseResponse<List<Banner>>) map.get(KEY_BANNER_DATA);
                        BaseResponse<ArticleList> articleListBaseResponse = (BaseResponse<ArticleList>) map.get(KEY_ARTICLE_DATA);
                        assert bannerBaseResponse != null;
                        assert articleListBaseResponse != null;
                        List<Banner> banners = bannerBaseResponse.getData();
                        List<ArticleDetail> details = articleListBaseResponse.getData().getDatas();
                        if (!banners.isEmpty() && !details.isEmpty()) {
                            mView.setLoadingIndicator(false);
                            mView.showBanners(banners);
                            mView.showArticles(details);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @NonNull
    private Map<String, Object> createResponseMap(BaseResponse<List<Banner>> banner, BaseResponse<ArticleList> article){
        HashMap<String, Object> map = new HashMap<>();
        map.put(KEY_BANNER_DATA, banner);
        map.put(KEY_ARTICLE_DATA, article);
        return map;
    }
}
