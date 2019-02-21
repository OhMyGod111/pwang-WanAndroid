package com.pwang.wanandroid.feature.home;

import com.pwang.wanandroid.base.BasePresenter;
import com.pwang.wanandroid.base.BaseView;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.Banner;

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

        /**
         *  加载指示器
         * @param active true 显示，else 隐藏
         */
        void setLoadingIndicator(boolean active);

        /**
         *  显示提示信息
         * @param msg
         */
        void showHintMsg(String msg);

        /**
         *  显示首页文章列表
         * @param details
         */
        void showArticles(List<ArticleDetail> details);

        /**
         *  显示首页Banner图
         * @param banners
         */
        void showBanners(List<Banner> banners);
    }

    interface Presenter extends BasePresenter<View>{

        /**
         *  请求加载数据
         * @param forceUpdate true 加载更多 else 直接加载
         */
        void loadHomePageData(boolean forceUpdate);
    }
}
