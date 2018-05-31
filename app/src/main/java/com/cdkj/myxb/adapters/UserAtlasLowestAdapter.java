package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemUserAtlasLowestBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.module.user.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasLowestAdapter extends BaseQuickAdapter<AtlasListModel, BaseViewHolder> {


    public UserAtlasLowestAdapter(@Nullable List<AtlasListModel> data) {
        super(R.layout.item_user_atlas_lowest, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtlasListModel item) {
        ItemUserAtlasLowestBinding mBinding = DataBindingUtil.bind(helper.itemView);

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), mBinding.imgLeft);

        mBinding.tvMobile.setText(item.getRealName() == null ? "" : item.getRealName());

        if (item.getKind().equals(UserHelper.T)){
            if (item.getMgJxsList() == null || item.getMgJxsList().size()==0){
                mBinding.ivMore.setVisibility(View.GONE);
            }else {
                mBinding.ivMore.setVisibility(View.VISIBLE);
            }
        } else {
            mBinding.ivMore.setVisibility(View.GONE);
        }

        if (item.getKind().equals(UserHelper.T)){
            mBinding.llRv.setVisibility(item.isShow() ? View.VISIBLE : View.GONE);
        }


        if (item.getMgJxsList() == null || item.getMgJxsList().size()==0){
            mBinding.llMgJxs.setVisibility(View.GONE);
        }else {
            mBinding.llMgJxs.setVisibility(View.VISIBLE);
        }
        UserAtlasLowestAdapter mMgJxsAdapter = new UserAtlasLowestAdapter(item.getMgJxsList());
        mBinding.rvMgJxs.setLayoutManager(getLinearLayoutManager());
        mBinding.rvMgJxs.setAdapter(mMgJxsAdapter);

        mBinding.llMgJxs.setOnClickListener(view -> {
            if (mBinding.rvMgJxs.getVisibility() == View.GONE){
                mBinding.rvMgJxs.setVisibility(View.VISIBLE);
                mBinding.ivMgJxs.setBackgroundResource(R.drawable.user_atlas_lower_up);
            }else {
                mBinding.rvMgJxs.setVisibility(View.GONE);
                mBinding.ivMgJxs.setBackgroundResource(R.drawable.user_atlas_lower_down);
            }
        });

    }


    /**
     * 获取布局管理器
     *
     * @return
     */
    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {  //禁止自滚动
                return false;
            }
        };
    }
}
