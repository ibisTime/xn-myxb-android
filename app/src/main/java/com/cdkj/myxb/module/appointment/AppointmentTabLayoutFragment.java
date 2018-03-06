package com.cdkj.myxb.module.appointment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.myxb.module.common.CommonTablayoutFragment;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约订单列表
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentTabLayoutFragment extends CommonTablayoutFragment {


    public static final String INTENTTYPE = "type";
    private String mType;//用户类型
    private List<String> mTitles;

    public static AppointmentTabLayoutFragment getInstanse(String type) {
        AppointmentTabLayoutFragment fragment = new AppointmentTabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENTTYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitles = new ArrayList<>();
        if (getArguments() != null) {
            mType = getArguments().getString(INTENTTYPE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void afterCreate() {
        if (mTitles.size() <= 5) {
            mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }


    /*1, 待审核, 2, 已排班待上门,3, 无档期, 4, 已上门待下课, 5, 已下课待录入,6, 已录入;*/
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENTALL, mType, true));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_1, mType, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_2, mType, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_4, mType, false));
        if (OrderHelper.canShowWaiteInputByUserType(mType)) {
            fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_5, mType, false));
        }
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_6, mType, false));
//        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_3, mType, false));


        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {

        mTitles.add("全部");
        mTitles.add("待审核");
        mTitles.add("待上门");
        mTitles.add("待下课");
        if (OrderHelper.canShowWaiteInputByUserType(mType)) {
            mTitles.add("待录入");
        }
        mTitles.add("已完成");
//        strings.add("已取消");
        return mTitles;
    }
}
