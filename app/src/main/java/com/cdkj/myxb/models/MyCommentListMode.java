package com.cdkj.myxb.models;

/**评价列表
 * Created by cdkj on 2018/2/26.
 */

public class MyCommentListMode {


    /**
     * code : C201805091123288724966
     * commentDatetime : May 9, 2018 11:23:28 AM
     * commentUser : {"adviser":"U201805090116434767725","companyCode":"CD-CXB000020","createDatetime":"May 9, 2018 1:17:17 AM","gender":"1","introduce":"黔黔","isRelation":"0","kind":"C","level":"bj_service","loginName":"15761663457","loginPwdStrength":"1","maxNumber":10,"mobile":"15761663457","parentAline":0,"parentBline":0,"parentOrderNo":0,"photo":"1_1526023808637.png","realName":"雷黔黔","remark":"","score":0,"signStatus":"2","slogan":"黔黔","status":"0","storeName":"黔黔","systemCode":"CD-CXB000020","tradepwdFlag":false,"updateDatetime":"May 11, 2018 3:30:09 PM","updater":"admin","userId":"U20180509011717860610"}
     * commenter : U20180509011717860610
     * content : 急急急
     * entityCode : U20180509012117357794
     * orderCode : UO201805090224326052044
     * score : 4
     * status : A
     * type : S
     */

    private String code;
    private String commentDatetime;
    private CommentUserBean commentUser;
    private String commenter;
    private String content;
    private String entityCode;
    private String orderCode;
    private int score;
    private String status;
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(String commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    public CommentUserBean getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CommentUserBean commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class CommentUserBean {
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
}
