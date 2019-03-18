package com.pwang.wanandroid.feature.navigation;

import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

import javax.inject.Inject;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/03/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
//@ActivityScoped
public class NavigationFragment extends BaseFragment {

    @Inject
    public NavigationFragment() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
