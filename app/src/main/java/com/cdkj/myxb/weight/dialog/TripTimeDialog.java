package com.cdkj.myxb.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.DialogTripTimeBinding;

/**
 * 行程显示时间
 * Created by cdkj on 2018/2/10.
 */

public class TripTimeDialog extends Dialog {

    private DialogTripTimeBinding mBinding;

    public TripTimeDialog(@NonNull Context context) {
        super(context, R.style.TipsDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_trip_time, null, false);
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
