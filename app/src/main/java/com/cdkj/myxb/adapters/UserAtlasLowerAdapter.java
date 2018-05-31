package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemUserAtlasLowerBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.module.user.UserAtlasManagerActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasLowerAdapter extends BaseQuickAdapter<AtlasListModel, BaseViewHolder> {


    public UserAtlasLowerAdapter(@Nullable List<AtlasListModel> data) {
        super(R.layout.item_user_atlas_lower, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtlasListModel item) {
        ItemUserAtlasLowerBinding mBinding = DataBindingUtil.bind(helper.itemView);

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
            mBinding.ivMore.setVisibility(View.VISIBLE);
        }

        if (item.getKind().equals(UserHelper.T)){
            mBinding.llRv.setVisibility(item.isShow() ? View.VISIBLE : View.GONE);
        }


        if (item.getJxsList() == null || item.getJxsList().size()==0){
            mBinding.llJxs.setVisibility(View.GONE);
        }else {
            mBinding.llJxs.setVisibility(View.VISIBLE);
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

        mBinding.llJxs.setOnClickListener(view -> {
            if (mBinding.rvJxs.getVisibility() == View.GONE){
                mBinding.rvJxs.setVisibility(View.VISIBLE);
                mBinding.ivJxs.setBackgroundResource(R.drawable.user_atlas_lower_up);
            }else {
                mBinding.rvJxs.setVisibility(View.GONE);
                mBinding.ivJxs.setBackgroundResource(R.drawable.user_atlas_lower_down);
            }
        });

//        mBinding.llJxs.setOnClickListener(view -> {
//            mBinding.rvJxs.setVisibility(mBinding.rvJxs.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//        });


        if (item.getFwsList() == null || item.getFwsList().size()==0){
            mBinding.llFws.setVisibility(View.GONE);
        }else {
            mBinding.llFws.setVisibility(View.VISIBLE);
        }
        UserAtlasLowestAdapter mFwsAdapter = new UserAtlasLowestAdapter(item.getFwsList());
        mFwsAdapter.setOnItemClickListener((adapter, view, position) -> {

            AtlasListModel model = mFwsAdapter.getItem(position);
            model.setShow(!model.isShow());
            mFwsAdapter.notifyItemChanged(position);
        });
        mBinding.rvFws.setLayoutManager(getLinearLayoutManager());
        mBinding.rvFws.setAdapter(mFwsAdapter);

        mBinding.llFws.setOnClickListener(view -> {
            if (mBinding.rvFws.getVisibility() == View.GONE){
                mBinding.rvFws.setVisibility(View.VISIBLE);
                mBinding.ivFws.setBackgroundResource(R.drawable.user_atlas_lower_up);
            }else {
                mBinding.rvFws.setVisibility(View.GONE);
                mBinding.ivFws.setBackgroundResource(R.drawable.user_atlas_lower_down);
            }
        });


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
