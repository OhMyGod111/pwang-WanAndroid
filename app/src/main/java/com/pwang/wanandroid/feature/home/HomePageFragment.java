package com.pwang.wanandroid.feature.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
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
public class HomePageFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View{

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
    public void showArticles(List<ArticleDetail> details) {
        mAdapter.addData(details);
    }

    @Override
    public void showBanners(List<Banner> banners) {
        if (banners == null || banners.isEmpty()) return;
        ArrayList<String> imgUrls = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        imgUrls.clear();
        titles.clear();
        for (Banner banner : banners) {
            imgUrls.add(banner.getImagePath());
            titles.add(banner.getTitle());
        }
        mBanner.setBannerTitles(titles).setImages(imgUrls).start();
        mBanner.setTag(banners);
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
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.home_recycle_item_tv_color));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        // 设置旋转进度的高度
        mSwipeRefreshLayout.setProgressViewEndTarget(true, Utils.dip2px(getResources()
                .getDimension(R.dimen.dp50)));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        mAdapter = new ArticleAdapter(R.layout.home_page__recycle_item);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mAdapter.setOnLoadMoreListener(onLoadMoreListener);

        @SuppressLint("InflateParams") LinearLayout linearLayout  = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.home_page_banner, null);
        this.mBanner = linearLayout.findViewById(R.id.banner);
        linearLayout.removeView(this.mBanner);
        mAdapter.addHeaderView(this.mBanner);

        this.mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE) // banner 样式
                .setBannerAnimation(Transformer.DepthPage)// 动画效果
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER) //设置指示器位置，当有banner中有指示器时
                .setOnBannerListener(onBannerListener)
                .setDelayTime(3 * 1000) // 设置轮播时间
                .isAutoPlay(true); // 设置自动轮播
    }

    BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener = (adapter, view, position) -> {
        Logger.d("adapter:" + adapter.getClass().getName() +
                "view:" + view.getClass().getName() +
                "position:" + position);
    };

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
//        mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.loadHomePageData(false);
    };

    OnBannerListener onBannerListener = position -> {
        Banner banner = ((List<Banner>) mBanner.getTag()).get(position);
        showPromptMessage("Banner:" + banner.getTitle());
    };

    BaseRecyclerViewAdapter.OnLoadMoreListener onLoadMoreListener = () -> {
        mPresenter.loadHomePageData(true);
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
//            Logger.d("Position:" + holder.getAdapterPosition()
//                    + " ### ItemViewType:" + holder.getItemViewType()
//                    + " ### ItemTitle:" + item.getTitle()
//                    + " ### ArticleDetail: " + item);
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
                    holder.getView(R.id.iv_collect).setOnClickListener(v -> {
                        Logger.d("v:" + v.getClass().getName());
                        if (v.isSelected()) v.setSelected(false);
                        else v.setSelected(true);
                    });
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
