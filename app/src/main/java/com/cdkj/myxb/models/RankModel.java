package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2018/3/1.
 */

public class RankModel {


    /**
     * amount : 535678
     * code : RA201802261328294551754
     * name : 1112
     * periods : 2018_2
     * rank : 2
     * refNo : B201802081729499985153
     * type : 0
     */

    private BigDecimal amount;
    private String code;
    private String name;
    private String periods;
    private int rank;
    private String refNo;
    private String type;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
