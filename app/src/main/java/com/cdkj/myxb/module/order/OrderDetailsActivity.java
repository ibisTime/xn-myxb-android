package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionDkeyModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.OrderDetailsListAdapter;
import com.cdkj.myxb.databinding.ActivityIntegralOrderDetailsBinding;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.product.ProductPayActivity;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.order.OrderHelper.ORDER_WAITE_GET;
import static com.cdkj.myxb.module.order.OrderHelper.ORDER_WAITE_PAY;

/**
 * 订单详情
 * Created by cdkj on 2018/2/23.
 */

public class OrderDetailsActivity extends AbsBaseLoadActivity {

    private ActivityIntegralOrderDetailsBinding mBinding;

    private String mOrderCode;

    private static final String ORDERCODE = "code";

    private OrderListModel mOrderListModel;


    /**
     * @param context
     * @param orderCode 订单编号
     */
    public static void open(Context context, String orderCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(ORDERCODE, orderCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_integral_order_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("订单详情");

        if (getIntent() != null) {
            mOrderCode = getIntent().getStringExtra(ORDERCODE);
        }

        mBinding.btnStateDo.setOnClickListener(view -> {

            if (TextUtils.equals(mOrderListModel.getStatus(), OrderHelper.ORDER_WAITE_COMMENT)){
                OrderCommentActivity.open(this, mOrderCode);
            }else if(TextUtils.equals(mOrderListModel.getStatus(), OrderHelper.ORDER_WAITE_PAY)) {
                showDoubleWarnListen("您确定要取消该订单吗？",view1 -> {
                    cancel();
                });
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetailsRequest();
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetailsRequest() {

        if (TextUtils.isEmpty(mOrderCode)) return;

        Map<String, String> map = new HashMap<>();

        map.put("code", mOrderCode);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getOrderDetails("805298", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<OrderListModel>(this) {
            @Override
            protected void onSuccess(OrderListModel data, String SucMessage) {
                if (data == null)
                    return;

                mOrderListModel = data;

                mBaseBinding.contentView.setShowText(null);
                setShowData();
                getCompnayRequest(data.getLogisiticsCompany());
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                mBaseBinding.contentView.setShowText(errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


    /**
     * 获取物流公司
     */
    private void getCompnayRequest(final String key) {

        if (TextUtils.isEmpty(key)) return;

        Map<String, String> map = new HashMap<>();

        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("parentKey", "kd_company");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getdKeyListInfo("805906", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseListCallBack<IntroductionDkeyModel>(this) {
            @Override
            protected void onSuccess(List<IntroductionDkeyModel> data, String SucMessage) {
                boolean isUse = false;
                for (IntroductionDkeyModel model : data) {
                    if (model == null) continue;
                    if (TextUtils.equals(model.getDkey(), key)) {
                        mBinding.tvLogisticscompany.setText(model.getDvalue());
                        isUse = true;
                        break;
                    }
                }
                if (!isUse) {
                    mBinding.tvLogisticscompany.setText("暂无");
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(OrderDetailsActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
            }
        });
    }

    public OrderDetailsListAdapter getListAdapter(OrderListModel data, String payType) {
        OrderDetailsListAdapter orderDetailsListAdapter = new OrderDetailsListAdapter(data.getProductOrderList(), payType);

        return orderDetailsListAdapter;
    }

    /**
     * 设置数据
     *
     */
    private void setShowData() {

        if (mOrderListModel == null) return;

        if (mOrderListModel.getPayType() == null){
            mBinding.rvCartList.setAdapter(getListAdapter(mOrderListModel, "0"));
        }else {
            mBinding.rvCartList.setAdapter(getListAdapter(mOrderListModel, mOrderListModel.getPayType()));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mBinding.rvCartList.setLayoutManager(linearLayoutManager);

        if (TextUtils.equals(mOrderListModel.getPayType(), "1")){
            if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
                mBinding.tvPrice.setText(MoneyUtils.getShowPriceSign(mOrderListModel.getAmount()));
            }else {
                mBinding.tvPrice.setText(MoneyUtils.getShowPriceSign(mOrderListModel.getTotalAmount()));
            }

        }else {
            mBinding.tvPrice.setText(MoneyUtils.getShowPriceSign(mOrderListModel.getTotalAmount()));
        }

        mBinding.tvNum.setText("" + mOrderListModel.getProductOrderList().size());
        mBinding.tvOrderCode.setText(mOrderListModel.getCode());
        mBinding.tvState.setText(OrderHelper.getOrderState(mOrderListModel.getStatus()));
        mBinding.tvOrderTime.setText(DateUtil.formatStringData(mOrderListModel.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        if (OrderHelper.canShowOrderButton(mOrderListModel.getStatus())) {
            mBinding.btnStateDo.setVisibility(View.VISIBLE);
        } else {
            mBinding.btnStateDo.setVisibility(View.GONE);
        }

        mBinding.btnStateDo.setText(OrderHelper.getOrderBtnStateString(mOrderListModel.getStatus()));

        //收货人信息
        if (!TextUtils.isEmpty(mOrderListModel.getReceiver())) {
            mBinding.linUserInfo.setVisibility(View.VISIBLE);
            mBinding.tvUserName.setText("收货人:" + mOrderListModel.getReceiver());
        }
        if (!TextUtils.isEmpty(mOrderListModel.getReMobile())) {
            mBinding.linUserInfo.setVisibility(View.VISIBLE);
            mBinding.tvPhone.setText(mOrderListModel.getReMobile());
        }
        if (!TextUtils.isEmpty(mOrderListModel.getReAddress())) {
            mBinding.tvAddress.setVisibility(View.VISIBLE);
            mBinding.tvAddress.setText("收货地址: "+mOrderListModel.getReAddress());
        }

        //物流信息
        mBinding.tvLogisticscode.setText(mOrderListModel.getLogisiticsCode());

        if (TextUtils.isEmpty(mOrderListModel.getLogisiticsCompany())) {
            mBinding.linLogisticscompany.setVisibility(View.GONE);
        } else {
            mBinding.linLogisticscompany.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(mOrderListModel.getLogisiticsCode())) {
            mBinding.linLogisticscode.setVisibility(View.GONE);
        } else {
            mBinding.linLogisticscode.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(ORDER_WAITE_PAY, mOrderListModel.getStatus())){
            mBaseBinding.titleView.setRightTitle("支付");
            mBaseBinding.titleView.setRightFraClickListener(view -> {
                ProductPayActivity.open(OrderDetailsActivity.this, mOrderListModel.getCode());
            });
        }else if (TextUtils.equals(ORDER_WAITE_GET, mOrderListModel.getStatus())) {
            mBaseBinding.titleView.setRightTitle("确认收货");
            mBaseBinding.titleView.setRightFraClickListener(view -> {
                confirm();
            });
        }else {
            mBaseBinding.titleView.setRightTitle(null);
        }

    }


    /**
     * 确认收货
     */
    private void confirm() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", mOrderCode);
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805299", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(OrderDetailsActivity.this, "确认收货成功",dialogInterface -> {
                    getOrderDetailsRequest();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(OrderDetailsActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 取消订单
     */
    private void cancel() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", mOrderCode);
        map.put("userId", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.getBaseAPiService().successRequest("805273", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(OrderDetailsActivity.this, "取消成功", dialogInterface -> {
                    getOrderDetailsRequest();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(OrderDetailsActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
