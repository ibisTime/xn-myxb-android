package com.cdkj.myxb.models;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2018/2/24.
 */

public class OrderListModel {


    /**
     * code : PO201802241635214937378
     * brandCode : B201802101711195833866
     * productCode : P201802241115097415566
     * productName : 354732
     * productPic : aa_1519442110827.jpg
     * productSlogan : 53543
     * unitPrice : 534453
     * quantity : 1
     * amount : 534453
     * status : 0
     * applyUser : U201802221556357869675
     * applyDatetime : Feb 24, 2018 4:35:21 PM
     * applyNote : 4545
     * receiver : 4545
     * reMobile : 13765051712
     * reAddress : 北京市 北京市 昌平区121212
     * realName : 13765051712
     */

    private String code;
    private String brandCode;
    private String productCode;
    private String productName;
    private String productPic;
    private String productSlogan;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal amount;
    private String status;
    private String applyUser;
    private String applyDatetime;
    private String applyNote;
    private String receiver;
    private String reMobile;
    private String reAddress;
    private String realName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductSlogan() {
        return productSlogan;
    }

    public void setProductSlogan(String productSlogan) {
        this.productSlogan = productSlogan;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
