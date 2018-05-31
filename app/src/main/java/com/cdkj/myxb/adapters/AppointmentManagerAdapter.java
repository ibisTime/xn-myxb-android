package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.TripListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 行程列表
 * Created by cdkj on 2017/10/12.
 */

public class AppointmentManagerAdapter extends BaseQuickAdapter<TripListModel, BaseViewHolder> {


    public AppointmentManagerAdapter(@Nullable List<TripListModel> data) {
        super(R.layout.item_appointment_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TripListModel item) {
        if (item == null) return;


        ImgUtils.loadQiniuLogo(mContext, SPUtilHelpr.getUserPhoto(), helper.getView(R.id.img_logo));

        if (item.getType() != null) {

            switch (item.getType()){

                case "1":
                    helper.setText(R.id.tv_type, "可预约");
                    break;

                case "2":
                    helper.setText(R.id.tv_type, "可调配时间");
                    break;

            }

        }

        helper.setText(R.id.tv_month, DateUtil.formatStringData(item.getStartDatetime(), DateUtil.DATE_YM));
        helper.setText(R.id.tv_start, DateUtil.formatStringData(item.getStartDatetime(), DateUtil.DATE_DHM));
        helper.setText(R.id.tv_end, DateUtil.formatStringData(item.getEndDatetime(), DateUtil.DATE_DHM));


    }
}
