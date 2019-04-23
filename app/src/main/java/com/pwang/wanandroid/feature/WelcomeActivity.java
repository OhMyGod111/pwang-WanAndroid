package com.pwang.wanandroid.feature;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.pwang.wanandroid.R;
import com.pwang.wanandroid.base.BaseActivity;
import com.pwang.wanandroid.feature.home.MainActivity;
import com.pwang.wanandroid.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.sf_container)
    ShimmerFrameLayout sfContainer;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private MyCountDownTimer downTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);


        Shimmer.AlphaHighlightBuilder highlightBuilder = new Shimmer.AlphaHighlightBuilder();
        highlightBuilder.setAutoStart(true).setDuration(2000).setRepeatMode(500).setShape(Shimmer.Shape.RADIAL);
        Shimmer shimmer = highlightBuilder.build();
        sfContainer.setShimmer(shimmer);
        sfContainer.startShimmer();
        downTimer = new MyCountDownTimer(5000, 1000);
        downTimer.setTextView(tvTime);
        downTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sfContainer.stopShimmer();
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
    }

    private void gotoMainActivity() {
        ActivityUtils.startActivity(WelcomeActivity.this, MainActivity.class);
        finish();
    }

    @OnClick(R.id.tv_time)
    public void onViewClicked() {
        gotoMainActivity();
    }

    public static class MyCountDownTimer extends CountDownTimer {

        TextView tvTime;
        private String temp;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {Ø@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String text = String.format(temp, String.valueOf(millisUntilFinished / 1000));
            tvTime.setText(text);
        }

        @Override
        public void onFinish() {
            WelcomeActivity activity = (WelcomeActivity) tvTime.getContext();
            activity.gotoMainActivity();
        }

        public void setTextView(TextView tvTime) {
            this.tvTime = tvTime;
            this.temp = tvTime.getText().toString();
        }
    }
}
