package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 品牌列表
 * Created by cdkj on 2017/10/12.
 */

public class BrandListAdapter extends BaseQuickAdapter<BrandListModel, BaseViewHolder> {


    public BrandListAdapter(@Nullable List<BrandListModel> data) {
        super(R.layout.item_brand_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandListModel item) {
        if (item == null) return;

        if(item.isChoose()){
            helper.setTextColor(R.id.tv_type, ContextCompat.getColor(mContext, R.color.title_bg));
        }else{
            helper.setTextColor(R.id.tv_type, ContextCompat.getColor(mContext, R.color.text_black_cd));
        }
        helper.setText(R.id.tv_type, item.getName());


    }


}
