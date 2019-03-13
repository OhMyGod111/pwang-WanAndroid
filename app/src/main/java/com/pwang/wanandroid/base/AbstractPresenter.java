package com.pwang.wanandroid.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/03/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class AbstractPresenter <T extends BaseView> implements BasePresenter <T>{

    private static final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected T mView;

    @Override
    public void takeView(T view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mCompositeDisposable.clear();
    }

    protected void addDisposable(Disposable disposable){
        mCompositeDisposable.add(disposable);
    }
}
