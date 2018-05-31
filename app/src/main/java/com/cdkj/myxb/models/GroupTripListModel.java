package com.cdkj.myxb.models;

/**
 * Created by cdkj on 2018/2/28.
 */

public class GroupTripListModel {


    /**
     * applyDatetime : May 9, 2018 1:49:12 AM
     * applyNote : 你就妮子
     * applyUser : U20180509011717860610
     * appointDatetime : May 31, 2018 1:48:59 AM
     * appointDays : 1
     * approveDatetime : May 9, 2018 2:20:04 AM
     * approveNote :
     * approver : admin
     * clientNumber : 10
     * code : UO201805090149120636930
     * deductAmount : 0
     * isComment : 1
     * mryApproveDatetime : May 12, 2018 9:00:30 PM
     * mryApproveNote : 美容院审核测试
     * mryUser : {"adviser":"U201805090116434767725","companyCode":"CD-CXB000020","createDatetime":"May 9, 2018 1:17:17 AM","gender":"1","introduce":"黔黔","isRelation":"0","kind":"C","level":"bj_service","loginName":"15761663457","loginPwdStrength":"1","maxNumber":10,"mobile":"15761663457","parentAline":0,"parentBline":0,"parentOrderNo":0,"photo":"1_1526023808637.png","realName":"雷黔黔","remark":"","score":0,"signStatus":"2","slogan":"黔黔","status":"0","storeName":"黔黔","systemCode":"CD-CXB000020","tradepwdFlag":false,"updateDatetime":"May 11, 2018 3:30:09 PM","updater":"admin","userId":"U20180509011717860610"}
     * owner : U201805090025148395633
     * planDatetime : May 9, 2018 2:19:58 AM
     * planDays : 2
     * realDatetime : May 7, 2018 5:55:07 PM
     * realDays : 10
     * saleAmount : 1000
     * status : 7
     * sucNumber : 10
     * type : T
     * updateDatetime : May 12, 2018 8:57:03 PM
     * updater : admin
     * user : {"companyCode":"CD-CXB000020","createDatetime":"May 9, 2018 12:25:14 AM","gender":"0","introduce":"服务精英","isRelation":"0","kind":"T","level":"zero_level","location":"1","loginName":"15761663457","loginPwdStrength":"1","maxNumber":0,"mobile":"15761663457","netCreateDatetime":"May 12, 2018 8:04:30 PM","orderNo":1,"parentAUserId":"U201805082312569539663","parentAUserType":"B","parentAline":0,"parentBUserId":"U201805082312569539663","parentBline":0,"parentOrderNo":0,"photo":"ANDROID_1525800296174_3120_4160.jpg","realName":"服务精英001","remark":"","score":0,"signStatus":"2","slogan":"服务精英","speciality":"服务","status":"0","style":"4","systemCode":"CD-CXB000020","tradepwdFlag":false,"updateDatetime":"May 9, 2018 2:23:08 AM","updater":"admin","userId":"U201805090025148395633","userReferee":"U201805082312569539663"}
     */

    private String applyDatetime;
    private String applyNote;
    private String applyUser;
    private String appointDatetime;
    private int appointDays;
    private String approveDatetime;
    private String approveNote;
    private String approver;
    private int clientNumber;
    private String code;
    private int deductAmount;
    private String isComment;
    private String mryApproveDatetime;
    private String mryApproveNote;
    private MryUserBean mryUser;
    private String owner;
    private String planDatetime;
    private int planDays;
    private String realDatetime;
    private int realDays;
    private int saleAmount;
    private String status;
    private int sucNumber;
    private String type;
    private String updateDatetime;
    private String updater;
    private UserBean user;

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

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
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

