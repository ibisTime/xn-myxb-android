package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.IntegralModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 积分商城
 * Created by cdkj on 2017/10/12.
 */

public class IntegraMallListAdapter extends BaseQuickAdapter<IntegralModel, BaseViewHolder> {


    public IntegraMallListAdapter(@Nullable List<IntegralModel> data) {
        super(R.layout.item_integral_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralModel item) {
        if (item == null) return;

        LogUtil.E(""+helper.getLayoutPosition());
        helper.setText(R.id.tv_integra_title, item.getName());
        helper.setText(R.id.tv_integra_title, item.getName());
        helper.setText(R.id.tv_integra, MoneyUtils.showPrice(item.getPrice()));

        ImgUtils.loadImg(mContext, MyCdConfig.QINIUURL + item.getAdvPic(), helper.getView(R.id.img_integra));

    }
}
