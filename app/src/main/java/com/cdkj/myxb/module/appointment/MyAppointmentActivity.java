package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityMyAppointmentBinding;
import com.cdkj.myxb.models.event.EventChangeCountModel;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.ArrayList;

import static com.cdkj.myxb.module.user.UserHelper.S;
import static com.cdkj.myxb.module.user.UserHelper.T;

/**
 * 我的预约界面
 * Created by cdkj on 2018/2/26.
 */

public class MyAppointmentActivity extends AbsBaseLoadActivity {

    private ActivityMyAppointmentBinding mBinding;

    private EventChangeCountModel model;

    private String mType = "";


    public static void open(Context context, String type, EventChangeCountModel model) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyAppointmentActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("model", model);
        context.startActivity(intent);
    }


    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_my_appointment, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBinding.framImgBack.setOnClickListener(view -> finish());
        mBinding.flRight.setVisibility(View.VISIBLE);
        mBinding.flRight.setOnClickListener(view -> AppointmentManagerActivity.open(this));
        showPage();

        initViewPager();
        initTopListener();


    }

    private void showPage() {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("type");
            model = (EventChangeCountModel) getIntent().getSerializableExtra("model");

            if (TextUtils.equals(mType, UserHelper.C)){
                mBinding.tvTitle.setVisibility(View.GONE);
                mBinding.llTitleTypeC.setVisibility(View.VISIBLE);
            }

            switch (mType) {

                case T:
                    mBinding.viewpagerAppointment.setCurrentItem(0);
                    break;

                case S:
                    mBinding.viewpagerAppointment.setCurrentItem(1);
                    break;
            }

        }
    }

    private void initTopListener() {

        mBinding.topLayout.radiogroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.radio_shopper:
                    mBinding.viewpagerAppointment.setCurrentItem(0, false);
                    break;

                case R.id.radio_experts:
                    mBinding.viewpagerAppointment.setCurrentItem(1, false);
                    break;
            }
        });

    }

    private void initViewPager() {
        ArrayList fragments = new ArrayList<>();

        if (model != null){
            if (TextUtils.equals(mType, UserHelper.C)){
                fragments.add(AppointmentTabLayoutFragment.getInstance(T, model));//服务团队
                fragments.add(AppointmentTabLayoutFragment.getInstance(S, model));//专家
            }else {
                fragments.add(AppointmentTabLayoutFragment.getInstance(mType, model));//服务团队
            }
        }




        mBinding.viewpagerAppointment.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpagerAppointment.setOffscreenPageLimit(fragments.size());
    }


}
