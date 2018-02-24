package com.cdkj.myxb.models;

/**
 * 评论平均分和总数
 * Created by 李先俊 on 2018/2/24.
 */

public class CommentCountAndAverage {


    /**
     * average : 0
     * totalCount : 0
     */

    private float average;
    private int totalCount;

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
