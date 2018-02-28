package com.cdkj.myxb.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityLogoSelectBinding;
import com.cdkj.myxb.databinding.DialogTripTimeBinding;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 行程显示时间
 * Created by cdkj on 2018/2/10.
 */

public class LogoSelectDialog extends Dialog {

    private ActivityLogoSelectBinding mBinding;

    private MouthAppointmentModel appointmentDateViewModel;

    public LogoSelectDialog(@NonNull Context context, MouthAppointmentModel appointmentDateViewModel) {
        super(context, R.style.TipsDialog);
        this.appointmentDateViewModel = appointmentDateViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_logo_select, null, false);
        int screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        setContentView(mBinding.getRoot());
        getWindow().setLayout((int) (screenWidth * 0.8f), ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mBinding.btnIKnow.setOnClickListener(view -> {
            dismiss();
        });

    }





}
