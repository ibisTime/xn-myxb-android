package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.IntegralListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/4.
 */

public class AccountBillAdapter extends BaseQuickAdapter<IntegralListModel, BaseViewHolder> {

    public AccountBillAdapter(@Nullable List<IntegralListModel> data) {
        super(R.layout.item_account_bill, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralListModel item) {
        helper.setText(R.id.tv_title, item.getBizNote());
        helper.setText(R.id.tv_date_time, DateUtil.formatStringData(item.getCreateDatetime(), DateUtil.DEFAULT_DATE_FMT));
        helper.setText(R.id.tv_price, MoneyUtils.showPrice(item.getTransAmount()));
    }
}
