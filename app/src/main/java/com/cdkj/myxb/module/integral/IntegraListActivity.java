package com.cdkj.myxb.module.integral;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityIntegralListBinding;
import com.cdkj.myxb.models.AccountModel;
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

    public static void open(Context context, String integral) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegraListActivity.class);
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
                return null;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getAccountNumber(limit, pageindex, isShowDialog);
            }
        });

        getAccountNumber();
    }

    /**
     * 获取账户列表
     */
    public void getAccountNumber() {
        Map<String, String> map = new HashMap<>();
        map.put("currency", "JF");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountList("805353", StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AccountModel>(this) {

            @Override
            protected void onSuccess(List<AccountModel> data, String SucMessage) {
                if (data.size() > 0) {
                    mAccountNum = data.get(0).getAccountNumber();
                    getAccountNumber(10, 1, true);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

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

        Call call = RetrofitUtils.createApi(BaseApiServer.class).stringRequest("805365", StringUtils.getJsonToString(map));

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack(this) {


            @Override
            protected void onSuccess(Object data, String SucMessage) {

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
