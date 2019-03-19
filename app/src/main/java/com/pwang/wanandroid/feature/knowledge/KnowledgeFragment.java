package com.pwang.wanandroid.feature.knowledge;

import android.os.Bundle;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseFragment;
import com.pwang.wanandroid.di.scoped.ActivityScoped;

import javax.inject.Inject;
import javax.inject.Qualifier;

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
    protected int getLayoutId() {
        return R.layout.knowledge_page_fragment;
    }
}
