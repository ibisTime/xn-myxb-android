package com.cdkj.myxb.module.order;

import android.text.TextUtils;

/**
 * Created by 李先俊 on 2018/2/23.
 */

public class OrderHelper {


    public static final String INTEGRALORDERWAITESEND = "0";//待发货；
    public static final String INTEGRALORDERWAITEGET = "1";//待收货；
    public static final String INTEGRALORDERWAITEECOMMENT = "2";//待评价；
    public static final String INTEGRALORDERDONE = "3";//已完成；
    public static final String INTEGRALORDERCANCEL = "4";//已取消；


    /*0待审核，1审核未通过，2待发货，3待评价，4已完成*/
    public static final String ORDERWAITEAUDIT = "0";//0待审核；
    public static final String ORDERWAITEAUDIT_NO = "1";//1审核未通过；
    public static final String ORDERWAITESEND = "2";//2待发货；
    public static final String ORDERWAITEECOMMENT = "3";//3待评价；
    public static final String ORDERDONE = "4";//4已完成；

    /**
     * @param state
     * @return
     */
    public static String getIntegralOrderState(String state) {

        switch (state) {
            case INTEGRALORDERWAITESEND:
                return "待发货";
            case INTEGRALORDERWAITEGET:
                return "待收货";
            case INTEGRALORDERWAITEECOMMENT:
                return "待评价";
            case INTEGRALORDERDONE:
                return "已完成";
            case INTEGRALORDERCANCEL:
                return "已取消";
        }
        return "";

    }

    /**
     * @param state
     * @return
     */
    public static String getOrderState(String state) {

        switch (state) {
            case ORDERWAITEAUDIT:
                return "待审核";
            case ORDERWAITEAUDIT_NO:
                return "审核未通过";
            case ORDERWAITESEND:
                return "待发货";
            case ORDERWAITEECOMMENT:
                return "待评价";
            case ORDERDONE:
                return "已完成";
        }
        return "";

    }

    /**
     * 是否可以显示底部按钮 （待收货 待评价可以显示）
     *
     * @return
     */
    public static boolean canShowIntegralOrderButton(String state) {
        return TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET) || TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEECOMMENT);
    }

    /**
     * 是否可以显示底部按钮 （待评价可以显示）
     *
     * @return
     */
    public static boolean canShowOrderButton(String state) {
        return TextUtils.equals(state, OrderHelper.ORDERWAITEECOMMENT);
    }

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getIntegralBtnStateString(String state) {
        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET)) {
            return "确认收货";
        }

        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEECOMMENT)) {
            return "前往评价";
        }

        return "";

    }

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getOrderBtnStateString(String state) {

        if (TextUtils.equals(state, OrderHelper.ORDERWAITEECOMMENT)) {
            return "前往评价";
        }

        return "";

    }


}
