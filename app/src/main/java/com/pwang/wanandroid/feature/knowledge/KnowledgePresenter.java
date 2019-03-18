package com.pwang.wanandroid.feature.knowledge;

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
public final class KnowledgePresenter extends AbstractPresenter<KnowledgeContract.View>implements
        KnowledgeContract.Presenter {

    @Inject
    public KnowledgePresenter() {
    }
}
