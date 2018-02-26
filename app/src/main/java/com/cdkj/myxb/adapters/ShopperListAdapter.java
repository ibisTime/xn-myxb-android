package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.weight.views.MyRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * 导购
 * Created by cdkj on 2017/10/12.
 */

public class ShopperListAdapter extends BaseQuickAdapter<UserModel, BaseViewHolder> {


    public ShopperListAdapter(@Nullable List<UserModel> data) {
        super(R.layout.item_shopper_appointment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserModel item) {
        if (item == null) return;

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), helper.getView(R.id.img_logo));

        helper.setText(R.id.tv_specialty, "美导  专长：" + item.getStyle());
        helper.setText(R.id.tv_user_info, item.getIntroduce());
        helper.setText(R.id.tv_name, item.getRealName());

        setTagLayout(helper, item);

        MyRatingBar ratingBar = helper.getView(R.id.ratingbar_shopper);

        ratingBar.setStar(item.getLevel());


    }

    /**
     * 设置标签布局
     *
     * @param helper
     * @param item
     */
    private void setTagLayout(BaseViewHolder helper, UserModel item) {
        FlexboxLayout flexboxLayout = helper.getView(R.id.flexbox_layout_search);

        for (String searString : StringUtils.splitAsList(item.getStyle(), ",")) {
            if (TextUtils.isEmpty(searString)) return;
            flexboxLayout.addView(createNewFlexItemTextView(searString));
        }
    }

    /**
     * 动态创建TextView
     *
     * @param
     * @return
     */
    private TextView createNewFlexItemTextView(String str) {
        final TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setText(str);
        textView.setTextSize(12);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.shopper_lable_1));
        textView.setBackgroundResource(R.drawable.bg_shopper_lable);
        textView.setTag(str);

        int padding = DisplayHelper.dip2px(mContext, 5);
        int paddingLeftAndRight = DisplayHelper.dip2px(mContext, 10);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DisplayHelper.dip2px(mContext, 5);
        int marginTop = DisplayHelper.dip2px(mContext, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
