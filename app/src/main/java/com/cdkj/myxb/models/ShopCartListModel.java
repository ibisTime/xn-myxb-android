package com.cdkj.myxb.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/5/5.
 */

public class ShopCartListModel implements Serializable{


    /**
     * code : Cart201805081255414584540
     * userId : U201805041340327776390
     * productCode : P201803071838316743012
     * quantity : 1
     * product : {"code":"P201803071838316743012","categoryCode":"001","brandCode":"B201805041348378992498","type":"T","name":"T产品名称","slogan":"广告语","advPic":"2_1520590284069.jpg||3_1520590286892.jpg||防护乳_1520590290752.jpg","pic":"希思黎_1520590278526.jpg","description":"详情描述","price":10000,"soldOutCount":0,"status":"2","updater":"更新人","updateDatetime":"May 4, 2018 3:33:06 PM"}
     */

    private String code;
    private String userId;
    private String productCode;
    private int quantity;
    private boolean isChoice;
    private ProductBean product;

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public static class ProductBean implements Serializable {
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
        private int soldOutCount;
        private String status;
        private String updater;
        private String updateDatetime;

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
