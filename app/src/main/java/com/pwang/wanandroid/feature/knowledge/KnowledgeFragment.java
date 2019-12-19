package com.pwang.wanandroid.feature.knowledge;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.orhanobut.logger.Logger;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;

import javax.inject.Inject;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/03/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class KnowledgeFragment extends BaseFragment <KnowledgePresenter> implements KnowledgeContract.View{

    public static KnowledgeFragment newInstance(String arg1, String arg2) {
        Bundle args = new Bundle();

        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public KnowledgeFragment() {
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) return;
        ViewStub viewStub = getView().findViewById(R.id.vb_fail_page);
        if (viewStub == null) return;
        Logger.d("viewStub:" + viewStub.getClass().getName()
                + " ### getView():" +getView().getClass().getName()
                + " ### threadName:" + Thread.currentThread().getName());
//        viewStub.setVisibility(View.VISIBLE);
        viewStub.inflate();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.knowledge_page_fragment;
    }

    @Override
    public void reloadData() {

    }
}
