package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemUserAtlasManagerBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasManagerAdapter extends BaseQuickAdapter<AtlasListModel, BaseViewHolder> {


    public UserAtlasManagerAdapter(@Nullable List<AtlasListModel> data) {
        super(R.layout.item_user_atlas_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtlasListModel item) {
        ItemUserAtlasManagerBinding mBinding = DataBindingUtil.bind(helper.itemView);

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), mBinding.imgLeft);

        mBinding.tvMobile.setText(item.getRealName() == null ? "" : item.getRealName());

        helper.addOnClickListener(R.id.tv_divide);

    }

}
