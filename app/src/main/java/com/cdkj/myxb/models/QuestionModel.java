package com.cdkj.myxb.models;

/**
 * Created by cdkj on 2018/5/1.
 */

public class QuestionModel {


    /**
     * content : 问题内容
     * orderNo : 1
     * remark : 备注
     * status : 已提交
     * title : 标题
     * updateDatetime : Feb 10, 2018 1:55:48 PM
     * updaterMobile : 1
     * updater : 更新人
     */

    private String content;
    private String orderNo;
    private String remark;
    private String status;
    private String title;
    private String updateDatetime;
    private int updaterMobile;
    private String updater;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public int getUpdaterMobile() {
        return updaterMobile;
    }

    public void setUpdaterMobile(int updaterMobile) {
        this.updaterMobile = updaterMobile;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
