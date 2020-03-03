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

    String TAG = "True";
    @Override
    protected void initView() {

        if (getView() != null) {
            TextView textView = getView().findViewById(R.id.textView4);

            String message = "教育部表示，上述高考评价体系有关新闻发布会非教育部考试中心举办；同时，教育部考试中心人员未参加新闻发布会，更未进行过解读。";

            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(message);

            stringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    widget.setTag(TAG);
                    Toast.makeText(getActivity(), "ClickableSpan 主体！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setColor(Color.RED);
                }
            }, 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            textView.setText(stringBuilder);
            textView.setMovementMethod(CustomLinkMovementMethod.getInstance());

            textView.setOnClickListener(v -> {
                if (TAG.equals(v.getTag())){
                    v.setTag("False");
                    return;
                }
                Toast.makeText(getActivity(), "TextView 主体！", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) return;
        ViewStub viewStub = getView().findViewById(R.id.vb_fail_page);
        if (viewStub == null) return;
        Logger.d("viewStub:" + viewStub.getClass().getName()
                + " ### getView():" + getView().getClass().getName()
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

    public static class CustomLinkMovementMethod extends LinkMovementMethod{

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            boolean b = super.onTouchEvent(widget,buffer,event);
            //解决点击事件冲突问题
            if(!b && event.getAction() == MotionEvent.ACTION_UP){
                ViewParent parent = widget.getParent();//处理widget的父控件点击事件
                if (parent instanceof ViewGroup) {
                    return ((ViewGroup) parent).performClick();
                }
            }
            return b;
        }

        public static CustomLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new CustomLinkMovementMethod();

            return sInstance;
        }


        private static CustomLinkMovementMethod sInstance;

    }
}
