package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityMyAppointmentBinding;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.MyFragment;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import static com.cdkj.myxb.module.user.UserHelper.C;
import static com.cdkj.myxb.module.user.UserHelper.L;
import static com.cdkj.myxb.module.user.UserHelper.S;
import static com.cdkj.myxb.module.user.UserHelper.T;

/**
 * 我的预约界面
 * Created by 李先俊 on 2018/2/26.
 */

public class MyAppointmentActivity extends AbsBaseLoadActivity {

    private ActivityMyAppointmentBinding mBinding;

    private String mType = "";


    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyAppointmentActivity.class);
        intent.putExtra("type", type);
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
        initViewPager();
        initTopListener();

        showPage();

    }

    private void showPage() {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("type");

            switch (mType) {

                case T:
                    mBinding.viewpagerAppointment.setCurrentItem(0);
                    break;
                case L:
                    mBinding.viewpagerAppointment.setCurrentItem(1);
                    break;

                case S:
                    mBinding.viewpagerAppointment.setCurrentItem(2);
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
                case R.id.radio_teacher:
                    mBinding.viewpagerAppointment.setCurrentItem(1, false);
                    break;
                case R.id.radio_experts:
                    mBinding.viewpagerAppointment.setCurrentItem(2, false);
                    break;
            }
        });

    }

    private void initViewPager() {
        ArrayList fragments = new ArrayList<>();
        fragments.add(AppointmentTabLayoutFragment.getInstanse(T));//美导
        fragments.add(AppointmentTabLayoutFragment.getInstanse(L));//讲师
        fragments.add(AppointmentTabLayoutFragment.getInstanse(S));//专家

        mBinding.viewpagerAppointment.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpagerAppointment.setOffscreenPageLimit(fragments.size());
    }


}
