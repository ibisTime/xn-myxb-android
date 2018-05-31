package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AppointmentManagerAdapter;
import com.cdkj.myxb.databinding.ActivityAppointmentManagerBinding;
import com.cdkj.myxb.models.TripListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/23.
 */

public class AppointmentManagerActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentManagerBinding mBinding;

    private RefreshHelper mRefreshHelper;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_manager, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("行程管理");
        mBaseBinding.titleView.setRightTitle("行程日历");
        mBaseBinding.titleView.setRightFraClickListener(view -> AppointmentCheckActivity.open(this, SPUtilHelpr.getUserId()));

        initListener();
        initRefreshHelper();
    }

    private void initListener() {
        mBinding.tvAdd.setOnClickListener(view -> {
            AppointmentAddActivity.open(this, null);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshHelper.onDefaluteMRefresh(true);
    }

    /**
     * 初始化刷新相关
     */
    private void initRefreshHelper() {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }


            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {

                AppointmentManagerAdapter managerAdapter = new AppointmentManagerAdapter(listData);

                managerAdapter.setOnItemClickListener((adapter, view, position) -> {
                    TripListModel model = managerAdapter.getItem(position);

                    AppointmentAddActivity.open(AppointmentManagerActivity.this, model.getCode());
                });

                return managerAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getAppointmentRequest(pageindex, limit, true);
            }
        });
        mRefreshHelper.init(10);
        mRefreshHelper.onDefaluteMRefresh(true);
    }

    /**
     * 获取行程请求
     *
     * @param isShowDialog
     */
    public void getAppointmentRequest(int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getTripList("805505", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<TripListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<TripListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无行程", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
