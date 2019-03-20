package com.pwang.wanandroid.feature.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.base.BaseRecyclerViewAdapter;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

import java.util.ArrayList;
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
public class HomePageFragment extends BaseFragment <HomePagePresenter> implements HomePageContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticleAdapter mAdapter;

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
        mAdapter.setData(details);
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
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        mAdapter = new ArticleAdapter(R.layout.home_page__recycle_item);
        mRecyclerView.setAdapter(mAdapter);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
        Logger.d("onRefresh");
    };

    @Override
    protected int getLayoutId() {
        return R.layout.home_page_fragment;
    }

    static class ArticleAdapter extends BaseRecyclerViewAdapter<ArticleDetail>{

        public ArticleAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(VH holder, ArticleDetail item) {
            switch (holder.getItemViewType()){
                case 0:
                    break;
                case 1:
                    break;
                case 3:
                    break;
                default:
                    holder.setText(R.id.tv_source, item.getSuperChapterName());
                    holder.setText(R.id.tv_author, item.getAuthor());
                    holder.setText(R.id.tv_date, item.getNiceDate());
                    holder.setText(R.id.tv_title, item.getTitle());
                    holder.setText(R.id.tv_label, item.getSuperChapterName());
                    break;
            }
        }
    }
}
