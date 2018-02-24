package com.cdkj.myxb.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2018/2/22.
 */

public class UserModel implements Parcelable {
    /**
     * userId : U201802221556357869675
     * loginName : 13765051712
     * mobile : 13765051712
     * photo :
     * nickname : 57869675
     * loginPwdStrength : 1
     * kind : T
     * level : 1
     * speciality : 13765051712
     * style : 13765051712
     * realName : 13765051712
     * status : 0
     * score : 0.0
     * createDatetime : Feb 22, 2018 3:56:35 PM
     * updater : admin
     * updateDatetime : Feb 22, 2018 3:56:35 PM
     * remark :
     * companyCode : CD-CXB000020
     * systemCode : CD-CXB000020
     * tradepwdFlag : false
     */

    private String userId;
    private String loginName;
    private String mobile;
    private String photo;
    private String nickname;
    private String loginPwdStrength;
    private String kind;
    private String level;
    private String speciality;
    private String style;
    private String realName;
    private String status;
    private BigDecimal score;
    private String createDatetime;
    private String updater;
    private String updateDatetime;
    private String remark;
    private String companyCode;
    private String systemCode;
    private String gender;
    private boolean tradepwdFlag;

    public String getGender() {
        return gender;
    }

    /**
     *
     * @return
     */
    public boolean isMan() {
        return TextUtils.equals(getGender(), "1");
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginPwdStrength() {
        return loginPwdStrength;
    }

    public void setLoginPwdStrength(String loginPwdStrength) {
        this.loginPwdStrength = loginPwdStrength;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public boolean isTradepwdFlag() {
        return tradepwdFlag;
    }

    public void setTradepwdFlag(boolean tradepwdFlag) {
        this.tradepwdFlag = tradepwdFlag;
    }

    public UserModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.loginName);
        dest.writeString(this.mobile);
        dest.writeString(this.photo);
        dest.writeString(this.nickname);
        dest.writeString(this.loginPwdStrength);
        dest.writeString(this.kind);
        dest.writeString(this.level);
        dest.writeString(this.speciality);
        dest.writeString(this.style);
        dest.writeString(this.realName);
        dest.writeString(this.status);
        dest.writeSerializable(this.score);
        dest.writeString(this.createDatetime);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.remark);
        dest.writeString(this.companyCode);
        dest.writeString(this.systemCode);
        dest.writeByte(this.tradepwdFlag ? (byte) 1 : (byte) 0);
    }

    protected UserModel(Parcel in) {
        this.userId = in.readString();
        this.loginName = in.readString();
        this.mobile = in.readString();
        this.photo = in.readString();
        this.nickname = in.readString();
        this.loginPwdStrength = in.readString();
        this.kind = in.readString();
        this.level = in.readString();
        this.speciality = in.readString();
        this.style = in.readString();
        this.realName = in.readString();
        this.status = in.readString();
        this.score = (BigDecimal) in.readSerializable();
        this.createDatetime = in.readString();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.remark = in.readString();
        this.companyCode = in.readString();
        this.systemCode = in.readString();
        this.tradepwdFlag = in.readByte() != 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
