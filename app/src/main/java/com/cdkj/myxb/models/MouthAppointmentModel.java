package com.cdkj.myxb.models;

import com.cdkj.myxb.weight.views.TripDateView;

import java.util.Date;
import java.util.List;

/**
 * Created by cdkj on 2018/2/27.
 */

public class MouthAppointmentModel  {


    private String startDatetime;
    private String endDatetime;




    private List<MouthAppointmentModel> oneDayDateTime;//一天内的相同日期

    public List<MouthAppointmentModel> getOneDayDateTime() {
        return oneDayDateTime;
    }

    public void setOneDayDateTime(List<MouthAppointmentModel> oneDayDateTime) {
        this.oneDayDateTime = oneDayDateTime;
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
