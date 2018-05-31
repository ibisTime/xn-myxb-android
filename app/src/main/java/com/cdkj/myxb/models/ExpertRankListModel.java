package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * 专家排名
 * Created by cdkj on 2018/2/28.
 */

public class ExpertRankListModel {


    /**
     * code : RA201802241703201078405
     * type : 1
     * periods : 2018_1
     * refNo : U201802281351106684511
     * rank : 111
     * amount : 111000
     * name : 13765051712
     */

    private String code;
    private String type;
    private String periods;
    private String refNo;
    private int rank;
    private BigDecimal amount;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
