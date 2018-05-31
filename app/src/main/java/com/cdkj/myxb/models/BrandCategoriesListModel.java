package com.cdkj.myxb.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cdkj on 2018/2/24.
 */

public class BrandCategoriesListModel implements Parcelable {


    /**
     * code : B201802101639421256279
     * name : 1640
     * slogan : 1640
     * advPic : aa_1518251981534.jpg
     * contacts : 1640
     * mobile : 18870421309
     * location : 1
     * orderNo : 77
     * status : 2
     * updater : admin
     * updateDatetime : Feb 24, 2018 10:17:29 AM
     * remark :
     */

    private String code;
    private String name;
    private String slogan;
    private String advPic;
    private String contacts;
    private String mobile;
    private String location;
    private int orderNo;
    private String status;
    private String description;
    private String updater;
    private String updateDatetime;
    private String remark;
    private boolean choose;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.slogan);
        dest.writeString(this.advPic);
        dest.writeString(this.contacts);
        dest.writeString(this.mobile);
        dest.writeString(this.location);
        dest.writeInt(this.orderNo);
        dest.writeString(this.status);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.remark);
    }

    public BrandCategoriesListModel() {
    }

    protected BrandCategoriesListModel(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.slogan = in.readString();
        this.advPic = in.readString();
        this.contacts = in.readString();
        this.mobile = in.readString();
        this.location = in.readString();
        this.orderNo = in.readInt();
        this.status = in.readString();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<BrandCategoriesListModel> CREATOR = new Creator<BrandCategoriesListModel>() {
        @Override
        public BrandCategoriesListModel createFromParcel(Parcel source) {
            return new BrandCategoriesListModel(source);
        }

        @Override
        public BrandCategoriesListModel[] newArray(int size) {
            return new BrandCategoriesListModel[size];
        }
    };
}
