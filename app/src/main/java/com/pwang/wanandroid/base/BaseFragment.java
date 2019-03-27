package com.pwang.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.common.ErrorPageType;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment <T extends BasePresenter> extends DaggerFragment implements BaseView{

    Unbinder unbinder;

    @Inject
    protected T mPresenter;
    protected View mErrorPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        if (mPresenter != null) mPresenter.takeView(this);
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.dropView();
            mPresenter = null;
        }

        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *  统一处理异常页面
     * @param type 异常页面类型  {@link ErrorPageType}
     */
    @Override
    public void showErrorPage(@ErrorPageType int type){
        if (getView() == null) return;
        ViewStub viewStub  = getView().findViewById(R.id.vb_fail_page);
        if (viewStub != null) {
            this.mErrorPage = viewStub.inflate();
            View view = mErrorPage.findViewById(R.id.specific_fail_page);
            switch (type){
                case ErrorPageType.NET_ERROR_TYPE:
//                    AppCompatTextView tvLabelName = view.findViewById(R.id.tv_label_name);
                    AppCompatButton btnRetry = view.findViewById(R.id.btn_net_retry);
                    btnRetry.setOnClickListener(v -> reloadData());
                    break;
                case ErrorPageType.NO_DATA_TYPE:
                    AppCompatTextView labelName = view.findViewById(R.id.tv_label_name);
                    labelName.setText(getString(R.string.tv_no_data_error));
                    AppCompatButton retry = view.findViewById(R.id.btn_net_retry);
                    retry.setOnClickListener(v -> reloadData());
                    break;
            }
        }

    }

    @Override
    public void showPromptMessage(String msg) {
        if (isAdded()){
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean isErrorPageShow(){
        if (mErrorPage == null)return false;
        return mErrorPage.getVisibility() == View.VISIBLE;
    }

    protected boolean hideErrorPage(){
        if (mErrorPage == null) return false;
        else if (mErrorPage.getVisibility() == View.VISIBLE) mErrorPage.setVisibility(View.GONE);
        else mErrorPage.setVisibility(View.GONE);
        return true;
    }
}
