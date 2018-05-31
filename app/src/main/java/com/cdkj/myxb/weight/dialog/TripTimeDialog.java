package com.cdkj.myxb.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;

import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.DialogTripTimeBinding;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 行程显示时间
 * Created by cdkj on 2018/2/10.
 */

public class TripTimeDialog extends Dialog {

    private DialogTripTimeBinding mBinding;

    private List<MouthAppointmentModel> appointmentDateViewModels;

    public TripTimeDialog(@NonNull Context context, List<MouthAppointmentModel> appointmentDateViewModel) {
        super(context, R.style.TipsDialog);
        this.appointmentDateViewModels = appointmentDateViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_trip_time, null, false);
//        int screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int screenWidth = DisplayHelper.getScreenWidth(getContext());
        setContentView(mBinding.getRoot());
        getWindow().setLayout((int) (screenWidth * 0.8f), ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mBinding.btnIKnow.setOnClickListener(view -> {
            dismiss();
        });

        setShowTime();
    }

    private void setShowTime() {
        if (appointmentDateViewModels == null || mBinding == null) return;

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(new BaseQuickAdapter<MouthAppointmentModel, BaseViewHolder>(R.layout.item_dialog_appointment, appointmentDateViewModels) {
            @Override
            protected void convert(BaseViewHolder helper, MouthAppointmentModel item) {
                if (item == null) return;
                helper.setText(R.id.tv_time, item.getDate()); // + " - " + DateUtil.formatStringData(item.getEndDatetime(), DateUtil.DATE_YYMMddHHmm)

            }
        });

    }


}
