package com.pwang.wanandroid.feature.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

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
public class HomePageFragment extends BaseFragment <HomePageContract.Presenter> implements HomePageContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    HomePagePresenter mPresenter;

    @Inject
    public HomePageFragment() {
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showHintMsg(String msg) {

    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_page_fragment;
    }
}
