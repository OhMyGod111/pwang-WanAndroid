package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.data.DataManager;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;
import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.util.RxUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

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
public class HomePagePresenter implements HomePageContract.Presenter {

    private HomePageContract.View mView;
    private DataManager dataManager;
    private CompositeDisposable mCompositeDisposable;

    private int mCurrentPage;
    private boolean mFirstLoad = true;

    @Inject
    HomePagePresenter(DataManager dataManager) {
        Objects.requireNonNull(dataManager,"dataManager cannot be null");
        this.dataManager = dataManager;
    }

    @SuppressLint("CheckResult")
    @Override
    public void takeView(HomePageContract.View view) {
        this.mView = view;
        Logger.d("takeView");
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
        Observable<BaseResponse<Banner>> homePageBanner = dataManager.getHomePageBanner();
        Observable<BaseResponse<ArticleList>> homePageArticleList = dataManager.getHomePageArticleList(mCurrentPage);
        homePageArticleList
                .compose(RxUtils.schedulersTransformer())
                .subscribe(articleListBaseResponse -> {
                    List<ArticleDetail> datas = articleListBaseResponse.getData().getDatas();
                    for (ArticleDetail data : datas) {
                        Logger.d("Title:" + data.getTitle());
                    }
                });
    }
}
