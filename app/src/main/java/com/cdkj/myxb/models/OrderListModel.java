package com.cdkj.myxb.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cdkj on 2018/2/24.
 */

public class OrderListModel {


    /**
     * code : O20180508140426991417
     * tamount : 10000
     * camount : 0
     * totalAmount : 10000
     * amount : 10000
     * status : 1
     * applyUser : U201805041340327776390
     * applyDatetime : May 8, 2018 2:04:26 PM
     * applyNote :
     * receiver : 雷黔
     * reMobile : 18984955240
     * reAddress : 贵州省 遵义市 红花岗区详细地址
     * payType : 1
     * payAmount1 : 10000
     * payAmount2 : 0
     * productOrderList : [{"code":"PO20180508140426994703","orderCode":"O20180508140426991417","categoryCode":"001","brandCode":"B201805041348378992498","productCode":"P201803071838316743012","price":10000,"product":{"code":"P201803071838316743012","categoryCode":"001","brandCode":"B201805041348378992498","type":"T","name":"T产品名称","slogan":"广告语","advPic":"2_1520590284069.jpg||3_1520590286892.jpg||防护乳_1520590290752.jpg","pic":"希思黎_1520590278526.jpg","description":"详情描述","price":10000,"soldOutCount":0,"status":"2","updater":"更新人","updateDatetime":"May 4, 2018 3:33:06 PM"},"quantity":1}]
     * user : {"userId":"U201805041340327776390","loginName":"18984955240","mobile":"18984955240","photo":"1_1525412430879.png","nickname":"27776390","loginPwdStrength":"1","kind":"T","level":"1","speciality":"信息","style":"1","realName":"雷","signStatus":"1","maxNumber":0,"status":"0","gender":"1","slogan":"信息","introduce":"信息","score":0,"createDatetime":"May 4, 2018 1:40:32 PM","updater":"admin","updateDatetime":"May 4, 2018 1:40:32 PM","remark":"","companyCode":"CD-CXB000020","systemCode":"CD-CXB000020","tradepwdFlag":false}
     */

    private String code;
    private int tamount;
    private int camount;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private String status;
    private String applyUser;
    private String applyDatetime;
    private String applyNote;
    private String receiver;
    private String reMobile;
    private String reAddress;
    private String payType;
    private int payAmount1;
    private int payAmount2;
    private UserBean user;
    private String logisiticsCode;
    private String logisiticsCompany;
    private List<ProductOrderListBean> productOrderList;

    public String getLogisiticsCompany() {
        return logisiticsCompany;
    }

    public void setLogisiticsCompany(String logisiticsCompany) {
        this.logisiticsCompany = logisiticsCompany;
    }

    public String getLogisiticsCode() {
        return logisiticsCode;
    }

    public void setLogisiticsCode(String logisiticsCode) {
        this.logisiticsCode = logisiticsCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTamount() {
        return tamount;
    }

    public void setTamount(int tamount) {
        this.tamount = tamount;
    }

    public int getCamount() {
        return camount;
    }

    public void setCamount(int camount) {
        this.camount = camount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReMobile() {
        return reMobile;
    }

    public void setReMobile(String reMobile) {
        this.reMobile = reMobile;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getPayAmount1() {
        return payAmount1;
    }

    public void setPayAmount1(int payAmount1) {
        this.payAmount1 = payAmount1;
    }

    public int getPayAmount2() {
        return payAmount2;
    }

    public void setPayAmount2(int payAmount2) {
        this.payAmount2 = payAmount2;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<ProductOrderListBean> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrderListBean> productOrderList) {
        this.productOrderList = productOrderList;
    }

    public static class UserBean {
        /**
         * userId : U201805041340327776390
         * loginName : 18984955240
         * mobile : 18984955240
         * photo : 1_1525412430879.png
         * nickname : 27776390
         * loginPwdStrength : 1
         * kind : T
         * level : 1
         * speciality : 信息
         * style : 1
         * realName : 雷
         * signStatus : 1
         * maxNumber : 0
         * status : 0
         * gender : 1
         * slogan : 信息
         * introduce : 信息
         * score : 0
         * createDatetime : May 4, 2018 1:40:32 PM
         * updater : admin
         * updateDatetime : May 4, 2018 1:40:32 PM
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

    public static class ProductOrderListBean {
        /**
         * code : PO20180508140426994703
         * orderCode : O20180508140426991417
         * categoryCode : 001
         * brandCode : B201805041348378992498
         * productCode : P201803071838316743012
         * price : 10000
         * product : {"code":"P201803071838316743012","categoryCode":"001","brandCode":"B201805041348378992498","type":"T","name":"T产品名称","slogan":"广告语","advPic":"2_1520590284069.jpg||3_1520590286892.jpg||防护乳_1520590290752.jpg","pic":"希思黎_1520590278526.jpg","description":"详情描述","price":10000,"soldOutCount":0,"status":"2","updater":"更新人","updateDatetime":"May 4, 2018 3:33:06 PM"}
         * quantity : 1
         */

        private String code;
        private String orderCode;
        private String categoryCode;
        private String brandCode;
        private String productCode;
        private BigDecimal price;
        private ProductBean product;
        private int quantity;
        private Double discountRate = 0.0;
        private BigDecimal discountPrice;

        public BigDecimal getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(BigDecimal discountPrice) {
            this.discountPrice = discountPrice;
        }

        public Double getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(Double discountRate) {
            this.discountRate = discountRate;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getBrandCode() {
            return brandCode;
        }

        public void setBrandCode(String brandCode) {
            this.brandCode = brandCode;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public static class ProductBean {
            /**
             * code : P201803071838316743012
             * categoryCode : 001
             * brandCode : B201805041348378992498
             * type : T
             * name : T产品名称
             * slogan : 广告语
             * advPic : 2_1520590284069.jpg||3_1520590286892.jpg||防护乳_1520590290752.jpg
             * pic : 希思黎_1520590278526.jpg
             * description : 详情描述
             * price : 10000
             * soldOutCount : 0
             * status : 2
             * updater : 更新人
             * updateDatetime : May 4, 2018 3:33:06 PM
             */

            private String code;
            private String categoryCode;
            private String brandCode;
            private String type;
            private String name;
            private String slogan;
            private String advPic;
            private String pic;
            private String description;
            private BigDecimal price;
            private BigDecimal discountPrice;
            private int soldOutCount;
            private String status;
            private String updater;
            private String updateDatetime;

            public BigDecimal getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(BigDecimal discountPrice) {
                this.discountPrice = discountPrice;
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

            public String getBrandCode() {
                return brandCode;
            }

            public void setBrandCode(String brandCode) {
                this.brandCode = brandCode;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public int getSoldOutCount() {
                return soldOutCount;
            }

            public void setSoldOutCount(int soldOutCount) {
                this.soldOutCount = soldOutCount;
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
        }
    }
}
