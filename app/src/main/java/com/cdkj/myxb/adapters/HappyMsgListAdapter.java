package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.HappyMsgModel;
import com.cdkj.myxb.models.TripListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;
import java.util.List;

/**
 * 喜报预报列表
 * Created by cdkj on 2017/10/12.
 */

public class HappyMsgListAdapter extends BaseQuickAdapter<HappyMsgModel, BaseViewHolder> {


    public HappyMsgListAdapter(@Nullable List<HappyMsgModel> data) {
        super(R.layout.item_happy_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HappyMsgModel item) {
        if (item == null) return;

        ImgUtils.loadQiniuImg(mContext, item.getAdvPic(), helper.getView(R.id.img_msg));

        helper.setText(R.id.tv_title, item.getTitle());


    }
}
