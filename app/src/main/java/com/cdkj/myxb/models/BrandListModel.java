package com.cdkj.myxb.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cdkj on 2018/2/24.
 */

public class BrandListModel implements Parcelable {


    /**
     * code : B201803071815434478321
     * categoryCode : C201805090124597449796
     * brandAdviser : U201805090116434767725
     * name : 韩束
     * slogan : 韩束创立于中国上海，以科技为核心品牌理念。秉持多元、乐观、创新、冒险的企业精神，专为中国新菁英女性量身打造时尚护肤品，释放中国美。
     * advPic : 韩素_1520589054116.jpg
     * description : 韩束
     * contacts : 陈山
     * mobile : 13110992819
     * location : 1
     * orderNo : 1
     * status : 2
     * updater : admin
     * updateDatetime : May 9, 2018 1:33:46 AM
     * remark :
     * realName : 胡莎莎顾问
     */

    private String code;
    private String categoryCode;
    private String brandAdviser;
    private String name;
    private String slogan;
    private String advPic;
    private String description;
    private String contacts;
    private String mobile;
    private String location;
    private int orderNo;
    private String status;
    private String updater;
    private String updateDatetime;
    private String remark;
    private String realName;
    private boolean choose;
    private String saleAmount = "";


    public BrandListModel() {

        super();

    }

    protected BrandListModel(Parcel in) {
        code = in.readString();
        categoryCode = in.readString();
        brandAdviser = in.readString();
        name = in.readString();
        slogan = in.readString();
        advPic = in.readString();
        description = in.readString();
        contacts = in.readString();
        mobile = in.readString();
        location = in.readString();
        orderNo = in.readInt();
        saleAmount = in.readString();
        status = in.readString();
        updater = in.readString();
        updateDatetime = in.readString();
        remark = in.readString();
        realName = in.readString();
        choose = in.readByte() != 0;
    }

    public static final Creator<BrandListModel> CREATOR = new Creator<BrandListModel>() {
        @Override
        public BrandListModel createFromParcel(Parcel in) {
            return new BrandListModel(in);
        }

        @Override
        public BrandListModel[] newArray(int size) {
            return new BrandListModel[size];
        }
    };

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBrandAdviser() {
        return brandAdviser;
    }

    public void setBrandAdviser(String brandAdviser) {
        this.brandAdviser = brandAdviser;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(categoryCode);
        parcel.writeString(brandAdviser);
        parcel.writeString(name);
        parcel.writeString(slogan);
        parcel.writeString(advPic);
        parcel.writeString(description);
        parcel.writeString(contacts);
        parcel.writeString(saleAmount);
        parcel.writeString(mobile);
        parcel.writeString(location);
        parcel.writeInt(orderNo);
        parcel.writeString(status);
        parcel.writeString(updater);
        parcel.writeString(updateDatetime);
        parcel.writeString(remark);
        parcel.writeString(realName);
        parcel.writeByte((byte) (choose ? 1 : 0));
    }
}
