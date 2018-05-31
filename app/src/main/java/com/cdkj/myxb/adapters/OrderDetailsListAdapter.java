package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.module.user.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 品牌列表
 * Created by cdkj on 2017/10/12.
 */

public class OrderDetailsListAdapter extends BaseQuickAdapter<OrderListModel.ProductOrderListBean, BaseViewHolder> {

    private String payType;

    public OrderDetailsListAdapter(@Nullable List<OrderListModel.ProductOrderListBean> data, String payType) {
        super(R.layout.item_order_details_list, data);
        this.payType = payType;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListModel.ProductOrderListBean item) {

        if (item == null) return;

        ImgUtils.loadQiniuImg(mContext, item.getProduct().getPic(), helper.getView(R.id.img_pic));

        helper.setText(R.id.tv_name, item.getProduct().getName());
        helper.setText(R.id.tv_info, item.getProduct().getSlogan());

        if (payType.equals("0")){
            helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getPrice()));
        }else {
            if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
                helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getDiscountPrice()));
            }else {
                helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getPrice()));
            }

        }


        helper.setText(R.id.tv_num, "X" + item.getQuantity());

    }


}
