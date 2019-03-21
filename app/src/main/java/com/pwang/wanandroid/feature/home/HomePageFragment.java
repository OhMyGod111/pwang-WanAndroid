package com.pwang.wanandroid.feature.home;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.base.BaseRecyclerViewAdapter;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.util.Utils;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

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
public class HomePageFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticleAdapter mAdapter;
    private com.youth.banner.Banner mBanner;

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
        ArrayList<String> imgUrls = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        imgUrls.clear();
        titles.clear();
        for (Banner banner : banners) {
            imgUrls.add(banner.getImagePath());
            titles.add(banner.getTitle());
        }
        mBanner.setBannerTitles(titles).setImages(imgUrls).start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
            mBanner = null;
        }
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        // 设置旋转进度的高度
        mSwipeRefreshLayout.setProgressViewEndTarget(true, Utils.dip2px(getResources()
                .getDimension(R.dimen.dp50)));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        mBanner = new com.youth.banner.Banner(mRecyclerView.getContext());
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE) // banner 样式
                .setBannerAnimation(Transformer.DepthPage)// 动画效果
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER) //设置指示器位置，当有banner中有指示器时
                .setOnBannerListener(onBannerListener)
                .setDelayTime(3 * 1000) // 设置轮播时间
                .isAutoPlay(true); // 设置自动轮播
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                Utils.dip2px(getResources().getDimension(R.dimen.dp100)));
        mBanner.setPadding(0,
                Utils.dip2px(getResources().getDimension(R.dimen.dp10)),
                0,
                Utils.dip2px(getResources().getDimension(R.dimen.dp10)));
        mBanner.setLayoutParams(layoutParams);
        mAdapter = new ArticleAdapter(R.layout.home_page__recycle_item);
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
//        mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.loadHomePageData(false);
    };

    OnBannerListener onBannerListener = position -> {

    };

    @Override
    protected int getLayoutId() {
        return R.layout.home_page_fragment;
    }

    static class ArticleAdapter extends BaseRecyclerViewAdapter<ArticleDetail> {

        public ArticleAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(VH holder, ArticleDetail item) {
            switch (holder.getItemViewType()) {
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

    /**
     * Banner 图显示专用 ImageLoader
     */
    private static class GlideImageLoader extends ImageLoader {
        private static final long serialVersionUID = 4455767717749086694L;

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
