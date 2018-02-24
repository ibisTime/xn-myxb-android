package com.cdkj.myxb.module.integral;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.IntegraMallListAdapter;
import com.cdkj.myxb.databinding.ActivityIntegralMallBinding;
import com.cdkj.myxb.models.AccountDetailsModel;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.IntegralModel;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.integral.order.MyIntegralOrderActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 积分商城
 * Created by 李先俊 on 2018/2/22.
 */

public class IntegralMallActivity extends AbsBaseLoadActivity {

    private ActivityIntegralMallBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private static final String USERDATA = "user";

    private UserModel mUserModel;
    private String mAccountNum;

    /**
     * @param context
     * @param userModel 用户信息
     */
    public static void open(Context context, UserModel userModel) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegralMallActivity.class);
        intent.putExtra(USERDATA, userModel);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_integral_mall, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.integral_mall));

        if (getIntent() != null) {
            mUserModel = getIntent().getParcelableExtra(USERDATA);
        }

        setShowData();

        initRefreHelper();

        mRefreshHelper.onDefaluteMRefresh(true);

        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccountNumber();
    }

    private void initListener() {
        //积分规则
        mBinding.layoutMallHeader.tvIntegralRules.setOnClickListener(view -> WebViewActivity.openkey(this, "积分规则", "dd"));

        //积分列表
        mBinding.orderLayout.fraIntegralList.setOnClickListener(view -> IntegraListActivity.open(this, mAccountNum, mBinding.layoutMallHeader.tvMyintegral.getText().toString()));

        //积分订单
        mBinding.orderLayout.fraIntegralOrder.setOnClickListener(view -> {
            MyIntegralOrderActivity.open(this);
        });
    }


    /**
     * 设置显示数据
     */
    private void setShowData() {

        if (mUserModel == null) return;

        mBinding.layoutMallHeader.tvUserName.setText(mUserModel.getRealName());
        ImgUtils.loadLogo(this, MyCdConfig.QINIUURL + mUserModel.getPhoto(), mBinding.layoutMallHeader.imgUserLogo);

        if (mUserModel.isMan()) {
            mBinding.layoutMallHeader.imgGender.setImageResource(R.drawable.man);
        } else {
            mBinding.layoutMallHeader.imgGender.setImageResource(R.drawable.man_2);
        }


    }

    private void initRefreHelper() {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerViewIntegral;
            }


            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getIntegraMallListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getIntegralProductListRequest(pageindex, limit, isShowDialog);
            }

        });

        mRefreshHelper.init(10);

        mRefreshHelper.setLayoutManager(new ScrollGridLayoutManager(this, 2));
    }


    /**
     * 获取账户列表
     */
    public void getAccountNumber() {

        if (!TextUtils.isEmpty(mAccountNum)) {
            getIntegral();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("currency", "JF");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountList("805353", StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AccountListModel>(this) {

            @Override
            protected void onSuccess(List<AccountListModel> data, String SucMessage) {
                if (data.size() > 0) {
                    mAccountNum = data.get(0).getAccountNumber();
                    getIntegral();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 获取积分
     */
    public void getIntegral() {
        if (TextUtils.isEmpty(mAccountNum)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("accountNumber", mAccountNum);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountDetails("805352", StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<AccountDetailsModel>(this) {

            @Override
            protected void onSuccess(AccountDetailsModel data, String SucMessage) {
                mBinding.layoutMallHeader.tvMyintegral.setText(BigDecimalUtils.intValue(data.getAmount()) + "");
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 获取数据适配器
     *
     * @param listData
     * @return
     */
    @NonNull
    private IntegraMallListAdapter getIntegraMallListAdapter(List listData) {
        IntegraMallListAdapter integraMallListAdapter = new IntegraMallListAdapter(listData);

        integraMallListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntegralModel integralModel = integraMallListAdapter.getItem(position);
                if (integralModel == null) return;
                IntegralProductDetailsActivity.open(IntegralMallActivity.this, integralModel.getCode());
            }
        });

        return integraMallListAdapter;
    }

    /**
     * 获取积分产品列表
     */
    public void getIntegralProductListRequest(int start, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("status", "2"); //2已上架

        Call call = RetrofitUtils.createApi(MyApiServer.class).getIntegralProductList("805285", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<IntegralModel>>(this) {
            @Override
            protected void onSuccess(ResponseInListModel<IntegralModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无积分商品", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
