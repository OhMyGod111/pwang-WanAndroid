package com.pwang.wanandroid.feature.project;

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
public class ProjectFragment extends BaseFragment <ProjectPresenter> implements ProjectContract.View{

    @Inject
    public ProjectFragment() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.project_page_fragment;
    }

    @Override
    public void reloadData() {

    }
}
