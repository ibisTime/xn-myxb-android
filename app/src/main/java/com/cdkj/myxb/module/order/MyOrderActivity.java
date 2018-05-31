package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.adapters.TablayoutAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityOrderTabBinding;
import com.cdkj.myxb.models.event.EventChangeCountModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 * Created by cdkj on 2018/2/10.
 */

public class MyOrderActivity extends AbsBaseLoadActivity {


    protected ActivityOrderTabBinding mTabLayoutBinding;

    private int toPayCount;
    private int toReceiveCount;

    /*Tablayout 适配器*/
    protected TablayoutAdapter tablayoutAdapter;

    public static void open(Context context, int toPayCount, int toReceiveCount) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyOrderActivity.class);
        intent.putExtra("toPayCount", toPayCount);
        intent.putExtra("toReceiveCount", toReceiveCount);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mTabLayoutBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_order_tab, null, false);
        return mTabLayoutBinding.getRoot();

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
        mBaseBinding.titleView.setMidTitle(getString(R.string.my_order_all));

        if (getIntent() != null){
            toPayCount = getIntent().getIntExtra("toPayCount", 0);
            toReceiveCount = getIntent().getIntExtra("toReceiveCount", 0);

            setView();
        }

        initViewPager();
    }

    private void setView() {
        mTabLayoutBinding.tvNumPay.setText(toPayCount+"");
        mTabLayoutBinding.tvNumPay.setVisibility(toPayCount== 0 ? View.GONE : View.VISIBLE) ;

        mTabLayoutBinding.tvNumReceive.setText(toReceiveCount+"");
        mTabLayoutBinding.tvNumReceive.setVisibility(toReceiveCount== 0 ? View.GONE : View.VISIBLE) ;
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderListFragment.getInstance("", true));
        fragments.add(OrderListFragment.getInstance(OrderHelper.ORDER_WAITE_PAY, false));
        fragments.add(OrderListFragment.getInstance(OrderHelper.ORDER_PAYED, false));
        fragments.add(OrderListFragment.getInstance(OrderHelper.ORDER_WAITE_GET, false));
        fragments.add(OrderListFragment.getInstance(OrderHelper.ORDER_WAITE_COMMENT, false));
        fragments.add(OrderListFragment.getInstance(OrderHelper.ORDERDONE, false));
        return fragments;
    }

    public List<String> getFragmentTitles() {
        List<String> titleList = new ArrayList<>();

        titleList.add("全部");
        titleList.add("待付款");
        titleList.add("待发货");
        titleList.add("待收货");
        titleList.add("待评价");
        titleList.add("已完成");

        return titleList;
    }

    protected void initViewPager() {

        tablayoutAdapter = new TablayoutAdapter(getSupportFragmentManager());

        List<Fragment> mFragments = getFragments();
        List<String> mTitles = getFragmentTitles();

        tablayoutAdapter.addFrag(mFragments, mTitles);

        mTabLayoutBinding.viewpager.setAdapter(tablayoutAdapter);
        mTabLayoutBinding.tablayout.setupWithViewPager(mTabLayoutBinding.viewpager);        //viewpager和tablayout关联
        mTabLayoutBinding.viewpager.setOffscreenPageLimit(tablayoutAdapter.getCount());
//        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置滑动模式 /TabLayout.MODE_SCROLLABLE 可滑动 ，TabLayout.MODE_FIXED表示不可滑动
    }

    @Subscribe
    public void doChangeCount(EventChangeCountModel model){
        if (model == null)
            return;

        toPayCount = model.getToPayCount();
        toReceiveCount = model.getToReceiceCount();

        setView();
    }

}
