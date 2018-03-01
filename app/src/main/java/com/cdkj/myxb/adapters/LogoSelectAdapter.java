package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.LogoListModel;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 用户头像选择
 * Created by cdkj on 2017/10/12.
 */

public class LogoSelectAdapter extends BaseQuickAdapter<LogoListModel, BaseViewHolder> {


    private int selectPosition = -1;//选择的下标

    public LogoSelectAdapter(@Nullable List<LogoListModel> data) {
        super(R.layout.item_logo_select, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, LogoListModel item) {
        if (item == null) {
            return;
        }

        FrameLayout fr = viewHolder.getView(R.id.fra_bg);

        if (selectPosition == viewHolder.getLayoutPosition()) {
            fr.setBackgroundResource(R.drawable.logo_select);
        } else {
            fr.setBackgroundResource(R.drawable.logo_select_un);
        }
        ImgUtils.loadQiniuLogo(mContext, item.getUrl(), viewHolder.getView(R.id.img_logo));
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public String getSelectUrl() {
        if (selectPosition == -1 || mData.size() < selectPosition) {
            return "";
        }

        LogoListModel logoListModel = mData.get(selectPosition);

        if (logoListModel == null) return "";

        return logoListModel.getUrl();

    }


}
