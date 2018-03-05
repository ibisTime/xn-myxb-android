package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.myxb.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 行程日历
 * Created by cdkj on 2017/10/12.
 */

public class TripDateAdapter extends BaseQuickAdapter<Date, BaseViewHolder> {

    private List<Date> compareData;

    public TripDateAdapter(@Nullable List<Date> data) {
        super(R.layout.item_trip_date, data);
        compareData = new ArrayList<>();
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, Date item) {
        if (item == null) {
            viewHolder.setText(R.id.tv_data_sign, "");
            viewHolder.setBackgroundColor(R.id.tv_data_sign, ContextCompat.getColor(mContext, R.color.white));
            return;
        }
        viewHolder.setText(R.id.tv_data_sign, DateUtil.format(item, "dd"));

        if (isHaveSameDay(item)) {    //判断是否相同 如果相同说明有预约
            viewHolder.setBackgroundRes(R.id.tv_data_sign, R.drawable.trip_date_select);
            viewHolder.setTextColor(R.id.tv_data_sign, ContextCompat.getColor(mContext, R.color.white));

        } else {
            viewHolder.setBackgroundRes(R.id.tv_data_sign, 0);
            viewHolder.setTextColor(R.id.tv_data_sign, ContextCompat.getColor(mContext, R.color.app_txt_black));
        }

    }

    /**
     * 判断是否有相同日期 如果有则说明有预约
     * @param item
     * @return
     */
    public boolean isHaveSameDay(Date item) {

        for (Date compareDatum : compareData) {
            if (compareDatum == null)
                continue;
            if (DateUtil.inSameDay(item, compareDatum)) {
                return true;
            }
        }

        return false;

    }

    public List<Date> getCompareData() {
        return compareData;
    }

    public void setCompareData(List<Date> data) {

        if (data == null ) {
            return;
        }

        compareData.clear();
        compareData.addAll(data);

        notifyDataSetChanged();
    }


}
