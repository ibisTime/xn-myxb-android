package com.cdkj.myxb.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.module.order.OrderHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 预约状态列表
 * Created by cdkj on 2017/10/12.
 */

public class AppointmentListAdapter extends BaseQuickAdapter<AppointmentListModel, BaseViewHolder> {


    public AppointmentListAdapter(@Nullable List<AppointmentListModel> data) {
        super(R.layout.item_appointment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppointmentListModel item) {

        if (item == null) return;

        if (item.getUser() != null) {
            helper.setText(R.id.tv_name, item.getUser().getRealName());
            ImgUtils.loadQiniuLogo(mContext, item.getUser().getPhoto(), helper.getView(R.id.img_logo));
        }

        helper.setText(R.id.tv_orderId, "订单编号：" + item.getCode());
        helper.setText(R.id.tv_state, OrderHelper.getAppoitmentState(item.getStatus()));
        helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DATE_YYMMddHHmm));

        helper.setText(R.id.tv_state_do, OrderHelper.getAppointmentBtnStateString(item.getStatus()));

        helper.setGone(R.id.lin_buttom, canComment(item) || OrderHelper.canShowAppointmentButton(item.getStatus())); //可以评价或有操作时可以显示
        helper.setGone(R.id.tv_state_do, OrderHelper.canShowAppointmentButton(item.getStatus()));
        helper.setGone(R.id.tv_to_comment, canComment(item));//

        helper.addOnClickListener(R.id.tv_state_do);
        helper.addOnClickListener(R.id.tv_to_comment);

    }

    /**
     * 是否可以进行评价 0 可以评价 (待审核 已排班待上门时不能评价)
     *
     * @param item
     * @return
     */
    private boolean canComment(AppointmentListModel item) {
        return TextUtils.equals("0", item.getIsComment()) && !TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_1) && TextUtils.equals(item.getStatus(), OrderHelper.APPOINTMENT_2);
    }
}
