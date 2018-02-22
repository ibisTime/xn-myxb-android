package com.cdkj.myxb.models;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.cdkj.myxb.module.user.UserHelper;

/**
 * 登录类型
 * Created by 李先俊 on 2018/2/22.
 */

public class LoginTypeModel implements IPickerViewData {

    private String typeString;


    private
    @UserHelper.userType
    String type;

    public String getType() {
        return type;
    }

    public void setType(@UserHelper.userType String type) {
        this.type = type;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    @Override
    public String getPickerViewText() {
        return typeString;
    }
}
