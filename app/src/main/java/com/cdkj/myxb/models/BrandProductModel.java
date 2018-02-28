package com.cdkj.myxb.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2018/2/24.
 */

public class BrandProductModel implements Parcelable {


    /**
     * code : P201802241115097415566
     * brandCode : B201802101711195833866
     * name : 354732
     * slogan : 53543
     * advPic : aa_1519442110827.jpg
     * pic : aa_1519442107307.jpg
     * description : 543453
     * price : 534453
     * location : 0
     * status : 2
     * updater : admin
     * updateDatetime : Feb 24, 2018 11:15:17 AM
     * remark :
     */

    private String code;
    private String brandCode;
    private String name;
    private String slogan;
    private String advPic;
    private String pic;
    private String description;
    private BigDecimal price;
    private String location;
    private String status;
    private String updater;
    private String updateDatetime;
    private String remark;
    private int soldOutCount;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSoldOutCount() {
        return soldOutCount;
    }

    public void setSoldOutCount(int soldOutCount) {
        this.soldOutCount = soldOutCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public BrandProductModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.brandCode);
        dest.writeString(this.name);
        dest.writeString(this.slogan);
        dest.writeString(this.advPic);
        dest.writeString(this.pic);
        dest.writeString(this.description);
        dest.writeSerializable(this.price);
        dest.writeString(this.location);
        dest.writeString(this.status);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.remark);
        dest.writeInt(this.soldOutCount);
        dest.writeString(this.mobile);
    }

    protected BrandProductModel(Parcel in) {
        this.code = in.readString();
        this.brandCode = in.readString();
        this.name = in.readString();
        this.slogan = in.readString();
        this.advPic = in.readString();
        this.pic = in.readString();
        this.description = in.readString();
        this.price = (BigDecimal) in.readSerializable();
        this.location = in.readString();
        this.status = in.readString();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.remark = in.readString();
        this.soldOutCount = in.readInt();
        this.mobile = in.readString();
    }

    public static final Creator<BrandProductModel> CREATOR = new Creator<BrandProductModel>() {
        @Override
        public BrandProductModel createFromParcel(Parcel source) {
            return new BrandProductModel(source);
        }

        @Override
        public BrandProductModel[] newArray(int size) {
            return new BrandProductModel[size];
        }
    };
}
