package com.cdkj.myxb.module.order;

import android.text.TextUtils;

import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.module.user.UserHelper;

/**
 * Created by cdkj on 2018/2/23.
 */

public class OrderHelper {


    /*积分订单*/
    public static final String INTEGRALORDERWAITESEND = "0";//待发货；
    public static final String INTEGRALORDERWAITEGET = "1";//待收货；
    public static final String INTEGRALORDERWAITEECOMMENT = "2";//待评价；
    public static final String INTEGRALORDERDONE = "3";//已完成；
    public static final String INTEGRALORDERCANCEL = "4";//已取消；


    /*产品订单*/
    /*0待审核，1审核未通过，2待发货，3待评价，4已完成*/
    public static final String ORDER_WAITE_PAY = "0";//0待付款
    public static final String ORDER_PAYED = "1";//1已付款待发货
    public static final String ORDER_USER_CANCEL = "2";//用户已取消
    public static final String ORDER_OSS_CANCEL = "3";//平台已取消；
    public static final String ORDER_WAITE_GET = "4";//待收货；
    public static final String ORDER_WAITE_COMMENT = "5";//待评价；
    public static final String ORDERDONE = "6";//已完成；

    /*成果订单 预约订单*/
    /*1, 已预约待接单, 2, 已排班待上门,3, 无档期, 4, 已上门待下课, 5, 已下课待录入,6, 已录入;*/
    public static final String APPOINTMENTALL = "";
    public static final String APPOINTMENT_1 = "1";
    public static final String APPOINTMENT_2 = "2";
    public static final String APPOINTMENT_3 = "3";
    public static final String APPOINTMENT_4 = "4";
    public static final String APPOINTMENT_5 = "5";
    public static final String APPOINTMENT_6 = "6";
    public static final String APPOINTMENT_7 = "7";
    public static final String APPOINTMENT_8 = "8";
    public static final String APPOINTMENT_9 = "9";
    public static final String APPOINTMENT_10 = "10";
    public static final String APPOINTMENT_11 = "11";


    public static final String APPOINTMENT_ALL = "全部";
    public static final String APPOINTMENT_FWZT = "服务状态";
    public static final String APPOINTMENT_DJD = "待接单";
    public static final String APPOINTMENT_DLR = "待录入";
    public static final String APPOINTMENT_DSH = "待审核";
    public static final String APPOINTMENT_YWC = "已完成";

    /**
     * 获取预约状态
     *
     * @param state
     * @return
     */
    public static String getAppoitmentState(String state) {

        switch (state) {
            case APPOINTMENT_1:
                return "待接单";
            case APPOINTMENT_2:
                return "待上门";
            case APPOINTMENT_3:
                return "无档期";
            case APPOINTMENT_4:
                return "待下课";
            case APPOINTMENT_5:
                return "待录入";
            case APPOINTMENT_6:
                return "已录入";
            case APPOINTMENT_7:
                return "经销商审核通过";
            case APPOINTMENT_8:
                return "经销商审核不通过";
            case APPOINTMENT_9:
                return "已支付";
            case APPOINTMENT_10:
                return "已完成";
            case APPOINTMENT_11:
                return "后台复核不通过";

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
            case ORDER_WAITE_PAY:
                return "待付款";
            case ORDER_PAYED:
                return "已付款待发货";
            case ORDER_USER_CANCEL:
                return "已取消";
            case ORDER_OSS_CANCEL:
                return "平台已取消";
            case ORDER_WAITE_GET:
                return "待收货";
            case ORDER_WAITE_COMMENT:
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
        return TextUtils.equals(state, OrderHelper.ORDER_WAITE_COMMENT) || TextUtils.equals(state, OrderHelper.ORDER_WAITE_PAY);
    }

    /**
     * 预约列表
     * 是否可以显示底部按钮 （待上门  待录入 ）
     *
     * @return
     */
    public static boolean canShowAppointmentButton(String state) {
        return TextUtils.equals(state, OrderHelper.APPOINTMENT_1) || TextUtils.equals(state, OrderHelper.APPOINTMENT_2) || TextUtils.equals(state, OrderHelper.APPOINTMENT_4);
    }

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getIntegralBtnStateString(String state) {
        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITESEND)) {
            return "取消订单";
        }

        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEGET)) {
            return "确认收货";
        }

        if (TextUtils.equals(state, OrderHelper.INTEGRALORDERWAITEECOMMENT)) {
            return "评价";
        }

        return "";

    }

    /**
     * 积分订单底部按钮文字显示
     *
     * @return
     */
    public static String getOrderBtnStateString(String state) {

        if (TextUtils.equals(state, OrderHelper.ORDER_WAITE_COMMENT)) {
            return "评价";
        }

        if (TextUtils.equals(state, OrderHelper.ORDER_WAITE_PAY)) {
            return "取消订单";
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
            case APPOINTMENT_1:
                return "接单";
            case APPOINTMENT_2:
                return "确认上门";
            case APPOINTMENT_4:
                return "确认下课";
        }

        return "";

    }


    /**
     * 预约list 详情呢弄否显示评价
     * 是否可以进行评价 0 可以评价 (待录入 已完成才可以进行评价)
     *
     * @param item
     * @return
     */
    public static boolean canAppointmentComment(AppointmentListModel item) {
        return TextUtils.equals("0", item.getIsComment())
                && (TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_5)
                || TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_6));
    }


    /**
     * 根据用户类型判断是否能显示带录入状态 服务团队和讲师不能显示
     *
     * @param type
     * @return
     */
    public static boolean canShowWaiteInputByUserType(String type) {
        return TextUtils.equals(type, UserHelper.S);
    }

}
