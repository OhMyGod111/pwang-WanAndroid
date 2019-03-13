package com.pwang.wanandroid.feature.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@ActivityScoped
public class HomePageFragment extends BaseFragment <HomePagePresenter> implements HomePageContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    public HomePageFragment() {
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showHintMsg(String msg) {

    }

    @Override
    public void showArticles(List<ArticleDetail> details) {
        for (ArticleDetail detail : details) {
            Logger.d("ArticleTitle:" + detail.getTitle());
        }
    }

    @Override
    public void showBanners(List<Banner> banners) {
        for (Banner banner : banners) {
            Logger.d("Banner", ":" + banner.getTitle());
        }
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_page_fragment;
    }
}
