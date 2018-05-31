package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.myxb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评价
 * Created by cdkj on 2018/2/28.
 */

public class MyCommentsAllActivity extends CommonTablayoutActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyCommentsAllActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle("我的评论");
    }

    @Override
    public List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(MyCommentsListFragment.getInstance(UserHelper.T));
        fragments.add(MyCommentsListFragment.getInstance(UserHelper.S));
        fragments.add(MyCommentsListFragment.getInstance("P")); // Specialist("S", "专家"), Tutor("T", "服务团队"), PRODUCT("P", "品牌产品"), INT_PRODUCT("IP", "积分产品"), ;

        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {

        List<String> titles = new ArrayList<>();

        titles.add(getString(R.string.shopper));
        titles.add(getString(R.string.experts));
        titles.add(getString(R.string.brand));

        return titles;
    }
}
