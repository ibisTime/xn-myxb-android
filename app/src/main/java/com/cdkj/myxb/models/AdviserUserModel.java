package com.cdkj.myxb.models;

import android.os.Parcel;
import android.os.Parcelable;

/**团队顾问
 * Created by 李先俊 on 2018/2/28.
 */

public class AdviserUserModel implements Parcelable {


    /**
     * userId : U201802261046156649849
     * loginName : 1045
     * mobile : 18870420006
     * photo : 资信中心借款人icon-圆角_1519613163746.png
     * loginPwdStrength : 1
     * kind : A
     * level : 1
     * mainBrand : B201802081729499985153
     * realName : 1045
     * roleCode : HWSR201700000000000001
     * status : 0
     * gender : 0
     * introduce : <p>104699999999999999</p>
     * score : 0
     * createDatetime : Feb 26, 2018 10:46:15 AM
     * updater : admin
     * updateDatetime : Feb 26, 2018 10:55:08 AM
     * remark : 1046
     * companyCode : CD-CXB000020
     * systemCode : CD-CXB000020
     * tradepwdFlag : false
     */
//
//    private String userId;
//    private String loginName;
    private String mobile;
//    private String photo;
//    private String loginPwdStrength;
//    private String kind;
//    private String level;
//    private String mainBrand;
//    private String realName;
//    private String roleCode;
//    private String status;
//    private String gender;
//    private String introduce;
//    private int score;
//    private String createDatetime;
//    private String updater;
//    private String updateDatetime;
//    private String remark;
//    private String companyCode;
//    private String systemCode;
//    private boolean tradepwdFlag;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobile);
    }

    public AdviserUserModel() {
    }

    protected AdviserUserModel(Parcel in) {
        this.mobile = in.readString();
    }

    public static final Parcelable.Creator<AdviserUserModel> CREATOR = new Parcelable.Creator<AdviserUserModel>() {
        @Override
        public AdviserUserModel createFromParcel(Parcel source) {
            return new AdviserUserModel(source);
        }

        @Override
        public AdviserUserModel[] newArray(int size) {
            return new AdviserUserModel[size];
        }
    };
}
