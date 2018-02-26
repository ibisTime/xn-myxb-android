package com.cdkj.myxb.module.appointment;

import android.support.v4.app.Fragment;

import com.cdkj.myxb.module.common.CommonTablayoutFragment;
import com.cdkj.myxb.module.order.OrderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2018/2/26.
 */

public class AppointmentTabLayoutFragment extends CommonTablayoutFragment {

    public static AppointmentTabLayoutFragment getInstanse() {
        AppointmentTabLayoutFragment fragment = new AppointmentTabLayoutFragment();
        return fragment;
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

        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENTALL, true));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_1, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_2, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_4, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_5, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_6, false));
        fragments.add(AppointmentListFragment.getInstanse(OrderHelper.APPOINTMENT_3, false));


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
