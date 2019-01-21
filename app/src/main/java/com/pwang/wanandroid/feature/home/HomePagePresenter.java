package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.data.DataManager;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.BaseResponse;
import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.util.RxUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

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

        Observable<BaseResponse<ArticleList>> homePageArticleList = dataManager.getHomePageArticleList(0);
        homePageArticleList
                .compose(RxUtils.schedulersTransformer())
                .subscribe(articleListBaseResponse -> {
                    List<ArticleDetail> datas = articleListBaseResponse.getData().getDatas();
                    for (ArticleDetail data : datas) {
                        Logger.d("Title:" + data.getTitle());
                    }
                });

    }

    @Override
    public void dropView() {
        Logger.d("dropView");
    }
}
