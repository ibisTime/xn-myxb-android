package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.GroupTripListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 行程列表
 * Created by cdkj on 2017/10/12.
 */

public class GroupTripListAdapter extends BaseQuickAdapter<GroupTripListModel, BaseViewHolder> {


    public GroupTripListAdapter(@Nullable List<GroupTripListModel> data) {
        super(R.layout.item_trip, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupTripListModel item) {
        if (item == null) return;


        ImgUtils.loadQiniuLogo(mContext, item.getUser().getPhoto(), helper.getView(R.id.img_logo));

        helper.setText(R.id.tv_day, item.getUser().getMobile() + "  " +  DateUtil.getWeekOfDate(item.getApplyDatetime()));
        helper.setText(R.id.tv_date, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_HM) + "-" + DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_HM));


    }
}
