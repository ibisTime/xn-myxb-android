package com.cdkj.myxb.models;

import com.cdkj.myxb.weight.views.TripDateView;

import java.util.Date;
import java.util.List;

/**
 * Created by 李先俊 on 2018/2/27.
 */

public class MouthAppointmentModel  {


    private String startDatetime;
    private String endDatetime;


    private Date date;
    private boolean isSame;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private List<MouthAppointmentModel> oneDayDateTime;//一天内的相同日期

    public List<MouthAppointmentModel> getOneDayDateTime() {
        return oneDayDateTime;
    }

    public void setOneDayDateTime(List<MouthAppointmentModel> oneDayDateTime) {
        this.oneDayDateTime = oneDayDateTime;
    }

    public boolean isSame() {
        return isSame;
    }

    public void setSame(boolean same) {
        isSame = same;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }



}
