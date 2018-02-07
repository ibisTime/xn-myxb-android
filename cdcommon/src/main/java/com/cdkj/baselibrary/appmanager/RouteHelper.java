package com.cdkj.baselibrary.appmanager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cdkj.baselibrary.activitys.FindPwdActivity;

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
     * 找回登录密码
     *
     * @param phoneNumber 手机号码
     */
    public static void openFindPwd(String phoneNumber) {
        ARouter.getInstance().build(CDFINDPWD)
                .withString(FindPwdActivity.PHONENUMBERSING, phoneNumber)
                .navigation();
    }


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
