package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.TripDateModel;
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

    private List<TripDateModel> compareData;

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

        TextView tvData = viewHolder.getView(R.id.tv_data_sign);
        isHaveSameDay(tvData, item);

//        if (isHaveSameDay(item)) {    //判断是否相同 如果相同说明有预约
//            viewHolder.setBackgroundRes(R.id.tv_data_sign, R.drawable.trip_date_select_red);
//            viewHolder.setTextColor(R.id.tv_data_sign, ContextCompat.getColor(mContext, R.color.white));
//
//        } else {
//
//            if (item.)
//
//            viewHolder.setBackgroundRes(R.id.tv_data_sign, 0);
//            viewHolder.setTextColor(R.id.tv_data_sign, ContextCompat.getColor(mContext, R.color.app_txt_black));
//        }

    }

    /**
     * 判断是否有相同日期 如果有则说明有预约
     * @param item
     * @return
     */
    public boolean isHaveSameDay(TextView tvData, Date item) {

        LogUtil.E("compareData():"+compareData.size());

        for (TripDateModel model : compareData) {
            if (model == null)
                continue;

            LogUtil.E("item"+item);
            LogUtil.E("model.getDate()"+model.getDate());
            LogUtil.E("inSameDay"+DateUtil.inSameDay(item, model.getDate()));
            LogUtil.E("model.getColor()"+model.getColor());
            LogUtil.E("_-------------------------------------_");

            if (DateUtil.inSameDay(item, model.getDate())) {

                if (TextUtils.isEmpty(model.getColor())){

                    tvData.setBackgroundResource(R.drawable.trip_date_select_red);
                    tvData.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                }else {

                    switch (model.getColor()){

                        case "1":

                            tvData.setBackgroundResource(R.drawable.trip_date_select_green);
                            tvData.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                            break;

                        case "2":

                            tvData.setBackgroundResource(R.drawable.trip_date_select_red);
                            tvData.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                            break;

                        case "3":

                            tvData.setBackgroundResource(R.drawable.trip_date_select_yellow);
                            tvData.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                            break;

                    }

                }

            }

        }

        return false;

    }


    /**
     * 判断是否有相同日期 如果有则说明有预约
     * @param item
     * @return
     */
    public boolean isHaveSameDay(Date item) {

        LogUtil.E("compareData():"+compareData.size());

        for (TripDateModel model : compareData) {
            if (model == null)
                continue;

            LogUtil.E("item="+item);
            LogUtil.E("model.getDate()="+model.getDate());

            if (DateUtil.inSameDay(item, model.getDate())) {
                return true;
            }

        }

        return false;

    }

    public List<TripDateModel> getCompareData() {
        return compareData;
    }

    public void setCompareData(List<TripDateModel> data) {

        if (data == null ) {
            return;
        }

        compareData.clear();
        compareData.addAll(data);

        notifyDataSetChanged();
    }


}
