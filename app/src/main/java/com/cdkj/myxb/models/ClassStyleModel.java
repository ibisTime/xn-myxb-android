package com.cdkj.myxb.models;

import android.text.TextUtils;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * 上课风格
 * Created by cd kjon k2018/2/28.
 */

public class ClassStyleModel implements IPickerViewData {


    /**
     * id : 68
     * type : 1
     * parentKey : style
     * dkey : 1
     * dvalue : 幽默
     * updater : admin
     * updateDatetime : Feb 7, 2018 3:59:12 PM
     * systemCode : CD-CXB000020
     */

    private int id;
    private String type;
    private String parentKey;
    private String dkey;
    private String dvalue;

    private boolean isClear; //是否清楚之前的选择

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }

    public String getDvalue() {
        return dvalue;
    }

    public void setDvalue(String dvalue) {
        this.dvalue = dvalue;
    }

    @Override
    public String getPickerViewText() {
        return dvalue;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        ClassStyleModel cmodel = (ClassStyleModel) obj;

        if (this.id==cmodel.id) {
            return true;
        }

        return false;
    }
}
