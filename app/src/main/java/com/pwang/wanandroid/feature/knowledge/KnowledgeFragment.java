package com.pwang.wanandroid.feature.knowledge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

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

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.View {

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.knowledge_page_fragment;
    }

    @Override
    public void reloadData() {

    }
}
