package com.pwang.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseFragment <T extends BasePresenter> extends DaggerFragment {

    Unbinder unbinder;

    @Inject
    protected T mPresenter;

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
}
