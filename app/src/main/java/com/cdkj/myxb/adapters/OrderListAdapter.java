package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 积分订单列表
 * Created by cdkj on 2017/10/12.
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderListModel, BaseViewHolder> {


    public OrderListAdapter(@Nullable List<OrderListModel> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListModel item) {

        if (item == null) return;

        ImgUtils.loadQiniuImg(mContext,  item.getProductOrderList().get(0).getProduct().getPic(), helper.getView(R.id.img_good));

        helper.setText(R.id.tv_orderId, "订单编号：" + item.getCode());
        helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_name, item.getProductOrderList().get(0).getProduct().getName());

        if (TextUtils.equals(item.getPayType(), "0")){
            helper.setText(R.id.tv_price_all, MoneyUtils.getShowPriceSign(item.getTotalAmount()));
//            helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getProductOrderList().get(0).getPrice()));
        }else {

            if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
                helper.setText(R.id.tv_price_all, MoneyUtils.getShowPriceSign(item.getAmount()));
//                helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getProductOrderList().get(0).getDiscountPrice()));
            }else {
                helper.setText(R.id.tv_price_all, MoneyUtils.getShowPriceSign(item.getTotalAmount()));
//                helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getProductOrderList().get(0).getPrice()));
            }
        }

        helper.setText(R.id.tv_state, OrderHelper.getOrderState(item.getStatus()));

        helper.setText(R.id.tv_quantity, "X" + item.getProductOrderList().size()+"");

        helper.setGone(R.id.lin_button, OrderHelper.canShowOrderButton(item.getStatus()));
        helper.setText(R.id.tv_state_do, OrderHelper.getOrderBtnStateString(item.getStatus()));

        helper.addOnClickListener(R.id.tv_state_do);
    }


}
