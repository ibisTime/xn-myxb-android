package com.cdkj.myxb.module.order;

import android.text.TextUtils;

import com.cdkj.myxb.models.AppointmentListModel;

/**
 * Created by cdkj on 2018/2/23.
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

    /*1, 待审核, 2, 已排班待上门,3, 无档期, 4, 已上门待下课, 5, 已下课待录入,6, 已录入;*/
    public static final String APPOINTMENTALL = "";
    public static final String APPOINTMENT_1 = "1";
    public static final String APPOINTMENT_2 = "2";
    public static final String APPOINTMENT_3 = "3";
    public static final String APPOINTMENT_4 = "4";
    public static final String APPOINTMENT_5 = "5";
    public static final String APPOINTMENT_6 = "6";


    /**
     * 获取预约状态
     *
     * @param state
     * @return
     */
    public static String getAppoitmentState(String state) {

        switch (state) {
            case APPOINTMENT_1:
                return "待审核";
            case APPOINTMENT_2:
                return "待上门";
            case APPOINTMENT_3:
                return "已取消";
            case APPOINTMENT_4:
                return "待下课";
            case APPOINTMENT_5:
                return "待录入";
            case APPOINTMENT_6:
                return "已完成";
        }

        return "";

    }


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
     * 积分订单
     * 是否可以显示底部按钮 （待收货 待评价可以显示）
     *
     * @return
     */
    public static boolean canShowIntegralOrderButton(String state) {
        return TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET) || TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEECOMMENT);
    }

    /**
     * 订单状态
     * 是否可以显示底部按钮 （待评价可以显示）
     *
     * @return
     */
    public static boolean canShowOrderButton(String state) {
        return TextUtils.equals(state, OrderHelper.ORDERWAITEECOMMENT);
    }

    /**
     * 预约列表
     * 是否可以显示底部按钮 （待上门  待录入 ）
     *
     * @return
     */
    public static boolean canShowAppointmentButton(String state) {
        return TextUtils.equals(state, OrderHelper.APPOINTMENT_2) || TextUtils.equals(state, OrderHelper.APPOINTMENT_4);
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

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getAppointmentBtnStateString(String state) {

        switch (state) {
            case APPOINTMENT_2:
                return "确认上门";
            case APPOINTMENT_4:
                return "确认下课";
        }

        return "";

    }


    /**
     * 预约list 详情呢弄否显示评价
     * 是否可以进行评价 0 可以评价 (待审核 已排班待上门时不能评价)
     *
     * @param item
     * @return
     */
    public static boolean canAppointmentComment(AppointmentListModel item) {
        return TextUtils.equals("0", item.getIsComment()) && !TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_1) && TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_2);
    }


}
