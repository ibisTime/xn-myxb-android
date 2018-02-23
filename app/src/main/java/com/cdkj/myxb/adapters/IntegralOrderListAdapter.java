package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.IntegralOrderListModel;
import com.cdkj.myxb.module.order.OrderHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 积分订单列表
 * Created by cdkj on 2017/10/12.
 */

public class IntegralOrderListAdapter extends BaseQuickAdapter<IntegralOrderListModel, BaseViewHolder> {


    public IntegralOrderListAdapter(@Nullable List<IntegralOrderListModel> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralOrderListModel item) {

        if (item == null) return;

        ImgUtils.loadImg(mContext, MyCdConfig.QINIUURL + item.getProductPic(), helper.getView(R.id.img_good));

        helper.setText(R.id.tv_orderId, "订单编号：" + item.getCode());
        helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_name, item.getProductName());
        helper.setText(R.id.tv_price, MoneyUtils.showPrice(item.getPrice()));
        helper.setText(R.id.tv_price_all, MoneyUtils.showPrice(item.getAmount()));
        helper.setText(R.id.tv_state, OrderHelper.getIntegralOrderState(item.getStatus()));

        helper.setGone(R.id.lin_buttom, OrderHelper.canShowButton(item.getStatus()));

        helper.setText(R.id.tv_state_do, OrderHelper.getBtnStateString(item.getStatus()));

        helper.addOnClickListener(R.id.tv_state_do);
    }


}
