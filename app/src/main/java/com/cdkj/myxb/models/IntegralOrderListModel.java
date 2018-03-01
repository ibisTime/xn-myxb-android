package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by cdkj on 2018/2/23.
 */

public class IntegralOrderListModel {


    /**
     * code : IO201802090921457436557
     * productCode : IP201802090829086058320
     * productName : 产品名称
     * price : 0
     * quantity : 1
     * amount : 2
     * status : 3
     * applyUser : U201802221556357869675
     * applyDatetime : Feb 9, 2018 9:21:45 AM
     * applyNote : 下单备注
     * receiver : 收货人
     * reMobile : 1111111111
     * reAddress : 地址
     * deliverer : 发货人
     * deliveryDatetime : Feb 9, 2018 9:40:57 AM
     * logisticsCode : 物流单
     * logisticsCompany : 物流公司
     * pdf : 物流单
     * realName : 13765051712
     */

    private String code;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal amount;
    private String status;
    private String applyUser;
    private String applyDatetime;
    private String applyNote;
    private String receiver;
    private String reMobile;
    private String reAddress;
    private String deliverer;
    private String deliveryDatetime;
    private String logisticsCode;
    private String logisticsCompany;
    private String pdf;
    private String realName;
    private String productSlogan;

    public String getProductSlogan() {
        return productSlogan;
    }

    public void setProductSlogan(String productSlogan) {
        this.productSlogan = productSlogan;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    private String productPic;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    public String getDeliveryDatetime() {
        return deliveryDatetime;
    }

    public void setDeliveryDatetime(String deliveryDatetime) {
        this.deliveryDatetime = deliveryDatetime;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
