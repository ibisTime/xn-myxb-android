package com.cdkj.myxb.module.user;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 李先俊 on 2018/2/22.
 */

public class UserHelper {
    /*C:美容院,P:平台用户,L:讲师,S:专家,T:美导,A:顾问,M:人力,*/

    public static final String C = "C";//美容院
    public static final String L = "L";//讲师
    public static final String S = "S";//专家
    public static final String T = "T";//美导


    @StringDef({C, L, S, T})
    @Retention(RetentionPolicy.SOURCE)
    public @interface userType {
    }


}
