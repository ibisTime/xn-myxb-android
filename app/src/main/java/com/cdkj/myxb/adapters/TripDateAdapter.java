package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.TripDatetimeModel;
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


    private List<TripDatetimeModel> datetimeModels;

    public TripDateAdapter(@Nullable List<Date> data) {
        super(R.layout.item_trip_date, data);
        datetimeModels = new ArrayList<>();
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, Date item) {
        if (item == null) {
            viewHolder.setText(R.id.tv_data_sign, "");
            viewHolder.setBackgroundColor(R.id.tv_data_sign, ContextCompat.getColor(mContext,R.color.white));
            return;
        }
        viewHolder.setText(R.id.tv_data_sign, DateUtil.format(item, "dd"));

        if ( isShowSignBgAndImg(item)) {

        } else {

        }

    }

    public void setSignData(List<TripDatetimeModel> das) {
        if (das == null || das.size() == 0) return;
        datetimeModels.clear();
        datetimeModels.addAll(das);
        notifyDataSetChanged();
    }


    public boolean isShowSignBgAndImg(Date posiDate) {
        for (TripDatetimeModel datetimeModel : datetimeModels) {
            if (datetimeModel == null || TextUtils.isEmpty(datetimeModel.getDatetime())) {
                continue;
            }

            if (DateUtil.inSameDay(posiDate, new Date(datetimeModel.getDatetime()))) {
                return true;
            }
        }
        return false;
    }

}
