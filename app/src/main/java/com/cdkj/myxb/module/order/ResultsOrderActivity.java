package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityMyAppointmentBinding;
import com.cdkj.myxb.module.appointment.AppointmentListFragment;
import com.cdkj.myxb.module.appointment.AppointmentTabLayoutFragment;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.MyFragment;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预约界面
 * Created by cdkj on 2018/2/26.
 */

public class ResultsOrderActivity extends CommonTablayoutActivity {
    private String mType = UserHelper.S;//用户类型

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultsOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle("成果订单");
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
