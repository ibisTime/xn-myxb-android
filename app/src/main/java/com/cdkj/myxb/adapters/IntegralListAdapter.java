package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.IntegralListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 积分流水
 * Created by cdkj on 2017/10/12.
 */

public class IntegralListAdapter extends BaseQuickAdapter<IntegralListModel, BaseViewHolder> {


    public IntegralListAdapter(@Nullable List<IntegralListModel> data) {
        super(R.layout.item_integral_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralListModel item) {

        if (item == null) return;

        helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getCreateDatetime(), DateUtil.DEFAULT_DATE_FMT));
        helper.setText(R.id.tv_name, item.getBizNote());
        helper.setText(R.id.tv_sum, item.getTransAmount());


    }
}