    public String getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(String approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(int deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getMryApproveDatetime() {
        return mryApproveDatetime;
    }

    public void setMryApproveDatetime(String mryApproveDatetime) {
        this.mryApproveDatetime = mryApproveDatetime;
    }

    public String getMryApproveNote() {
        return mryApproveNote;
    }

    public void setMryApproveNote(String mryApproveNote) {
        this.mryApproveNote = mryApproveNote;
    }

    public MryUserBean getMryUser() {
        return mryUser;
    }

    public void setMryUser(MryUserBean mryUser) {
        this.mryUser = mryUser;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlanDatetime() {
        return planDatetime;
    }

    public void setPlanDatetime(String planDatetime) {
        this.planDatetime = planDatetime;
    }

    public int getPlanDays() {
        return planDays;
    }

    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }

    public String getRealDatetime() {
        return realDatetime;
    }

    public void setRealDatetime(String realDatetime) {
        this.realDatetime = realDatetime;
    }

    public int getRealDays() {
        return realDays;
    }

    public void setRealDays(int realDays) {
        this.realDays = realDays;
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(int saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSucNumber() {
        return sucNumber;
    }

    public void setSucNumber(int sucNumber) {
        this.sucNumber = sucNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class MryUserBean {
        /**
         * adviser : U201805090116434767725
         * companyCode : CD-CXB000020
         * createDatetime : May 9, 2018 1:17:17 AM
         * gender : 1
         * introduce : 黔黔
         * isRelation : 0
         * kind : C
         * level : bj_service
         * loginName : 15761663457
         * loginPwdStrength : 1
         * maxNumber : 10
         * mobile : 15761663457
         * parentAline : 0
         * parentBline : 0
         * parentOrderNo : 0
         * photo : 1_1526023808637.png
         * realName : 雷黔黔
         * remark :
         * score : 0
         * signStatus : 2
         * slogan : 黔黔
         * status : 0
         * storeName : 黔黔
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         * updateDatetime : May 11, 2018 3:30:09 PM
         * updater : admin
         * userId : U20180509011717860610
         */

        private String adviser;
        private String companyCode;
        private String createDatetime;
        private String gender;
        private String introduce;
        private String isRelation;
        private String kind;
        private String level;
        private String loginName;
        private String loginPwdStrength;
        private int maxNumber;
        private String mobile;
        private int parentAline;
        private int parentBline;
        private int parentOrderNo;
        private String photo;
        private String realName;
        private String remark;
        private int score;
        private String signStatus;
        private String slogan;
        private String status;
        private String storeName;
        private String systemCode;
        private boolean tradepwdFlag;
        private String updateDatetime;
        private String updater;
        private String userId;

        public String getAdviser() {
            return adviser;
        }

        public void setAdviser(String adviser) {
            this.adviser = adviser;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
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

        public String getIsRelation() {
            return isRelation;
        }

        public void setIsRelation(String isRelation) {
            this.isRelation = isRelation;
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

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPwdStrength() {
            return loginPwdStrength;
        }

        public void setLoginPwdStrength(String loginPwdStrength) {
            this.loginPwdStrength = loginPwdStrength;
        }

        public int getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getParentAline() {
            return parentAline;
        }

        public void setParentAline(int parentAline) {
            this.parentAline = parentAline;
        }

        public int getParentBline() {
            return parentBline;
        }

        public void setParentBline(int parentBline) {
            this.parentBline = parentBline;
        }

        public int getParentOrderNo() {
            return parentOrderNo;
        }

        public void setParentOrderNo(int parentOrderNo) {
            this.parentOrderNo = parentOrderNo;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
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

        public String getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(String updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class UserBean {
        /**
         * companyCode : CD-CXB000020
         * createDatetime : May 9, 2018 12:25:14 AM
         * gender : 0
         * introduce : 服务精英
         * isRelation : 0
         * kind : T
         * level : zero_level
         * location : 1
         * loginName : 15761663457
         * loginPwdStrength : 1
         * maxNumber : 0
         * mobile : 15761663457
         * netCreateDatetime : May 12, 2018 8:04:30 PM
         * orderNo : 1
         * parentAUserId : U201805082312569539663
         * parentAUserType : B
         * parentAline : 0
         * parentBUserId : U201805082312569539663
         * parentBline : 0
         * parentOrderNo : 0
         * photo : ANDROID_1525800296174_3120_4160.jpg
         * realName : 服务精英001
         * remark :
         * score : 0
         * signStatus : 2
         * slogan : 服务精英
         * speciality : 服务
         * status : 0
         * style : 4
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         * updateDatetime : May 9, 2018 2:23:08 AM
         * updater : admin
         * userId : U201805090025148395633
         * userReferee : U201805082312569539663
         */

        private String companyCode;
        private String createDatetime;
        private String gender;
        private String introduce;
        private String isRelation;
        private String kind;
        private String level;
        private String location;
        private String loginName;
        private String loginPwdStrength;
        private int maxNumber;
        private String mobile;
        private String netCreateDatetime;
        private int orderNo;
        private String parentAUserId;
        private String parentAUserType;
        private int parentAline;
        private String parentBUserId;
        private int parentBline;
        private int parentOrderNo;
        private String photo;
        private String realName;
        private String remark;
        private int score;
        private String signStatus;
        private String slogan;
        private String speciality;
        private String status;
        private String style;
        private String systemCode;
        private boolean tradepwdFlag;
        private String updateDatetime;
        private String updater;
        private String userId;
        private String userReferee;

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
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

        public String getIsRelation() {
            return isRelation;
        }

        public void setIsRelation(String isRelation) {
            this.isRelation = isRelation;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPwdStrength() {
            return loginPwdStrength;
        }

        public void setLoginPwdStrength(String loginPwdStrength) {
            this.loginPwdStrength = loginPwdStrength;
        }

        public int getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNetCreateDatetime() {
            return netCreateDatetime;
        }

        public void setNetCreateDatetime(String netCreateDatetime) {
            this.netCreateDatetime = netCreateDatetime;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getParentAUserId() {
            return parentAUserId;
        }

        public void setParentAUserId(String parentAUserId) {
            this.parentAUserId = parentAUserId;
        }

        public String getParentAUserType() {
            return parentAUserType;
        }

        public void setParentAUserType(String parentAUserType) {
            this.parentAUserType = parentAUserType;
        }

        public int getParentAline() {
            return parentAline;
        }

        public void setParentAline(int parentAline) {
            this.parentAline = parentAline;
        }

        public String getParentBUserId() {
            return parentBUserId;
        }

        public void setParentBUserId(String parentBUserId) {
            this.parentBUserId = parentBUserId;
        }

        public int getParentBline() {
            return parentBline;
        }

        public void setParentBline(int parentBline) {
            this.parentBline = parentBline;
        }

        public int getParentOrderNo() {
            return parentOrderNo;
        }

        public void setParentOrderNo(int parentOrderNo) {
            this.parentOrderNo = parentOrderNo;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
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

        public String getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(String updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }
    }
}
