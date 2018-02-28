package com.cdkj.myxb.models;

/**
 * Created by 李先俊 on 2018/2/28.
 */

public class UpdateUserInfo {


    /**
     * approveDatetime : Feb 25, 2018 8:13:45 PM
     * approver : 审核人
     * code : UI201802252009483512747
     * description : 个人简介
     * realName : 名字
     * remark : 备注
     * speciality : 专长领域
     * status : 3
     * style : 授课风格
     * userId : U201802081714236359986
     */

    private String approveDatetime;
    private String approver;
    private String code;
    private String description;
    private String realName;
    private String remark;
    private String speciality;
    private String status;
    private String style;
    private String userId;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    private String slogan;

    public String getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(String approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
