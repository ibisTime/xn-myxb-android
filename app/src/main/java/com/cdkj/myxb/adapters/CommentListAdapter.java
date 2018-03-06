package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.weight.views.MyRatingBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 评价列表
 * Created by cdkj on 2017/10/12.
 */

public class CommentListAdapter extends BaseQuickAdapter<CommentListMode, BaseViewHolder> {


    public CommentListAdapter(@Nullable List<CommentListMode> data) {
        super(R.layout.item_comments, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListMode item) {
        if (item == null) return;

        MyRatingBar myRatingBar = helper.getView(R.id.ratingbar_comment);

        myRatingBar.setmClickable(false);

        myRatingBar.setStar(item.getScore());

        ImgUtils.loadQiniuLogo(mContext, item.getPhoto(), helper.getView(R.id.img_comment));

        helper.setText(R.id.tv_comment_name, item.getEntityName());
        helper.setText(R.id.tv_comment_time, DateUtil.formatStringData(item.getCommentDatetime(), DateUtil.DATE_YMD));
        helper.setText(R.id.tv_comment_info, item.getContent());

    }
}
