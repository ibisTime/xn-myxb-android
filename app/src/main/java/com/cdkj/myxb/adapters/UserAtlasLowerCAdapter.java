package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemUserAtlasLowerCBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.module.user.UserAtlasManagerActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasLowerCAdapter extends BaseQuickAdapter<AtlasListModel, BaseViewHolder> {


    public UserAtlasLowerCAdapter(@Nullable List<AtlasListModel> data) {
        super(R.layout.item_user_atlas_lower_c, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtlasListModel item) {
        ItemUserAtlasLowerCBinding mBinding = DataBindingUtil.bind(helper.itemView);

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), mBinding.imgLeft);

        mBinding.tvMobile.setText(item.getRealName() == null ? "" : item.getRealName());

        if (item.getKind().equals(UserHelper.T)){
            if (item.getJxsList() == null || item.getJxsList().size()==0){
                mBinding.ivMore.setVisibility(View.GONE);
            }else {
                if (item.getIsBuildable()!= null && item.getIsBuildable().equals("0")){
                    mBinding.ivMore.setVisibility(View.GONE);
                }else {
                    mBinding.ivMore.setVisibility(View.VISIBLE);
                }
            }
        } else {
            mBinding.ivMore.setVisibility(View.GONE);
        }


        UserAtlasLowestAdapter mJxsAdapter = new UserAtlasLowestAdapter(item.getJxsList());
        mJxsAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (item.getIsBuildable()!= null && !item.getIsBuildable().equals("0")){
                AtlasListModel model = mJxsAdapter.getItem(position);
                UserAtlasManagerActivity.open(mContext, model.getUserId());
            }
        });
        mBinding.rvJxs.setLayoutManager(getLinearLayoutManager());
        mBinding.rvJxs.setAdapter(mJxsAdapter);

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
