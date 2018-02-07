package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 首页品牌
 * Created by cdkj on 2017/10/12.
 */

public class FirstPageBrandAdapter extends BaseQuickAdapter<BrandModel, BaseViewHolder> {


    public FirstPageBrandAdapter(@Nullable List<BrandModel> data) {
        super(R.layout.layout_abs_empty, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandModel item) {

    }
}
