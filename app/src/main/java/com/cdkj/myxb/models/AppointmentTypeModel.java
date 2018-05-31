package com.cdkj.myxb.models;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * 登录类型
 * Created by cdkj on 2018/2/22.
 */

public class AppointmentTypeModel implements IPickerViewData {

    private String typeString;


    private String type;

    public String getType() {
        return type;
    }

    public void setType( String type) {
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
