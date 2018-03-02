package com.cdkj.myxb.models;

/**
 * Created by cdkj on 2018/3/2.
 */

public class InvitationModel {

    /**
     * totalAmount : 0
     * totalUser : 0
     */

    private int totalAmount; //获得积分
    private int totalUser;//总人数

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}
