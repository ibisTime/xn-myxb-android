package com.cdkj.myxb.models.event;

import java.io.Serializable;

/**
 * Created by cdkj on 2018/5/24.
 */

public class EventChangeCountModel implements Serializable {

    /**
     * toPayCount : 0
     * toReceiceCount : 0
     * jxsToApproveCount : 0
     * fwToBookCount : 0
     * fwToClassCount : 0
     * fwClassEndCount : 0
     * fwInputCount : 0
     */

    private int toPayCount;
    private int toReceiceCount;

    private int jxsToApproveCount;
    private int fwToBookCount;
    private int fwToClassCount;
    private int fwClassEndCount;
    private int fwInputCount;

    public int getToPayCount() {
        return toPayCount;
    }

    public void setToPayCount(int toPayCount) {
        this.toPayCount = toPayCount;
    }

    public int getToReceiceCount() {
        return toReceiceCount;
    }

    public void setToReceiceCount(int toReceiceCount) {
        this.toReceiceCount = toReceiceCount;
    }

    public int getJxsToApproveCount() {
        return jxsToApproveCount;
    }

    public void setJxsToApproveCount(int jxsToApproveCount) {
        this.jxsToApproveCount = jxsToApproveCount;
    }

    public int getFwToBookCount() {
        return fwToBookCount;
    }

    public void setFwToBookCount(int fwToBookCount) {
        this.fwToBookCount = fwToBookCount;
    }

    public int getFwToClassCount() {
        return fwToClassCount;
    }

    public void setFwToClassCount(int fwToClassCount) {
        this.fwToClassCount = fwToClassCount;
    }

    public int getFwClassEndCount() {
        return fwClassEndCount;
    }

    public void setFwClassEndCount(int fwClassEndCount) {
        this.fwClassEndCount = fwClassEndCount;
    }

    public int getFwInputCount() {
        return fwInputCount;
    }

    public void setFwInputCount(int fwInputCount) {
        this.fwInputCount = fwInputCount;
    }
}