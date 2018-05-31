package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.adapters.TablayoutAdapter;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityResultBinding;
import com.cdkj.myxb.models.ResultAmount;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/11.
 */

public class ResultActivity extends AbsBaseLoadActivity {

    private ActivityResultBinding mBinding;

    /*Tablayout 适配器*/
    protected TablayoutAdapter tablayoutAdapter;

    private String mType = "";//用户类型

    /**
     * @param context
     * @param type    用户类型
     */
    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result,null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("我的成果");
        mBaseBinding.titleView.setRightTitle("成果明细");
        mBaseBinding.titleView.setRightFraClickListener(view -> {
            ResultsOrderActivity.open(this, mType);
        });


        init();
        getSaleAmount();
    }

    private void init() {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("type");
        }

//        if (OrderHelper.canShowWaiteInputByUserType(mType)) { // 服务团队没有待录入状态
//            mBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        }else{
//            mBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
//        }

//        initViewPager();
    }

    protected void initViewPager() {

        tablayoutAdapter = new TablayoutAdapter(getSupportFragmentManager());

        List<Fragment> mFragments = getFragments();
        List<String> mTitles = getFragmentTitles();

        tablayoutAdapter.addFrag(mFragments, mTitles);

        mBinding.viewpager.setAdapter(tablayoutAdapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpager); //viewpager和tablayout关联
        mBinding.viewpager.setOffscreenPageLimit(tablayoutAdapter.getCount());
//        mBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置滑动模式 /TabLayout.MODE_SCROLLABLE 可滑动 ，TabLayout.MODE_FIXED表示不可滑动
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENTALL, mType, true));
        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_1, mType, false));
        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_2, mType, false));
        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_4, mType, false));
        if (OrderHelper.canShowWaiteInputByUserType(mType)) { //服务团队没有待录入状态
            fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_5, mType, false));
        }
        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_6, mType, false));

//        fragments.add(ResultsOrderListFragment.getInstance(OrderHelper.APPOINTMENT_3, mType, false));


        return fragments;
    }

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

    private void getSaleAmount() {
        Map<String, String> object = new HashMap<>();
        object.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getResultAmount("805522", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResultAmount>(this) {

            @Override
            protected void onSuccess(ResultAmount data, String SucMessage) {
                mBinding.tvNum.setText(MoneyUtils.getShowPriceSign(data.getSaleAmount()));

            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
