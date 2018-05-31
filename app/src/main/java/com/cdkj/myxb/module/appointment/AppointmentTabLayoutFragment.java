package com.cdkj.myxb.module.appointment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.adapters.TablayoutAdapter;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentTabBinding;
import com.cdkj.myxb.models.event.EventChangeCountModel;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_ALL;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DJD;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DLR;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DSH;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_FWZT;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_YWC;

/**
 * 预约订单列表
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentTabLayoutFragment extends BaseLazyFragment {

    protected ActivityAppointmentTabBinding mTabLayoutBinding;

    private EventChangeCountModel model;

    /*Tablayout 适配器*/
    protected TablayoutAdapter tablayoutAdapter;

    public static final String INTENTTYPE = "type";
    private String mType;//用户类型
    private List<String> mTitles;

    public static AppointmentTabLayoutFragment getInstance(String type, EventChangeCountModel model) {
        AppointmentTabLayoutFragment fragment = new AppointmentTabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENTTYPE, type);
        bundle.putSerializable("model", model);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTabLayoutBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_tab, null, false);

        mTitles = new ArrayList<>();
        if (getArguments() != null) {
            mType = getArguments().getString(INTENTTYPE);
            model = (EventChangeCountModel) getArguments().getSerializable("model");

            setView();
        }



        initViewPager();

        return mTabLayoutBinding.getRoot();
    }


    private void setView() {
        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){

            mTabLayoutBinding.llCover.setVisibility(View.GONE);

            mTabLayoutBinding.tvNum3.setText(model.getJxsToApproveCount()+"");
            mTabLayoutBinding.tvNum3.setVisibility(model.getJxsToApproveCount()== 0 ? View.GONE : View.VISIBLE) ;

        }else {

            mTabLayoutBinding.tvNum2.setText(model.getFwToBookCount()+"");
            mTabLayoutBinding.tvNum2.setVisibility(model.getFwToBookCount()== 0 ? View.GONE : View.VISIBLE) ;

            mTabLayoutBinding.tvNum3.setText(model.getFwInputCount()+"");
            mTabLayoutBinding.tvNum3.setVisibility(model.getFwInputCount()== 0 ? View.GONE : View.VISIBLE) ;

        }


    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    protected void initViewPager() {

        tablayoutAdapter = new TablayoutAdapter(getChildFragmentManager());

        List<Fragment> mFragments = getFragments();
        List<String> mTitles = getFragmentTitles();

        tablayoutAdapter.addFrag(mFragments, mTitles);

        mTabLayoutBinding.viewpager.setAdapter(tablayoutAdapter);
        mTabLayoutBinding.tablayout.setupWithViewPager(mTabLayoutBinding.viewpager);        //viewpager和tablayout关联
        mTabLayoutBinding.viewpager.setOffscreenPageLimit(3);
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(AppointmentListFragment.getInstance(OrderHelper.APPOINTMENTALL, mType, true));
        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
            fragments.add(AppointmentListFragment.getInstance(APPOINTMENT_FWZT, mType, false));
        }else {
            fragments.add(AppointmentListFragment.getInstance(APPOINTMENT_DJD, mType, false));
            fragments.add(AppointmentListFragment.getInstance(APPOINTMENT_DLR, mType, false));
        }
        fragments.add(AppointmentListFragment.getInstance(APPOINTMENT_DSH, mType, false));
        fragments.add(AppointmentListFragment.getInstance(APPOINTMENT_YWC, mType, false));

        return fragments;
    }

    public List<String> getFragmentTitles() {

        mTitles.add(APPOINTMENT_ALL);
        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
            mTitles.add(APPOINTMENT_FWZT);
        }else {
            mTitles.add(APPOINTMENT_DJD);
            mTitles.add(APPOINTMENT_DLR);
        }
        mTitles.add(APPOINTMENT_DSH);
        mTitles.add(APPOINTMENT_YWC);

        return mTitles;
    }

    @Subscribe
    public void doChangeCount(EventChangeCountModel model){
        if (model == null)
            return;

        this.model = model;

        setView();
    }

}
