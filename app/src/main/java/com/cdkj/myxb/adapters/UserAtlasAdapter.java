package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemUserAtlasBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.models.AtlasModel;
import com.cdkj.myxb.module.user.UserAtlasManagerActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasAdapter extends BaseQuickAdapter<AtlasModel, BaseViewHolder> {


    public UserAtlasAdapter(@Nullable List<AtlasModel> data) {
        super(R.layout.item_user_atlas, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AtlasModel item) {
        ItemUserAtlasBinding mBinding = DataBindingUtil.bind(helper.itemView);

        switch (helper.getLayoutPosition()){

            case 0:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_1);
                mBinding.tvLine.setText("白金");
                break;

            case 1:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_2);
                mBinding.tvLine.setText("钻石");
                break;

            case 2:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_3);
                mBinding.tvLine.setText("双钻石");
                break;

            case 3:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_4);
                mBinding.tvLine.setText("皇冠");
                break;

            case 4:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_5);
                mBinding.tvLine.setText("双皇冠");
                break;

            case 5:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_6);
                mBinding.tvLine.setText("三皇冠");
                break;

            default:
                mBinding.ivLine.setBackgroundResource(R.drawable.user_atlas_level_6);
                mBinding.tvLine.setText("三皇冠");
                break;

        }


        if (item.getJxsList() == null || item.getJxsList().size()==0){
            mBinding.llJxs.setVisibility(View.GONE);
        }else {
            mBinding.llJxs.setVisibility(View.VISIBLE);
        }
        UserAtlasLowerAdapter mJxsAdapter = new UserAtlasLowerAdapter(item.getJxsList());
        mJxsAdapter.setOnItemClickListener((adapter, view, position) -> {

            AtlasListModel model = mJxsAdapter.getItem(position);
            UserAtlasManagerActivity.open(mContext, model.getUserId());

        });
        mBinding.rvJxs.setLayoutManager(getLinearLayoutManager());
        mBinding.rvJxs.setAdapter(mJxsAdapter);

        mBinding.llJxs.setOnClickListener(view -> {
            if (mBinding.rvJxs.getVisibility() == View.GONE){
                mBinding.rvJxs.setVisibility(View.VISIBLE);
                mBinding.ivJxs.setBackgroundResource(R.drawable.user_atlas_up);
            }else {
                mBinding.rvJxs.setVisibility(View.GONE);
                mBinding.ivJxs.setBackgroundResource(R.drawable.user_atlas_down);
            }
        });


        if (item.getFwsList() == null || item.getFwsList().size()==0){
            mBinding.llFws.setVisibility(View.GONE);
        }else {
            mBinding.llFws.setVisibility(View.VISIBLE);
        }
        UserAtlasLowerAdapter mFwsAdapter = new UserAtlasLowerAdapter(item.getFwsList());
        mFwsAdapter.setOnItemClickListener((adapter, view, position) -> {

            AtlasListModel model = mFwsAdapter.getItem(position);
            model.setShow(!model.isShow());
            mFwsAdapter.notifyItemChanged(position);
        });
        mBinding.rvFws.setLayoutManager(getLinearLayoutManager());
        mBinding.rvFws.setAdapter(mFwsAdapter);

//        mBinding.llFws.setOnClickListener(view -> {
//            mBinding.rvFws.setVisibility(mBinding.rvFws.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//        });

        mBinding.llFws.setOnClickListener(view -> {
            if (mBinding.rvFws.getVisibility() == View.GONE){
                mBinding.rvFws.setVisibility(View.VISIBLE);
                mBinding.ivFws.setBackgroundResource(R.drawable.user_atlas_up);
            }else {
                mBinding.rvFws.setVisibility(View.GONE);
                mBinding.ivFws.setBackgroundResource(R.drawable.user_atlas_down);
            }
        });

        /*防止局部刷新闪烁*/
        ((DefaultItemAnimator) mBinding.rvFws.getItemAnimator()).setSupportsChangeAnimations(false);
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
