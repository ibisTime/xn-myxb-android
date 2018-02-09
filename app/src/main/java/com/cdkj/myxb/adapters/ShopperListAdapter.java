package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 导购
 * Created by cdkj on 2017/10/12.
 */

public class ShopperListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ShopperListAdapter(@Nullable List<String> data) {
        super(R.layout.item_shopper_appointment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
