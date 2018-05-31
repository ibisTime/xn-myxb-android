package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.CommentItemModel;
import com.cdkj.myxb.weight.views.MyRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 导购
 * Created by cdkj on 2017/10/12.
 */

public class CommentItemAdapter extends BaseQuickAdapter<CommentItemModel, BaseViewHolder> {


    public CommentItemAdapter(@Nullable List<CommentItemModel> data) {
        super(R.layout.item_comments_change, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentItemModel item) {

        helper.setText(R.id.tv_commetns, item.getCvalue());

        MyRatingBar bar = helper.getView(R.id.ratingbar_comments);

        bar.setOnRatingChangeListener(RatingCount -> {
            item.setcScore(RatingCount + "");
        });

    }
}
