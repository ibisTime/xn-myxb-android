package com.cdkj.myxb.module.integral;

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
import com.cdkj.myxb.adapters.IntegralListAdapter;
import com.cdkj.myxb.databinding.ActivityIntegralListBinding;
import com.cdkj.myxb.models.IntegralListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * c积分流水界面
 * Created by cdkj on 2018/2/23.
 */

public class IntegraListActivity extends AbsBaseLoadActivity {

    private ActivityIntegralListBinding mBinding;
    private String mAccountNum;//积分账户

    private RefreshHelper mRefreshHelper;

    public static void open(Context context, String accountNum, String integral) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegraListActivity.class);
        intent.putExtra("accountNum", accountNum);
        intent.putExtra("integral", integral);
        context.startActivity(intent);

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_integral_list, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("积分列表");

        if (getIntent() != null) {
            mBinding.tvNum.setText(getIntent().getStringExtra("integral"));
            mAccountNum = getIntent().getStringExtra("accountNum");
        }

        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.layoutRefresh.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.layoutRefresh.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return new IntegralListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getAccountNumber(limit, pageindex, isShowDialog);
            }
        });

        mRefreshHelper.init(10);

        mRefreshHelper.onDefaluteMRefresh(true);

    }


    /**
     * 获取流水
     */
    public void getAccountNumber(int limit, int start, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("currency", "JF");
        map.put("accountNumber", mAccountNum);
        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getIntegralList("805365", StringUtils.getJsonToString(map));

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<IntegralListModel>>(this) {
            @Override
            protected void onSuccess(ResponseInListModel<IntegralListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无积分流水", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
