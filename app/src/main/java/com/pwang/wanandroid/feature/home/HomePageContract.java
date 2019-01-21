package com.pwang.wanandroid.feature.home;

import com.pwang.wanandroid.base.BasePresenter;
import com.pwang.wanandroid.base.BaseView;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;

import java.util.List;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface HomePageContract {

    interface View extends BaseView<Presenter> {
        void showLoading();
        void hideLoading();
        void showHintMsg(String msg);
        void showArticles(List<ArticleDetail> details);
    }

    interface Presenter extends BasePresenter<View>{
    }
}
