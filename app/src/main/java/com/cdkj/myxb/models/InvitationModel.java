package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/3/2.
 */

public class InvitationModel {

    /**
     * totalAmount : 0
     * totalUser : 0
     */

    private BigDecimal totalAmount; //获得积分
    private int totalUser;//总人数

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}
