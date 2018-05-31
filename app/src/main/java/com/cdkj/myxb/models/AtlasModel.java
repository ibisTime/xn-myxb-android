package com.cdkj.myxb.models;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class AtlasModel {

    private String level;
    private List<AtlasListModel> jxsList;
    private List<AtlasListModel> fwsList;
    private List<AtlasListModel> mgJxsList;

    public List<AtlasListModel> getMgJxsList() {
        return mgJxsList;
    }

    public void setMgJxsList(List<AtlasListModel> mgJxsList) {
        this.mgJxsList = mgJxsList;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<AtlasListModel> getJxsList() {
        return jxsList;
    }

    public void setJxsList(List<AtlasListModel> jxsList) {
        this.jxsList = jxsList;
    }

    public List<AtlasListModel> getFwsList() {
        return fwsList;
    }

    public void setFwsList(List<AtlasListModel> fwsList) {
        this.fwsList = fwsList;
    }
}
