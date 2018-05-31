package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.models.ExpertRankListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 专家排名列表
 * Created by cdkj on 2017/10/12.
 */

public class ExpertsRankListAdapter extends BaseQuickAdapter<ExpertRankListModel, BaseViewHolder> {


    public ExpertsRankListAdapter(@Nullable List<ExpertRankListModel> data) {
        super(R.layout.item_my_rank, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertRankListModel item) {
        if (item == null) return;


        ImgUtils.loadQiniuLogo(mContext, SPUtilHelpr.getUserPhoto(), helper.getView(R.id.img_logo));

        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_date, item.getPeriods());

        helper.setText(R.id.tv_count, MoneyUtils.getShowPriceSign(item.getAmount()));
        helper.setGone(R.id.img_rank, !(item.getRank() > 3));
        helper.setGone(R.id.tv_rank, item.getRank() > 3);

        if (item.getRank() > 3) {
            helper.setText(R.id.tv_rank, "第" + item.getRank() + "名");
        } else {
            switch (item.getRank()) {
                case 1:
                    helper.setImageResource(R.id.img_rank, R.drawable.rank_1);
                    break;
                case 2:
                    helper.setImageResource(R.id.img_rank, R.drawable.rank_2);
                    break;
                case 3:
                    helper.setImageResource(R.id.img_rank, R.drawable.rank_3);
                    break;
            }
        }

    }
}
