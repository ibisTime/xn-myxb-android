package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/2/23.
 */

public class AccountListModel {


    /**
     * accountNumber : A201802221556357872484
     * userId : U201802221556357869675
     * realName : 13765051712
     * type : T
     * status : 0
     * currency : JF
     * amount : 0.0
     * md5 : f0ed31502f5d1f206753a5e8114c87e0
     * addAmount : 0.0
     * inAmount : 0.0
     * outAmount : 0.0
     * createDatetime : Feb 22, 2018 3:56:35 PM
     */

    private String accountNumber;
    private String userId;
    private String realName;
    private String type;
    private String status;
    private String currency;
    private BigDecimal amount;
    private String md5;
    private double addAmount;
    private double inAmount;
    private double outAmount;
    private String createDatetime;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public double getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(double addAmount) {
        this.addAmount = addAmount;
    }

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(double outAmount) {
        this.outAmount = outAmount;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
}
