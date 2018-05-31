package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.BrandModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 首页品牌
 * Created by cdkj on 2017/10/12.
 */

public class FirstPageBrandAdapter extends BaseQuickAdapter<BrandListModel, BaseViewHolder> {


    public FirstPageBrandAdapter(@Nullable List<BrandListModel> data) {
        super(R.layout.item_brand_firstpage, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandListModel item) {

        if (item == null) return;


        ImgUtils.loadQiniuImg(mContext, item.getAdvPic(), helper.getView(R.id.img_brand));

        helper.setText(R.id.tv_brand_name, item.getName());

    }
}
