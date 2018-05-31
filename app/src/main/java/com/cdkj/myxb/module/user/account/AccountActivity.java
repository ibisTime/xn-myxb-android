package com.cdkj.myxb.module.user.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.cdkj.myxb.adapters.AccountBillAdapter;
import com.cdkj.myxb.databinding.ActivityAccountBinding;
import com.cdkj.myxb.models.IntegralListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/3.
 */

public class AccountActivity extends AbsBaseLoadActivity {

    private ActivityAccountBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private String balance;
    private String accountNumber;

    public static void open(Context context,String accountNumber, String balance) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra("balance", balance);
        intent.putExtra("accountNumber", accountNumber);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_account, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        initListener();

    }

    private void init() {
        mBaseBinding.titleView.setMidTitle("我的账户");

        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C))
            mBinding.tvWithdraw.setVisibility(View.GONE);

        if (getIntent() == null)
            return;

        balance = getIntent().getStringExtra("balance");
        accountNumber = getIntent().getStringExtra("accountNumber");

        initRefreshHelper();
    }

    private void initListener() {
        mBinding.tvAdd.setOnClickListener(view -> {
            ChargeActivity.open(this, accountNumber);
        });

        mBinding.tvWithdraw.setOnClickListener(view -> {
            WithdrawActivity.open(this, accountNumber, balance);
        });
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

                AccountBillAdapter billAdapter = new AccountBillAdapter(listData);

                return billAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getAccountBill(limit, pageindex, true);
            }
        });
        mRefreshHelper.init(10);
        mRefreshHelper.onDefaluteMRefresh(true);
    }

    /**
     * 获取流水
     */
    public void getAccountBill(int limit, int start, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("accountNumber", accountNumber);
        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getIntegralList("802524", StringUtils.getJsonToString(map));

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<IntegralListModel>>(this) {
            @Override
            protected void onSuccess(ResponseInListModel<IntegralListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无流水", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }



}
