package com.cdkj.myxb.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentListModel implements Serializable {


    /**
     * code : UO201805131718070384953
     * type : S
     * owner : U20180509012117357794
     * appointDatetime : May 13, 2018 5:17:54 PM
     * appointDays : 25
     * planDatetime : May 13, 2018 5:22:06 PM
     * planDays : 25
     * realDays : 25
     * clientNumber : 25
     * sucNumber : 25
     * saleAmount : 1000000
     * status : 7
     * applyUser : U20180509011717860610
     * applyDatetime : May 13, 2018 5:18:07 PM
     * applyNote : yuyuetianshu
     * mryApproveDatetime : May 13, 2018 5:23:28 PM
     * mryApproveNote :
     * deductAmount : 40000
     * approver : admin
     * approveDatetime : May 13, 2018 5:22:08 PM
     * approveNote :
     * updater : U20180509012117357794
     * updateDatetime : May 13, 2018 5:22:39 PM
     * isComment : 1
     * mryUser : {"userId":"U20180509011717860610","loginName":"15761663457","mobile":"15761663457","photo":"1_1526023808637.png","loginPwdStrength":"1","kind":"C","level":"bj_service","adviser":"U201805090116434767725","storeName":"黔黔","realName":"雷黔黔","signStatus":"2","maxNumber":10,"status":"0","gender":"1","slogan":"黔黔","introduce":"黔黔","score":0,"createDatetime":"May 9, 2018 1:17:17 AM","updater":"admin","updateDatetime":"May 11, 2018 3:30:09 PM","remark":"","parentBline":0,"parentAline":0,"isRelation":"0","parentOrderNo":0,"companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     * user : {"userId":"U20180509012117357794","loginName":"18984955240","mobile":"18984955240","photo":"d287448e6d5113a7583d01e8cc6474a6_1526199449591.gif","loginPwdStrength":"1","kind":"S","level":"sx_spec","speciality":"信息","style":"1,2","handler":"U201805090116265073097","location":"1","orderNo":1,"realName":"黔黔销售","signStatus":"2","maxNumber":0,"status":"0","gender":"1","slogan":"黔黔销售","introduce":"黔黔销售","score":0,"createDatetime":"May 9, 2018 1:21:17 AM","updater":"admin","updateDatetime":"May 13, 2018 4:17:55 PM","parentBline":0,"parentAline":0,"isRelation":"0","parentOrderNo":0,"companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     * detailList : [{"id":"3","orderCode":"UO201805131718070384953","brandCode":"B201803071815434478321","amount":1000000,"brand":{"name":"韩束"}}]
     */

    private String code;
    private String type;
    private String owner;
    private String realDatetime;
    private String appointDatetime;
    private int appointDays;
    private String planDatetime;
    private int planDays;
    private int realDays;
    private int clientNumber;
    private int sucNumber;
    private BigDecimal saleAmount;
    private String status;
    private String applyUser;
    private String applyDatetime;
    private String applyNote;
    private String pdf;
    private String mryApproveDatetime;
    private String mryApproveNote;
    private BigDecimal deductAmount;
    private String approver;
    private String approveDatetime;
    private String approveNote;
    private String updater;
    private String updateDatetime;
    private String isComment;
    private MryUserBean mryUser;
    private UserBean user;
    private List<DetailListBean> detailList;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getRealDatetime() {
        return realDatetime;
    }

    public void setRealDatetime(String realDatetime) {
        this.realDatetime = realDatetime;
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

    public int getRealDays() {
        return realDays;
    }

    public void setRealDays(int realDays) {
        this.realDays = realDays;
    }

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

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
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

    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
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

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public MryUserBean getMryUser() {
        return mryUser;
    }

    public void setMryUser(MryUserBean mryUser) {
        this.mryUser = mryUser;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class MryUserBean implements Serializable {
        /**
         * userId : U20180509011717860610
         * loginName : 15761663457
         * mobile : 15761663457
         * photo : 1_1526023808637.png
         * loginPwdStrength : 1
         * kind : C
         * level : bj_service
         * adviser : U201805090116434767725
         * storeName : 黔黔
         * realName : 雷黔黔
         * signStatus : 2
         * maxNumber : 10
         * status : 0
         * gender : 1
         * slogan : 黔黔
         * introduce : 黔黔
         * score : 0
         * createDatetime : May 9, 2018 1:17:17 AM
         * updater : admin
         * updateDatetime : May 11, 2018 3:30:09 PM
         * remark :
         * parentBline : 0
         * parentAline : 0
         * isRelation : 0
         * parentOrderNo : 0
         * companyCode : CD-CXB000020
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         */

        private String userId;
        private String loginName;
        private String mobile;
        private String photo;
        private String loginPwdStrength;
        private String kind;
        private String level;
        private String adviser;
        private String storeName;
        private String realName;
        private String signStatus;
        private int maxNumber;
        private String status;
        private String gender;
        private String slogan;
        private String introduce;
        private int score;
        private String createDatetime;
        private String updater;
        private String updateDatetime;
        private String remark;
        private int parentBline;
        private int parentAline;
        private String isRelation;
        private int parentOrderNo;
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

        public String getAdviser() {
            return adviser;
        }

        public void setAdviser(String adviser) {
            this.adviser = adviser;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public int getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
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

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
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

        public int getParentBline() {
            return parentBline;
        }

        public void setParentBline(int parentBline) {
            this.parentBline = parentBline;
        }

        public int getParentAline() {
            return parentAline;
        }

        public void setParentAline(int parentAline) {
            this.parentAline = parentAline;
        }

        public String getIsRelation() {
            return isRelation;
        }

        public void setIsRelation(String isRelation) {
            this.isRelation = isRelation;
        }

        public int getParentOrderNo() {
            return parentOrderNo;
        }

        public void setParentOrderNo(int parentOrderNo) {
            this.parentOrderNo = parentOrderNo;
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

    public static class UserBean implements Serializable {
        /**
         * userId : U20180509012117357794
         * loginName : 18984955240
         * mobile : 18984955240
         * photo : d287448e6d5113a7583d01e8cc6474a6_1526199449591.gif
         * loginPwdStrength : 1
         * kind : S
         * level : sx_spec
         * speciality : 信息
         * style : 1,2
         * handler : U201805090116265073097
         * location : 1
         * orderNo : 1
         * realName : 黔黔销售
         * signStatus : 2
         * maxNumber : 0
         * status : 0
         * gender : 1
         * slogan : 黔黔销售
         * introduce : 黔黔销售
         * score : 0
         * createDatetime : May 9, 2018 1:21:17 AM
         * updater : admin
         * updateDatetime : May 13, 2018 4:17:55 PM
         * parentBline : 0
         * parentAline : 0
         * isRelation : 0
         * parentOrderNo : 0
         * companyCode : CD-CXB000020
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         */

        private String userId;
        private String loginName;
        private String mobile;
        private String photo;
        private String loginPwdStrength;
        private String kind;
        private String level;
        private String speciality;
        private String style;
        private String handler;
        private String location;
        private int orderNo;
        private String realName;
        private String signStatus;
        private int maxNumber;
        private String status;
        private String gender;
        private String slogan;
        private String introduce;
        private int score;
        private String createDatetime;
        private String updater;
        private String updateDatetime;
        private int parentBline;
        private int parentAline;
        private String isRelation;
        private int parentOrderNo;
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

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public int getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
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

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
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

        public int getParentBline() {
            return parentBline;
        }

        public void setParentBline(int parentBline) {
            this.parentBline = parentBline;
        }

        public int getParentAline() {
            return parentAline;
        }

        public void setParentAline(int parentAline) {
            this.parentAline = parentAline;
        }

        public String getIsRelation() {
            return isRelation;
        }

        public void setIsRelation(String isRelation) {
            this.isRelation = isRelation;
        }

        public int getParentOrderNo() {
            return parentOrderNo;
        }

        public void setParentOrderNo(int parentOrderNo) {
            this.parentOrderNo = parentOrderNo;
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

    public static class DetailListBean implements Serializable {
        /**
         * id : 3
         * orderCode : UO201805131718070384953
         * brandCode : B201803071815434478321
         * amount : 1000000
         * brand : {"name":"韩束"}
         */

        private String id;
        private String orderCode;
        private String brandCode;
        private int amount;
        private BrandBean brand;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getBrandCode() {
            return brandCode;
        }

        public void setBrandCode(String brandCode) {
            this.brandCode = brandCode;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public static class BrandBean implements Serializable {
            /**
             * name : 韩束
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
