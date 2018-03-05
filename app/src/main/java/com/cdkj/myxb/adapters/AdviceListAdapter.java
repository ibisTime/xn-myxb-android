package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.AdviceListModel;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.weight.views.MyRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 评论列表
 * Created by cdkj on 2017/10/12.
 */

public class AdviceListAdapter extends BaseQuickAdapter<AdviceListModel, BaseViewHolder> {


    public AdviceListAdapter(@Nullable List<AdviceListModel> data) {
        super(R.layout.item_comments, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdviceListModel item) {
        if (item == null) return;

        MyRatingBar myRatingBar = helper.getView(R.id.ratingbar_comment);

        myRatingBar.setmClickable(false);

        myRatingBar.setStar(item.getScore());

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), helper.getView(R.id.img_comment));

        helper.setText(R.id.tv_comment_name, item.getRealName());
        helper.setText(R.id.tv_comment_time, DateUtil.formatStringData(item.getCommentDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_comment_info, item.getContent());

        helper.setGone(R.id.tv_comment_info, !TextUtils.isEmpty(item.getContent()));

    }

}
