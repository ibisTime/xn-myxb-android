package com.cdkj.myxb.models;

/**
 * Created by cdkj on 2018/5/15.
 */

public class WithdrawTipModel {


    /**
     * CUSERQXFL : 0.01
     * QXDBZDJE : 50000
     * CUSERDZTS : 5
     * CUSERQXBS : 5
     * CUSERMONTIMES : 5
     */

    private Double CUSERQXFL;
    private String QXDBZDJE;
    private String CUSERDZTS;
    private String CUSERQXBS;
    private String CUSERMONTIMES;

    public Double getCUSERQXFL() {
        return CUSERQXFL;
    }

    public void setCUSERQXFL(Double CUSERQXFL) {
        this.CUSERQXFL = CUSERQXFL;
    }

    public String getQXDBZDJE() {
        return QXDBZDJE;
    }

    public void setQXDBZDJE(String QXDBZDJE) {
        this.QXDBZDJE = QXDBZDJE;
    }

    public String getCUSERDZTS() {
        return CUSERDZTS;
    }

    public void setCUSERDZTS(String CUSERDZTS) {
        this.CUSERDZTS = CUSERDZTS;
    }

    public String getCUSERQXBS() {
        return CUSERQXBS;
    }

    public void setCUSERQXBS(String CUSERQXBS) {
        this.CUSERQXBS = CUSERQXBS;
    }

    public String getCUSERMONTIMES() {
        return CUSERMONTIMES;
    }

    public void setCUSERMONTIMES(String CUSERMONTIMES) {
        this.CUSERMONTIMES = CUSERMONTIMES;
    }
}
