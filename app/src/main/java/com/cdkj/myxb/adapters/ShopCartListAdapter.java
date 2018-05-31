package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemShopCartListBinding;
import com.cdkj.myxb.models.ShopCartListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 品牌列表
 * Created by cdkj on 2017/10/12.
 */

public class ShopCartListAdapter extends BaseQuickAdapter<ShopCartListModel, BaseViewHolder> {

    private boolean isShopCartOrder;

    public ShopCartListAdapter(@Nullable List<ShopCartListModel> data, boolean isShopCartOrder) {
        super(R.layout.item_shop_cart_list, data);
        this.isShopCartOrder = isShopCartOrder;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartListModel item) {
        ItemShopCartListBinding mBinding = DataBindingUtil.bind(helper.itemView);

        if (item == null) return;

        if (item.isChoice()){
            helper.setBackgroundRes(R.id.iv_choice, R.drawable.pay_choose);
        }else {
            helper.setBackgroundRes(R.id.iv_choice, R.drawable.pay_unchoose);
        }

        ImgUtils.loadQiniuImg(mContext, item.getProduct().getPic(), helper.getView(R.id.iv_advPic));
        helper.setText(R.id.tv_name, item.getProduct().getName());
        helper.setText(R.id.tv_price, MoneyUtils.getShowPriceSign(item.getProduct().getPrice()));
        helper.setText(R.id.tv_info, item.getProduct().getSlogan());

        if (isShopCartOrder){  // isShopCartOrder为true, 不可修改数量、删除

            mBinding.llSub.setVisibility(View.GONE);
            mBinding.llAdd.setVisibility(View.GONE);
            mBinding.ivDelete.setVisibility(View.GONE);
            mBinding.ivChoice.setVisibility(View.GONE);

            helper.setText(R.id.tv_quantity, "x"+item.getQuantity());
        }else {
            mBinding.llSub.setVisibility(View.VISIBLE);
            mBinding.llAdd.setVisibility(View.VISIBLE);
            mBinding.ivDelete.setVisibility(View.VISIBLE);
            mBinding.ivChoice.setVisibility(View.VISIBLE);

            helper.setText(R.id.tv_quantity, item.getQuantity()+"");
        }

        helper.addOnClickListener(R.id.iv_choice);
        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.ll_sub);
        helper.addOnClickListener(R.id.ll_add);
    }


}
