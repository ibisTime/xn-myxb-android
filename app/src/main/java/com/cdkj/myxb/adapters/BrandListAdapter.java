package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.BrandProductModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 品牌列表
 * Created by cdkj on 2017/10/12.
 */

public class BrandListAdapter extends BaseQuickAdapter<BrandProductModel, BaseViewHolder> {


    public BrandListAdapter(@Nullable List<BrandProductModel> data) {
        super(R.layout.item_brand_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandProductModel item) {
        if (item == null) return;


        ImgUtils.loadQiniuImg(mContext, item.getPic(), helper.getView(R.id.img_brand_product));

        helper.setText(R.id.tv_brand_product_name, item.getName());
        helper.setText(R.id.tv_brand_slogan, item.getSlogan());
        helper.setText(R.id.tv_brand_price, MoneyUtils.getShowPriceSign(item.getPrice()));
        helper.setText(R.id.tv_brand_sell_num, "已出售:" + item.getSoldOutCount());


    }


}
