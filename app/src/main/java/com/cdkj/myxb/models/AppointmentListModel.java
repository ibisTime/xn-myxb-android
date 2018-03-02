package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentListModel {

    /**
     * code : UO201802261647301335017
     * type : T
     * owner : U201802261030202667416
     * appointDatetime : Feb 26, 2018 8:47:26 AM
     * appointDays : 1
     * status : 1
     * applyUser : U201802261329407129715
     * applyDatetime : Feb 26, 2018 4:47:30 PM
     * applyNote : 232323
     * isComment : 0
     * mryUser : {"userId":"U201802261329407129715","loginName":"13765051712","mobile":"13765051712","nickname":"07129715","loginPwdStrength":"1","kind":"C","level":"1","adviser":"U201802261046156649849","storeName":"13765051712","realName":"13765051712","status":"0","gender":"1","introduce":"<p>13765051712<\/p><p><br><\/p>","score":0,"createDatetime":"Feb 26, 2018 1:29:40 PM","updater":"admin","updateDatetime":"Feb 26, 2018 1:29:40 PM","remark":"","companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     * user : {"userId":"U201802261030202667416","loginName":"1029","mobile":"18870420003","photo":"资信中心借款人icon-圆角_1519612211969.png","nickname":"02667416","loginPwdStrength":"1","kind":"T","level":"1","speciality":"1029","style":"1029","realName":"1029","status":"0","gender":"1","introduce":"<p>1029<\/p>","score":0,"createDatetime":"Feb 26, 2018 10:30:20 AM","updater":"admin","updateDatetime":"Feb 26, 2018 11:31:38 AM","remark":"","companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     */

    private String code;
    private String type;



    public String getRealDatetime() {
        return realDatetime;
    }

    public void setRealDatetime(String realDatetime) {
        this.realDatetime = realDatetime;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    private String owner;
    private String appointDatetime;
    private int appointDays;
    private String status;
    private String applyUser;
    private String applyDatetime;
    private String applyNote;
    private String isComment;
    private String planDatetime;
    private String planDays;
    private int clientNumber; //见客户量
    private int sucNumber;  //成交数
    private int realDays;  //实际天数
    private String realDatetime;  //实际上门时间
    private BigDecimal saleAmount;  //销售额
    private MryUserBean mryUser;
    private UserBean user;

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getSucNumber() {
        return sucNumber;
    }

    public void setSucNumber(int sucNumber) {
        this.sucNumber = sucNumber;
    }

    public int getRealDays() {
        return realDays;
    }

    public void setRealDays(int realDays) {
        this.realDays = realDays;
    }

    public MryUserBean getMryUser() {
        return mryUser;
    }

    public void setMryUser(MryUserBean mryUser) {
        this.mryUser = mryUser;
    }

    public String getPlanDatetime() {
        return planDatetime;
    }

    public void setPlanDatetime(String planDatetime) {
        this.planDatetime = planDatetime;
    }

    public String getPlanDays() {
        return planDays;
    }

    public void setPlanDays(String planDays) {
        this.planDays = planDays;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAppointDatetime() {
        return appointDatetime;
    }

    public void setAppointDatetime(String appointDatetime) {
        this.appointDatetime = appointDatetime;
    }

    public int getAppointDays() {
        return appointDays;
    }

    public void setAppointDays(int appointDays) {
        this.appointDays = appointDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getApplyNote() {
        return applyNote;
    }

    public void setApplyNote(String applyNote) {
        this.applyNote = applyNote;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

//    public MryUserBean getMryUser() {
//        return mryUser;
//    }
//
//    public void setMryUser(MryUserBean mryUser) {
//        this.mryUser = mryUser;
//    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class MryUserBean {
        /**
         * userId : U201802261329407129715
         * loginName : 13765051712
         * mobile : 13765051712
         * nickname : 07129715
         * loginPwdStrength : 1
         * kind : C
         * level : 1
         * adviser : U201802261046156649849
         * storeName : 13765051712
         * realName : 13765051712
         * status : 0
         * gender : 1
         * introduce : <p>13765051712</p><p><br></p>
         * score : 0
         * createDatetime : Feb 26, 2018 1:29:40 PM
         * updater : admin
         * updateDatetime : Feb 26, 2018 1:29:40 PM
         * remark :
         * companyCode : CD-CXB000020
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         */

//        private String userId;
//        private String loginName;
//        private String mobile;
//        private String nickname;
//        private String loginPwdStrength;
//        private String kind;
//        private String level;
//        private String adviser;
//        private String storeName;
        private String realName;
//        private String status;
//        private String gender;
//        private String introduce;
//        private int score;
//        private String createDatetime;
//        private String updater;
//        private String updateDatetime;
//        private String remark;
//        private String companyCode;
//        private String systemCode;
        private String photo;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class UserBean {
        /**
         * userId : U201802261030202667416
         * loginName : 1029
         * mobile : 18870420003
         * photo : 资信中心借款人icon-圆角_1519612211969.png
         * nickname : 02667416
         * loginPwdStrength : 1
         * kind : T
         * level : 1
         * speciality : 1029
         * style : 1029
         * realName : 1029
         * status : 0
         * gender : 1
         * introduce : <p>1029</p>
         * score : 0
         * createDatetime : Feb 26, 2018 10:30:20 AM
         * updater : admin
         * updateDatetime : Feb 26, 2018 11:31:38 AM
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
        private String gender;
        private String introduce;
        private int score;
        private String createDatetime;
        private String updater;
        private String updateDatetime;
        private String remark;
        private String companyCode;
        private String systemCode;
        private boolean tradepwdFlag;

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
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
    }
}
