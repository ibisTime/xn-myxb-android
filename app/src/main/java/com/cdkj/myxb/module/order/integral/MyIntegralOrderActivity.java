package com.cdkj.myxb.module.order.integral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.module.maintab.AdviceFragment;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.HelpCenterFragment;
import com.cdkj.myxb.module.maintab.InvitationFriendFragment;
import com.cdkj.myxb.module.order.OrderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的积分订单
 * Created by cdkj on 2018/2/10.
 */

public class MyIntegralOrderActivity extends CommonTablayoutActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyIntegralOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle(getString(R.string.my_integral_order));
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(IntegralOrderListFragment.getInstanse(OrderHelper.INTEGRALORDERWAITESEND));
        fragments.add(IntegralOrderListFragment.getInstanse(OrderHelper.INTEGRALORDERWAITEGET));
        fragments.add(IntegralOrderListFragment.getInstanse(OrderHelper.INTEGRALORDERWAITEEVALUATION));
        fragments.add(IntegralOrderListFragment.getInstanse(OrderHelper.INTEGRALORDERDONE));
        fragments.add(IntegralOrderListFragment.getInstanse(OrderHelper.INTEGRALORDERCANCEL));

        return fragments;
    }

    /* 0待发货，1待收货，2待评价，3已完成，4无货取消*/

    @Override
    public List<String> getFragmentTitles() {
        List<String> titleList = new ArrayList<>();

        titleList.add("待发货");
        titleList.add("待收货");
        titleList.add("待评价");
        titleList.add("已完成");
        titleList.add("已取消");

        return titleList;
    }
}
