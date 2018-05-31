package com.cdkj.myxb.models;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class AtlasListModel {

    private String userId;
    private String mobile;
    private String nickName;
    private String realName;
    private String photo;
    private String kind;
    private double maxNumber;
    private String mdUserId;
    private String parentBUserId;
    private String parentAUserId;
    private double line;
    private String isRelation;
    private boolean tradepwdFlag;
    private String isBuildable;
    private boolean isShow = false;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIsBuildable() {
        return isBuildable;
    }

    public void setIsBuildable(String isBuildable) {
        this.isBuildable = isBuildable;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private List<AtlasListModel> jxsList;
    private List<AtlasListModel> fwsList;
    private List<AtlasListModel> mgJxsList;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String loginName) {
        this.nickName = loginName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getMdUserId() {
        return mdUserId;
    }

    public void setMdUserId(String mdUserId) {
        this.mdUserId = mdUserId;
    }

    public String getParentBUserId() {
        return parentBUserId;
    }

    public void setParentBUserId(String parentBUserId) {
        this.parentBUserId = parentBUserId;
    }

    public String getParentAUserId() {
        return parentAUserId;
    }

    public void setParentAUserId(String parentAUserId) {
        this.parentAUserId = parentAUserId;
    }

    public double getLine() {
        return line;
    }

    public void setLine(double line) {
        this.line = line;
    }

    public String getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(String isRelation) {
        this.isRelation = isRelation;
    }

    public boolean isTradepwdFlag() {
        return tradepwdFlag;
    }

    public void setTradepwdFlag(boolean tradepwdFlag) {
        this.tradepwdFlag = tradepwdFlag;
    }

    public List<AtlasListModel> getJxsList() {
        return jxsList;
    }

    public void setJxsList(List<AtlasListModel> jxsList) {
        this.jxsList = jxsList;
    }

    public List<AtlasListModel> getFwsList() {
        return fwsList;
    }

    public void setFwsList(List<AtlasListModel> fwsList) {
        this.fwsList = fwsList;
    }

    public List<AtlasListModel> getMgJxsList() {
        return mgJxsList;
    }

    public void setMgJxsList(List<AtlasListModel> mgJxsList) {
        this.mgJxsList = mgJxsList;
    }
}
