package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预约界面
 * Created by cdkj on 2018/2/26.
 */

public class ResultsOrderActivity extends CommonTablayoutActivity {
    private String mType = UserHelper.S;//用户类型

    /**
     * @param context
     * @param type    用户类型
     */
    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultsOrderActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("type");
        }

        initViewPager();

        mBaseBinding.titleView.setMidTitle("成果订单");
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENTALL, mType, true));
        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_1, mType, false));
        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_2, mType, false));
        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_4, mType, false));
        if (OrderHelper.canShowWaiteInputByUserType(mType)) {                                                                      //美导没有待录入状态
            fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_5, mType, false));
        }
        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_6, mType, false));
//        fragments.add(ResultsOrderListFragment.getInstanse(OrderHelper.APPOINTMENT_3, mType, false));


        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {

        List<String> strings = new ArrayList<>();

        strings.add("全部");
        strings.add("待审核");
        strings.add("待上门");
        strings.add("待下课");
        if (OrderHelper.canShowWaiteInputByUserType(mType)) {
            strings.add("待录入");
        }
        strings.add("已完成");
//        strings.add("已取消");
        return strings;
    }
}
