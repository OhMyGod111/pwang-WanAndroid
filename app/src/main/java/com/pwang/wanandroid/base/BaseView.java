package com.pwang.wanandroid.base;

import com.pwang.wanandroid.common.ErrorPageType;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface BaseView{

    /**
     *  显示提示信息
     * @param msg
     */
    void showPromptMessage(String msg);

    /**
     *
     * @param type
     */
    void showErrorPage(@ErrorPageType int type);
}
