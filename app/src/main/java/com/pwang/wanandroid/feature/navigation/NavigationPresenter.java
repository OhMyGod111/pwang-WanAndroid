package com.pwang.wanandroid.feature.navigation;

import com.pwang.wanandroid.base.AbstractPresenter;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

import javax.inject.Inject;

/**
 * <pre>
 *    @author : WangPan
 *    @e-mail : 501098462@qq.con
 *    @time   : 2019/3/17
 *    @desc   :
 *    @version: 1.0
 * </pre>
 */
public final class NavigationPresenter extends AbstractPresenter<NavigationContract.View> implements
        NavigationContract.Presenter {

    @Inject
    public NavigationPresenter() {
    }
}
