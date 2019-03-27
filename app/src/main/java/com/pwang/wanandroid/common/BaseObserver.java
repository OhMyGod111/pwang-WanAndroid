package com.pwang.wanandroid.common;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseView;
import com.pwang.wanandroid.util.NetworkUtils;
import com.pwang.wanandroid.util.Utils;

import io.reactivex.observers.ResourceObserver;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/03/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {
    protected BaseView mBaseView;
    protected String mErrorMsg;
    protected boolean mShowError;

    public BaseObserver(BaseView view) {
        this.mBaseView = view;
    }

    public BaseObserver(BaseView mView, String mErrorMsg) {
        this.mBaseView = mView;
        this.mErrorMsg = mErrorMsg;
    }

    /**
     *
     * @param view {@link BaseView}
     * @param mErrorMsg 错误消息
     * @param showError true 将显示 error 页面
     */
    public BaseObserver(BaseView view, String mErrorMsg, boolean showError) {
        this.mBaseView = view;
        this.mErrorMsg = mErrorMsg;
        this.mShowError = showError;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!NetworkUtils.isNetConnected()){
            /* 调用此函数，不会从网络缓存中拿取数据了 */
            mBaseView.showPromptMessage("不好意思，网络不通！");
            mBaseView.showErrorPage(ErrorPageType.NET_ERROR_TYPE);
//            this.dispose();
        }
        onComplete();
    }

    @Override
    public void onComplete() {
        Logger.d("onComplete");
        releaseResources();
    }

    @Override
    public void onError(Throwable e) {
        /* 统一处理异常 */
        if (mBaseView == null) return;

        if (!TextUtils.isEmpty(mErrorMsg)){
            mBaseView.showPromptMessage(mErrorMsg);
            e.printStackTrace();
        }else{
            mBaseView.showPromptMessage(Utils.getAppContext().getString(R.string.unknown_error));
            e.printStackTrace();
        }

        if (mShowError){
            /* 处理错误页面 */
            mBaseView.showErrorPage(ErrorPageType.NO_DATA_TYPE);
        }

        releaseResources();
    }

    /* 资源释放 */
    private void releaseResources() {
        if (mBaseView != null) mBaseView = null;
        if (mErrorMsg != null) mErrorMsg = null;
    }
}
