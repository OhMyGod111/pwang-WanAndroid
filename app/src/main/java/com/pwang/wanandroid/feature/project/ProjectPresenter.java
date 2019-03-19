package com.pwang.wanandroid.feature.project;

import com.pwang.wanandroid.base.AbstractPresenter;
import com.pwang.wanandroid.di.scoped.ActivityScoped;
import com.pwang.wanandroid.di.scoped.FragmentScoped;

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
public final class ProjectPresenter extends AbstractPresenter<ProjectContract.View> implements
        ProjectContract.Presenter {

    @Inject
    public ProjectPresenter() {
    }
}
