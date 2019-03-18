package com.pwang.wanandroid.di.component;

import com.pwang.wanandroid.base.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * <pre>
 *    @author : WangPan
 *    @e-mail : 501098462@qq.con
 *    @time   : 2019/3/17
 *    @desc   :
 *    @version: 1.0
 * </pre>
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {}
}
