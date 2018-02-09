package com.cdkj.myxb.module.shopper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityShopperAppointmentBinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by cdkj on 2018/2/9.
 */

public class ShopperAppointmentActivity extends AbsBaseLoadActivity {

    private ActivityShopperAppointmentBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopperAppointmentActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shopper_appointment, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.shopper_appoint));
        mBinding.tripDate.initData();

        initListener();

    }

    /**
     *
     */
    private void initListener() {
        ObjectAnimator animStart = ObjectAnimator.ofFloat(mBinding.imgTrip, "rotation", 0f, 90f);
        ObjectAnimator animEnd = ObjectAnimator.ofFloat(mBinding.imgTrip, "rotation", 90f, 0f);
        mBinding.llayoutTrip.setOnClickListener(view -> {
            if (mBinding.tripDate.getVisibility() == GONE) {
                animStart.start();
                mBinding.tripDate.setVisibility(VISIBLE);

            } else {
                animEnd.start();
                mBinding.tripDate.setVisibility(GONE);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.tripDate.destroyView();
    }
}
