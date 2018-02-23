package com.cdkj.myxb.module.order;

import android.text.TextUtils;

/**
 * Created by 李先俊 on 2018/2/23.
 */

public class OrderHelper {


    public static final String INTEGRALORDERWAITESEND = "0";//待发货；
    public static final String INTEGRALORDERWAITEGET = "1";//待收货；
    public static final String INTEGRALORDERWAITEEVALUATION = "2";//待评价；
    public static final String INTEGRALORDERDONE = "3";//已完成；
    public static final String INTEGRALORDERCANCEL = "4";//已取消；

    /*0待发货，1待收货，2待评价，3已完成，4无货取消*/

    /**
     * 获取积分订单状态
     *
     * @return
     */
    public static String getIntegralOrderState(String state) {

        switch (state) {
            case "0":
                return "待发货";
            case "1":
                return "待收货";
            case "2":
                return "待评价";
            case "3":
                return "已完成";
            case "4":
                return "已取消";
        }
        return "";

    }

    /**
     * 是否可以显示底部按钮 （待评价可以显示）
     *
     * @return
     */
    public static boolean canShowButton(String state) {
        return TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET) || TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEEVALUATION);
    }

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getBtnStateString(String state) {
        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET)) {
            return "确认收货";
        }

        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEEVALUATION)) {
            return "前往评价";
        }

        return "";

    }



}
