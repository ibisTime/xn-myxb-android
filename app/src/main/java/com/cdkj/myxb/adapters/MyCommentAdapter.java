package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.MyCommentListMode;
import com.cdkj.myxb.weight.views.MyRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 评价列表
 * Created by cdkj on 2017/10/12.
 */

public class MyCommentAdapter extends BaseQuickAdapter<MyCommentListMode, BaseViewHolder> {


    public MyCommentAdapter(@Nullable List<MyCommentListMode> data) {
        super(R.layout.item_my_comments, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommentListMode item) {
        if (item == null) return;

        MyRatingBar myRatingBar = helper.getView(R.id.ratingbar_comment);

        myRatingBar.setmClickable(false);

        helper.setText(R.id.tv_comment_name, item.getCommentUser().getMobile());
        helper.setText(R.id.tv_comment_time, DateUtil.formatStringData(item.getCommentDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_comment_info, item.getContent());

    }
}
