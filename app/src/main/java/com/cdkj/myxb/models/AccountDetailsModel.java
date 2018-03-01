package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/2/23.
 */

public class AccountDetailsModel {


    /**
     * accountNumber : A201802221556357872484
     * userId : U201802221556357869675
     * realName : 13765051712
     * type : T
     * status : 0
     * currency : JF
     * amount : 99999995770
     * md5 : 518d5a486794106fceffc8baf9aecf44
     * addAmount : 100000000000
     * inAmount : 0
     * outAmount : 0
     * createDatetime : Feb 22, 2018 3:56:35 PM
     * lastOrder : J20180223155226331707
     */

    private String accountNumber;
    private String userId;
    private String realName;
    private String type;
    private String status;
    private String currency;
    private BigDecimal amount;
    private String md5;
    private BigDecimal addAmount;
    private BigDecimal inAmount;
    private BigDecimal outAmount;
    private String createDatetime;
    private String lastOrder;

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


    public String getMd5() {
        return md5;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public BigDecimal getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(BigDecimal addAmount) {
        this.addAmount = addAmount;
    }

    public BigDecimal getInAmount() {
        return inAmount;
    }

    public void setInAmount(BigDecimal inAmount) {
        this.inAmount = inAmount;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(String lastOrder) {
        this.lastOrder = lastOrder;
    }
}
