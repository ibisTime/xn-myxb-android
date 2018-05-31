package com.cdkj.myxb.models;

/**评价列表
 * Created by cdkj on 2018/2/26.
 */

public class CommentListMode {


    /**
     * code : C201805142115251531900
     * type : S
     * orderCode : UO201805142113147148840
     * entityCode : U201805141732384602851
     * score : 3
     * content : 特意
     * status : A
     * commenter : U201805142013010271683
     * commentDatetime : May 14, 2018 9:15:25 PM
     * entityName : 销售-严
     * commentUser : {"userId":"U201805142013010271683","loginName":"18857111122","mobile":"18857111122","photo":"ANDROID_1526303553850_1080_1920.jpg","nickname":"10271683","loginPwdStrength":"1","kind":"C","level":"zero_level","userReferee":"U201805141552342182529","signStatus":"2","maxNumber":0,"status":"0","pdf":"EC803ABC-5558-4492-8BAC-231A16FFB1C7_1526300002000.jpeg","score":0,"createDatetime":"May 14, 2018 8:13:01 PM","updateDatetime":"May 14, 2018 8:21:17 PM","parentBline":0,"parentAline":0,"parentOrderNo":0,"companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     */

    private String code;
    private String type;
    private String orderCode;
    private String entityCode;
    private int score;
    private String content;
    private String status;
    private String commenter;
    private String commentDatetime;
    private String entityName;
    private CommentUserBean commentUser;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(String commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public CommentUserBean getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CommentUserBean commentUser) {
        this.commentUser = commentUser;
    }

    public static class CommentUserBean {
        /**
         * userId : U201805142013010271683
         * loginName : 18857111122
         * mobile : 18857111122
         * photo : ANDROID_1526303553850_1080_1920.jpg
         * nickname : 10271683
         * loginPwdStrength : 1
         * kind : C
         * level : zero_level
         * userReferee : U201805141552342182529
         * signStatus : 2
         * maxNumber : 0
         * status : 0
         * pdf : EC803ABC-5558-4492-8BAC-231A16FFB1C7_1526300002000.jpeg
         * score : 0
         * createDatetime : May 14, 2018 8:13:01 PM
         * updateDatetime : May 14, 2018 8:21:17 PM
         * parentBline : 0
         * parentAline : 0
         * parentOrderNo : 0
         * companyCode : CD-CXB000020
         * systemCode : CD-CXB000020
         * tradepwdFlag : false
         */

        private String userId;
        private String loginName;
        private String mobile;
        private String photo;
        private String nickname;
        private String realName;
        private String loginPwdStrength;
        private String kind;
        private String level;
        private String userReferee;
        private String signStatus;
        private int maxNumber;
        private String status;
        private String pdf;
        private int score;
        private String createDatetime;
        private String updateDatetime;
        private int parentBline;
        private int parentAline;
        private int parentOrderNo;
        private String companyCode;
        private String systemCode;
        private boolean tradepwdFlag;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
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

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
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

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
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
}
