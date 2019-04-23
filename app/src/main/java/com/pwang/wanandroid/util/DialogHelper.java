package com.pwang.wanandroid.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.pwang.wanandroid.R;
import com.pwang.wanandroid.util.PermissionUtils.OnRationaleListener.ShouldRequest;

/**
 * <pre>
 *    @author :WangPan
 *    @e-mail :501098462@qq.con
 *    @time   :2019/04/21
 *    @desc   :
 *    @version:1.0
 * </pre>
 */
public final class DialogHelper {

    public static void showRationaleDialog(ShouldRequest shouldRequest) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    shouldRequest.again(true);
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    shouldRequest.again(false);
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showOpenAppSettingDialog() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> PermissionUtils.launchAppDetailsSettings())
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    ActivityUtils.finishAllActivities();
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
