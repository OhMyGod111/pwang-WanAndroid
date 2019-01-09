package com.pwang.wanandroid.feature.home;

import com.pwang.wanandroid.di.scoped.ActivityScoped;

import javax.inject.Inject;

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

    @Inject
    HomePagePresenter() {
    }

    @Override
    public void takeView(HomePageContract.View view) {
        this.mView = view;

    }

    @Override
    public void dropView() {

    }
}
