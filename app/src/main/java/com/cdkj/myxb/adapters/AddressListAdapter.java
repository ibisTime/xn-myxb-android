package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.AddressModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 发布页面图片适配器
 * Created by cdkj on 2017/10/12.
 */

public class AddressListAdapter extends BaseQuickAdapter<AddressModel, BaseViewHolder> {

    public AddressListAdapter(@Nullable List<AddressModel> data) {
        super(R.layout.item_address, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, AddressModel item) {
        if (item == null) return;
        TextView txtName = viewHolder.getView(R.id.txt_name);
        TextView txtPhone = viewHolder.getView(R.id.txt_phone);
        TextView txtAddress = viewHolder.getView(R.id.txt_address);
        TextView tvChoose = viewHolder.getView(R.id.tv_is_choose);
        ImageView imgChoose = viewHolder.getView(R.id.img_choose);


        if (item.isDefaultAddress()) {
            imgChoose.setBackgroundResource(R.drawable.check_on);
            tvChoose.setTextColor(ContextCompat.getColor(mContext, R.color.address_select));
        } else {
            imgChoose.setBackgroundResource(R.drawable.pay_no);
            tvChoose.setTextColor(ContextCompat.getColor(mContext, R.color.app_txt_black));
        }

        txtName.setText(item.getAddressee());
        txtPhone.setText(item.getMobile());
        txtAddress.setText(item.getProvince() + " " + item.getCity() + " " + item.getDistrict() + "" + item.getDetailAddress());

        viewHolder.addOnClickListener(R.id.layout_delete);
        viewHolder.addOnClickListener(R.id.layout_edit);
        viewHolder.addOnClickListener(R.id.real_address);


    }

}
