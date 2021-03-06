package com.pwang.wanandroid.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.util.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/02/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.VH> {

    protected List<T> mData;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemChildClickListener mOnItemChildClickListener;

    private static final int HEADER_VIEW = 0x001111;
    private static final int FOOTER_VIEW = 0x001112;
    private static final int LOADING_VIEW = 0x001113;
    private static final int DEFAULT_VIEW_TYPE = 0x001114;
    private static final int EMPTY_VIEW_TYPE = 0x001115;
    private static final int TYPE_NOT_FOUND = -404;

    //region 头布局、脚布局
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    //endregion

    // 默认的下拉加载布局
    private AbstractLoadingView mLoadingView = new DefaultLoadingView();

    /**
     * 多种类型布局容器
     */
    private SparseIntArray mMultiLayouts = new SparseIntArray();


    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId) {
        this(null, layoutResId);
    }

    public BaseRecyclerViewAdapter(@Nullable List<T> data) {
        this(data, DEFAULT_VIEW_TYPE);
    }

    public BaseRecyclerViewAdapter(@Nullable List<T> mData, int mLayoutResId) {
        this.mData = mData == null ? new ArrayList<>() : mData;
        this.mMultiLayouts.put(DEFAULT_VIEW_TYPE, mLayoutResId);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mLayoutInflater == null)
            this.mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        VH vh;
        switch (viewType) {
            case HEADER_VIEW:
                view = mHeaderLayout;
                vh = new VH(view);
                break;
            case FOOTER_VIEW:
                view = mFooterLayout;
                vh = new VH(view);
                break;
            case LOADING_VIEW:
                vh = new VH(inflate(mLoadingView.getLoadingViewLayout(), viewGroup));
                break;
            case EMPTY_VIEW_TYPE:
                // TODO: 2019/3/24  EMPTY_VIEW 目前不做处理，也不添加自定义的 EMPTY_VIEW
                vh = new VH(new View(viewGroup.getContext()));
                break;
            default:
                view = inflate(getLayoutId(viewType), viewGroup);
                vh = new VH(view);
                if (mOnItemClickListener != null) {
                    view.setOnClickListener(v -> mOnItemClickListener.onItemClick(
                            BaseRecyclerViewAdapter.this,
                            view, vh.getLayoutPosition() - getHeaderLayoutCount()));

                    if (mOnItemLongClickListener != null) {
                        view.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(
                                BaseRecyclerViewAdapter.this,
                                view, vh.getLayoutPosition() - getHeaderLayoutCount()));
                    }
                }
                break;
        }
        vh.setAdapter(this);
        return vh;
    }

    private View inflate(@LayoutRes int resource, ViewGroup root) {
        return mLayoutInflater.inflate(resource, root, false);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {
        int viewType = vh.getItemViewType();
        switch (viewType) {
            case LOADING_VIEW:
//                if (position == mData.size() + 1)
//                    mLoadingView.setLoadStatus(AbstractLoadingView.LOADING);
//                else mLoadingView.setLoadStatus(AbstractLoadingView.LOADING_COMPLETE);
                mLoadingView.convert(vh);
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW_TYPE:
            case FOOTER_VIEW:
                break;
            default:
                convert(vh, getDataItemByPosition(position - getHeaderLayoutCount()));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.isEmpty()) return EMPTY_VIEW_TYPE;
        int headerCount = getHeaderLayoutCount();
        if (position < headerCount) return HEADER_VIEW;
        else {
            // range 处在数据区域之内 也就是mData.size()范围之内，超出这个范围就是footer的区域了或者 LoadingView区域
            int range = position - headerCount;
            if (range < mData.size()) return getMultiDataViewType(position);
            else {
//                Logger.d("before-range:" + range + " ### headerCount:" + headerCount
//                        + " ### position:" + position + " ### DataSize:" + mData.size());

                // 此时的 range 处在数据区域之外 range > mData.size()
                // 如果此时 range = range - mData.size() = 0 处在最后的位置
                range = range - mData.size();
                int footerCount = getFooterLayoutCount();

//                Logger.d("after-range:" + range + " ### footerCount: " + footerCount
//                        + " ### position: " + position);

                if (range < footerCount) return FOOTER_VIEW;
                else return LOADING_VIEW;
            }
        }
    }

    /**
     * 用于获取不同数据对应Item的布局类型, 如果存在多种布局类型请务必重写此方法
     *
     * @param position Item 对应的位置
     * @return 根据业务返回的布局类型
     */
    protected int getMultiDataViewType(int position) {
        return DEFAULT_VIEW_TYPE;
    }

    protected abstract void convert(VH holder, T item);

    /**
     * 通过position获取条目的数据
     *
     * @param position position
     * @return 数据
     */
    @Nullable
    public T getDataItemByPosition(@IntRange(from = 0) int position) {
        if (position < mData.size())
            return mData.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        // + 1 代表一个还有一个上拉加载布局
        return mData.size() + getFooterLayoutCount() + getHeaderLayoutCount() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    int visibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && visibleItemPosition + 1 == getItemCount()){
                        if (mOnLoadMoreListener != null){
                            mOnLoadMoreListener.onLoadMoreRequested();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.clearOnScrollListeners();
    }

    /**
     * 获取数据集
     *
     * @return
     */
    public List<T> getData() {
        return mData;
    }

    /**
     *  设置 Adapter 数据，此时会刷新整个页面布局,
     * @param data
     */
    public void setData(@Nullable List<T> data) {
        if (mData != null) mData.clear();
        this.mData = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        this.mData.add(data);
        notifyItemInserted(mData.size() + getHeaderLayoutCount());
    }

    public void addData(int position, @NonNull T data) {
        this.mData.add(data);
        notifyItemInserted(position + getHeaderLayoutCount());
    }

    /**
     * 往Adapter数据容器中添加新数据。
     *
     * @param data 数据容器
     */
    public void addData(@NonNull Collection<? extends T> data) {
        this.mData.addAll(data);
        notifyItemRangeInserted(mData.size() - data.size() + getHeaderLayoutCount(),
                data.size());
    }

    public void addData(int position, @NonNull Collection<? extends T> data) {
        this.mData.addAll(data);
        notifyItemRangeInserted(position + getHeaderLayoutCount(), data.size());
    }

    public void remove(int position) {
        this.mData.remove(position);
        int internalPosition = position + getHeaderLayoutCount();
        notifyItemRemoved(internalPosition);
//        notifyItemRangeRemoved(internalPosition, mData.size() - internalPosition);
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加多种布局类型
     *
     * @param key         这个key应该写入 viewType 的类型
     * @param layoutResId 相应 viewType 类型的布局
     * @return
     */
    final public BaseRecyclerViewAdapter addMultiLayout(int key, @LayoutRes int layoutResId) {
        mMultiLayouts.put(key, layoutResId);
        return this;
    }

    /**
     *  设置自定义的底部LoadingView， 需要继承 AbstractLoadingView 实现它的抽象方法
     * @param loadingView 具体实现类
     */
    public void setLoadingView(@NonNull AbstractLoadingView loadingView) {
        this.mLoadingView = loadingView;
    }

    public void setLoadingStatus(@LoadStatus int status) {
        this.mLoadingView.setLoadStatus(status);
//        notifyDataSetChanged();
        notifyItemChanged(getItemCount() - 1);
    }

    private int getLayoutId(int viewType) {
        return mMultiLayouts.get(viewType, TYPE_NOT_FOUND);
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     *
     * @see #addHeaderView(View)
     */
    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) return 0;
        return 1;
    }

    /**
     * if addFooterView will be return 1, if not will be return 0
     *
     * @see #addHeaderView(View)
     */
    public int getFooterLayoutCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) return 0;
        return 1;
    }

    public void addHeaderView(View header) {
        addHeaderView(header, -1, null);
    }

    public void addHeaderView(View header, LinearLayout.LayoutParams params) {
        addHeaderView(header, -1, params);
    }

    public void addHeaderView(View header, int index, LinearLayout.LayoutParams params) {
        addHeaderView(header, index, LinearLayout.VERTICAL, params);
    }


    public void addHeaderView(View header, int index, int orientation, LinearLayout.LayoutParams params) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }

            if (params == null) {
                /* 添加的一些默认属性*/
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = (int) mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp50);
                layoutParams.bottomMargin = (int) mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp10);
                mHeaderLayout.setLayoutParams(layoutParams);
                mHeaderLayout.setPadding(Utils.dip2px(mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp0)),
                        Utils.dip2px(mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp3)),
                        Utils.dip2px(mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp0)),
                        Utils.dip2px(mHeaderLayout.getContext().getResources().getDimension(R.dimen.dp0)));
            } else {
                mHeaderLayout.setLayoutParams(params);
            }
        }
        final int childCount = mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mHeaderLayout.addView(header, index);
        if (mHeaderLayout.getChildCount() == 1) {
            notifyItemInserted(0);
        }
    }

    public void addFooterView(View header) {
        addFooterView(header, -1, null);
    }

    public void addFooterView(View header, LinearLayout.LayoutParams params) {
        addFooterView(header, -1, params);
    }

    public void addFooterView(View header, int index, LinearLayout.LayoutParams params) {
        addFooterView(header, index, LinearLayout.VERTICAL, params);
    }


    public void addFooterView(View footer, int index, int orientation, LinearLayout.LayoutParams params) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }

            if (params == null) {
                /* 添加的一些默认属性*/
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = (int) mFooterLayout.getContext().getResources().getDimension(R.dimen.dp10);
                mFooterLayout.setLayoutParams(layoutParams);
                mFooterLayout.setPadding(Utils.dip2px(mFooterLayout.getContext().getResources().getDimension(R.dimen.dp3)),
                        Utils.dip2px(mFooterLayout.getContext().getResources().getDimension(R.dimen.dp0)),
                        Utils.dip2px(mFooterLayout.getContext().getResources().getDimension(R.dimen.dp3)),
                        Utils.dip2px(mFooterLayout.getContext().getResources().getDimension(R.dimen.dp0)));
            }else {
                mFooterLayout.setLayoutParams(params);
            }
        }
        final int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int position = getHeaderLayoutCount() + mData.size();
            notifyItemInserted(position);
        }
    }

    public static class VH extends RecyclerView.ViewHolder {

        private final SparseArray<View> views;
        private BaseRecyclerViewAdapter adapter;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.views = new SparseArray<>();
        }

        public VH setAdapter(BaseRecyclerViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public VH setAdapter(@IdRes int viewId, Adapter adapter) {
            View view = getView(viewId);
            if (view instanceof AdapterView)
                ((AdapterView)view).setAdapter(adapter);
            else
                throw new IllegalArgumentException("An instance of viewId is not an AdapterView !");
            return this;
        }


        public VH setText(@IdRes int viewId, CharSequence value) {
            TextView view = getView(viewId);
            view.setText(value);
            return this;
        }

        public VH setText(@IdRes int viewId, @StringRes int strId) {
            TextView view = getView(viewId);
            view.setText(strId);
            return this;
        }

        public VH setTextColor(@IdRes int viewId, @ColorInt int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }

        public VH setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return this;
        }

        public VH setImageDrawable(@IdRes int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public VH setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public VH setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public VH setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public VH setGone(@IdRes int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }

        public VH setVisible(@IdRes int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            return this;
        }

        public VH setTag(@IdRes int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }


        public VH setTag(@IdRes int viewId, int key, Object tag) {
            View view = getView(viewId);
            view.setTag(key, tag);
            return this;
        }

        public VH addOnClickListener(@IdRes final int viewId) {
            final View view = getView(viewId);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(v -> {
                    if (adapter.getOnItemChildClickListener() != null) {
                        adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, getClickPosition());
                    }
                });
            }
            return this;
        }

        /**
         *  条目中对应View选择事件
         * @param viewId view id
         * @param listener 监听事件
         * @return
         */
        public VH setOnItemSelectedClickListener(@IdRes int viewId, AdapterView.OnItemSelectedListener listener){
            View view = getView(viewId);
            if (view instanceof AdapterView)
                ((AdapterView)view).setOnItemSelectedListener(listener);
            else
                throw new IllegalArgumentException("An instance of viewId is not an AdapterView !");
            return this;
        }

        /**
         *  条目中对应View长点击事件
         * @param viewId view id
         * @param listener 监听事件
         * @return
         */
        public VH setOnItemLongClickListener(@IdRes int viewId, AdapterView.OnItemLongClickListener listener){
            View view = getView(viewId);
            if (view instanceof AdapterView)
                ((AdapterView)view).setOnItemLongClickListener(listener);
            else
                throw new IllegalArgumentException("An instance of viewId is not an AdapterView !");
            return this;
        }

        public <T extends View> T getView(@IdRes int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        private int getClickPosition() {
            if (getLayoutPosition() >= adapter.getHeaderLayoutCount()) {
                return getLayoutPosition() - adapter.getHeaderLayoutCount();
            }
            return 0;
        }
    }

    @IntDef({AbstractLoadingView.LOADING,
            AbstractLoadingView.LOADING_COMPLETE,
            AbstractLoadingView.LOADING_END,
            AbstractLoadingView.LOADING_FAIL})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @interface LoadStatus {
    }

    public static abstract class AbstractLoadingView {
        // 正在加载
        public static final int LOADING = 1;
        // 加载完成
        public static final int LOADING_COMPLETE = 2;
        // 加载到底
        public static final int LOADING_END = 3;
        // 加载出错
        public static final int LOADING_FAIL = 4;

        private int loadingStatus = LOADING_COMPLETE;

        public void convert(VH holder) {
            switch (loadingStatus) {
                case LOADING:
                    visibleLoading(holder);
                    break;
                case LOADING_COMPLETE:
                    visibleLoadComplete(holder);
                    break;
                case LOADING_END:
                    visibleLoadEnd(holder);
                    break;
                case LOADING_FAIL:
                    visibleFail(holder);
                    break;
            }
        }

        abstract @LayoutRes
        int getLoadingViewLayout();

        abstract void visibleLoadEnd(VH holder);

        abstract void visibleLoadComplete(VH holder);

        abstract void visibleLoading(VH holder);

        abstract void visibleFail(VH holder);

        public void setLoadStatus(@LoadStatus int status) {
            this.loadingStatus = status;
        }

        public int getLoadingStatus() {
            return loadingStatus;
        }
    }

    public static class DefaultLoadingView extends AbstractLoadingView {

        @Override
        int getLoadingViewLayout() {
            return R.layout.bottom_loading_view;
        }

        @Override
        void visibleLoadEnd(VH holder) {
            holder.setVisible(R.id.ll_loading, false);
            holder.setVisible(R.id.ll_loading_end, true);
            holder.setVisible(R.id.ll_loading_fail, false);
        }

        @Override
        void visibleLoadComplete(VH holder) {
            holder.setVisible(R.id.ll_loading, false);
            holder.setVisible(R.id.ll_loading_end, false);
            holder.setVisible(R.id.ll_loading_fail, false);
        }

        @Override
        void visibleLoading(VH holder) {
            holder.setVisible(R.id.ll_loading, true);
            holder.setVisible(R.id.ll_loading_end, false);
            holder.setVisible(R.id.ll_loading_fail, false);
        }

        @Override
        void visibleFail(VH holder) {
            holder.setVisible(R.id.ll_loading, false);
            holder.setVisible(R.id.ll_loading_end, false);
            holder.setVisible(R.id.ll_loading_fail, true);
        }
    }

    /**
     * 加载更多数据回调接口
     */
    @FunctionalInterface
    public interface OnLoadMoreListener {

        /**
         * 当需要加载更多数据时将会调用此接口方法
         */
        void onLoadMoreRequested();
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView itemView has been clicked.
     */
    @FunctionalInterface
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         *
         * @param adapter  the adpater
         * @param view     The itemView within the RecyclerView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(BaseRecyclerViewAdapter adapter, View view, int position);
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    @FunctionalInterface
    public interface OnItemLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param adapter  the adpater
         * @param view     The view whihin the RecyclerView that was clicked and held.
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        boolean onItemLongClick(BaseRecyclerViewAdapter adapter, View view, int position);
    }

    /**
     * Interface definition for a callback to be invoked when an itemchild in this
     * view has been clicked
     */
    @FunctionalInterface
    public interface OnItemChildClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the ItemView that was clicked
         * @param position The position of the view int the adapter
         */
        void onItemChildClick(BaseRecyclerViewAdapter adapter, View view, int position);
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been long clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }
}
