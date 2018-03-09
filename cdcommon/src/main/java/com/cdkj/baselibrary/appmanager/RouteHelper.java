package com.cdkj.baselibrary.appmanager;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由管理
 * Created by cdkj on 2017/10/12.
 */

public class RouteHelper {
    //跳转到登录页面
    public static final String APPLOGIN = "/user/login";
    //找回密码
    public static final String CDFINDPWD = "/cdcommon/findpwd";
    //WebView
    public static final String CDWEBVIEW = "/cdcommon/web";



    /**
     * 打开登录
     *
     * @param canopenmain
     */
    public static void openLogin(boolean canopenmain) {
        ARouter.getInstance().build(APPLOGIN)
                .withBoolean("canOpenMain", canopenmain)
                .navigation();
    }

}
