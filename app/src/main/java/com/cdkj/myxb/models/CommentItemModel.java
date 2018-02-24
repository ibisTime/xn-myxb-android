package com.cdkj.myxb.models;

/**
 * Created by 李先俊 on 2018/2/24.
 */

public class CommentItemModel {


    /**
     * id : 16.0
     * type : ISW
     * ckey : FW
     * cvalue : 服务评分
     * updater : admin
     * updateDatetime : Feb 7, 2018 3:59:12 PM
     * remark : 服务评分
     * companyCode : CD-CXB000020
     * systemCode : CD-CXB000020
     */

    private String ckey;
    private String cvalue;

    private String cScore="0"; //默认0分

    public String getcScore() {
        return cScore;
    }

    public void setcScore(String cScore) {
        this.cScore = cScore;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }
}
