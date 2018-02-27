package com.cdkj.myxb.module.user;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.cdkj.myxb.BaseApplication;
import com.cdkj.myxb.R;

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


    /**
     * 根据用户类型获取预约标题
     *
     * @param state
     * @return
     */
    public static String getAppointmentTypeByState(String state) {

        switch (state) {
            case L:
                return "讲师预约";
            case T:
                return "美导预约";
            case S:
                return "专家预约";
        }

        return "预约";

    }

    /**
     * 根据kind获取类型
     *
     * @param kind
     * @return
     */
    public static String getUserTypeByKind(String kind) {
        switch (kind) {
            case UserHelper.C:
                return BaseApplication.getInstance().getString(R.string.mry);
            case UserHelper.L:
                return BaseApplication.getInstance().getString(R.string.teacher);
            case UserHelper.S:
                return BaseApplication.getInstance().getString(R.string.experts);
            case UserHelper.T:
                return BaseApplication.getInstance().getString(R.string.shopper);
        }
        return "";
    }

}
