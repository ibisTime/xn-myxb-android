package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/12.
 */

public class SaleAmountAdapter extends BaseQuickAdapter<BrandListModel, BaseViewHolder> {

    public SaleAmountAdapter(@Nullable List<BrandListModel> data) {
        super(R.layout.item_result_brand_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandListModel item) {

        helper.setText(R.id.tv_brand_name, item.getName());

        EditText edtSale = helper.getView(R.id.edt_sale);
        edtSale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString())){
                    item.setSaleAmount(((int) (Double.parseDouble(editable.toString().trim()) * 1000))+"");
                }
            }
        });

        helper.addOnClickListener(R.id.iv_delete);

    }

}
