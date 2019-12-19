package com.pwang.wanandroid.feature.browser;

import com.pwang.wanandroid.base.AbstractPresenter;

import javax.inject.Inject;

/**
 * <pre>
 *    @author :WangPan
 *    @e-mail :501098462@qq.con
 *    @time   :2019/05/05
 *    @desc   :
 *    @version:1.0
 * </pre>
 */
public class WebPresenter extends AbstractPresenter<WebContract.View> implements WebContract.Presenter{

    @Inject
    public WebPresenter() {
    }

    @Override
    public void takeView(WebContract.View view) {
        super.takeView(view);
    }

    @Override
    public void dropView() {
        super.dropView();
    }
}
