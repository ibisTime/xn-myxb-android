package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.module.maintab.AdviceFragment;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.HelpCenterFragment;
import com.cdkj.myxb.module.maintab.InvitationFriendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 * Created by cdkj on 2018/2/10.
 */

public class MyOrderActivity extends CommonTablayoutActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle(getString(R.string.my_order_all));
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(FirstPageFragment.getInstanse());//首页
        fragments.add(HelpCenterFragment.getInstanse());//帮助中心
        fragments.add(InvitationFriendFragment.getInstanse());//邀请好友
        fragments.add(AdviceFragment.getInstanse());//建议评价

        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> titleList = new ArrayList<>();

        titleList.add("全部");
        titleList.add("待审核");
        titleList.add("待评价");
        titleList.add("已完成");

        return titleList;
    }
}
