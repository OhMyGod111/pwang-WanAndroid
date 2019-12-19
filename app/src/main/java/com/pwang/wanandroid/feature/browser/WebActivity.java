package com.pwang.wanandroid.feature.browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseActivity;
import com.pwang.wanandroid.data.network.entity.ArticleDetail;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *    @author :WangPan
 *    @e-mail :501098462@qq.con
 *    @time   :2019/04/20
 *    @desc   :
 *    @version:1.0
 * </pre>
 */
public class WebActivity extends BaseActivity implements WebContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_web_container)
    FrameLayout flWebContainer;
    private AgentWeb mAgentWeb;

    @Inject
    WebPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceCompat.beginSection("onCreate");
        setContentView(R.layout.web_activity);
        TraceCompat.endSection();
        ButterKnife.bind(this);
        if (mPresenter !=null)mPresenter.takeView(this);
        setupWebView();
    }

    @Override
    protected void onPause() {
//        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
//        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
//        mAgentWeb.getWebLifeCycle().onDestroy();
        if (mPresenter !=null){
            mPresenter.dropView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb == null) return false;
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setupWebView() {
        ArticleDetail webData = (ArticleDetail) getIntent().getSerializableExtra("web_data");
        String link = webData.getLink();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebContainer,
                        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready().go(link);


        toolbar.setTitle(Html.fromHtml(webData.getTitle()));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void showPromptMessage(String msg) {

    }

    @Override
    public void showErrorPage(int type) {

    }

    @Override
    public void reloadData() {

    }
}
