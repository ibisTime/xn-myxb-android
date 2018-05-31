package com.cdkj.myxb.models;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class AtlasOuterModel {

    private List<AtlasListModel> mgJxsList;
    private List<AtlasModel> lineList;

    public List<AtlasListModel> getMgJxsList() {
        return mgJxsList;
    }

    public void setMgJxsList(List<AtlasListModel> mgJxsList) {
        this.mgJxsList = mgJxsList;
    }

    public List<AtlasModel> getLineList() {
        return lineList;
    }

    public void setLineList(List<AtlasModel> lineList) {
        this.lineList = lineList;
    }
}
