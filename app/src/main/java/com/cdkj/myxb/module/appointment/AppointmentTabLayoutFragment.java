package com.cdkj.myxb.module.appointment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.myxb.module.common.CommonTablayoutFragment;
import com.cdkj.myxb.module.order.OrderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2018/2/26.
 */

public class AppointmentTabLayoutFragment extends CommonTablayoutFragment {


    public static final String INTENTTYPE = "type";
    private String mType;//用户类型

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
        if (getArguments() != null) {
            mType = getArguments().getString(INTENTTYPE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
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
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_5, mType, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_6, mType, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_3, mType, false));


        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {

        List<String> strings = new ArrayList<>();

        strings.add("全部");
        strings.add("待审核");
        strings.add("待上门");
        strings.add("待下课");
        strings.add("待录入");
        strings.add("已完成");
        strings.add("已取消");
        return strings;
    }
}
