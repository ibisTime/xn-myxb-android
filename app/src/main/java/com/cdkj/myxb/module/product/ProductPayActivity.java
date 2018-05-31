package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.pay.WxPayRequestModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityProductPayBinding;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.models.event.EventOpenOrderModel;
import com.cdkj.myxb.models.event.EventPaySuccessDoClose;
import com.cdkj.myxb.models.event.EventWxPaySuc;
import com.cdkj.myxb.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/4.
 */

public class ProductPayActivity extends AbsBaseLoadActivity {


    private static final String MODEL = "model";
    private static final String AMOUNT = "amount";
    private static final String ORDER_CODE = "order_code";

    private ActivityProductPayBinding mBinding;
    private String type;

    private String payType = "1";
    private String orderCode;

    private OrderListModel model;


    /**
     * @param context
     * @param
     */
    public static void open(Context context, String orderCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductPayActivity.class);
        intent.putExtra(ORDER_CODE, orderCode);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_product_pay, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();

        initListener();
    }

    private void init() {

        mBaseBinding.titleView.setMidTitle("支付");

        if (getIntent() == null)
            return;

        orderCode = getIntent().getStringExtra(ORDER_CODE);


        getOrderDetailsRequest();
    }

    private void intImage() {
        mBinding.imgBalance.setBackgroundResource(R.drawable.pay_unchoose);
        mBinding.imgTicket.setBackgroundResource(R.drawable.pay_unchoose);
        mBinding.imgZfb.setBackgroundResource(R.drawable.pay_unchoose);
    }

    private void initListener() {
        mBinding.llTicket.setOnClickListener(view -> {
            intImage();

            payType = "0";
            mBinding.imgTicket.setBackgroundResource(R.drawable.pay_choose);

            mBinding.tvAmount.setText(MoneyUtils.getShowPriceSign(model.getTotalAmount()));
        });

        mBinding.llBalance.setOnClickListener(view -> {
            intImage();

            payType = "1";
            mBinding.imgBalance.setBackgroundResource(R.drawable.pay_choose);

            mBinding.tvAmount.setText(MoneyUtils.getShowPriceSign(model.getAmount()));
        });

        mBinding.tvPay.setOnClickListener(view -> {
            pay();
        });
    }

    private void pay() {
        Map<String, String> object = new HashMap<>();
        object.put("code", orderCode);
        object.put("payType", payType);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().wxPayRequest("805272", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<WxPayRequestModel>(this) {

            @Override
            protected void onSuccess(WxPayRequestModel data, String SucMessage) {
                if (payType.equals("1")||payType.equals("0")){
                    UITipDialog.showSuccess(ProductPayActivity.this, getString(R.string.pay_success), dialogInterface -> {
                        closePay(new EventWxPaySuc());
                    });
                }

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ProductPayActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Subscribe
    public void closePay(EventWxPaySuc eventWxPaySuc){

        // 打开订单界面
        EventBus.getDefault().post(new EventOpenOrderModel());

        // 关闭下单界面
        EventBus.getDefault().post(new EventPaySuccessDoClose());

        finish();
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetailsRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("code", orderCode);
        map.put("userId", SPUtilHelpr.getUserId());

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getOrderDetails("805298", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<OrderListModel>(this) {
            @Override
            protected void onSuccess(OrderListModel data, String SucMessage) {

                model = data;

                setShowData();
                getUserAccount();
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                mBaseBinding.contentView.setShowText(errorMessage);

                disMissLoading();
            }

            @Override
            protected void onFinish() {

            }
        });


    }

    private void setShowData() {

        mBinding.tvAmount.setText(MoneyUtils.getShowPriceSign(model.getAmount()));
//        if (model.getProductOrderList().size() == 0)
//            return;
//
//        if (model.getProductOrderList().get(0).getDiscountRate() == null)
//            return;
//
//        if (model.getProductOrderList().get(0).getDiscountRate() > 0 && model.getProductOrderList().get(0).getDiscountRate() < 1){
//            mBinding.txtBalance.setText("销帮币--" + MoneyUtils.doubleFormatDiscount(Double.parseDouble((model.getProductOrderList().get(0).getDiscountRate() * 10)+""))  + "折");
//        }else {
//            mBinding.txtBalance.setText("销帮币");
//        }

    }

    /**
     * 获取用户信息
     */
    public void getUserAccount() {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("currency", "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountList("802503", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseListCallBack<AccountListModel>(this) {

            @Override
            protected void onSuccess(List<AccountListModel> data, String SucMessage) {

                if (data == null || data.size() == 0)
                    return;

                for (AccountListModel model : data){
                    if (model.getCurrency().equals("JF")){
                        mBinding.txtTicket.setText(mBinding.txtTicket.getText().toString() + "(" + MoneyUtils.showPrice(model.getAmount()) + ")");
                        mBinding.txtTicket.setTag(model.getAccountNumber());
                    }else {
                        mBinding.txtBalance.setText(mBinding.txtBalance.getText().toString()
                                + "(" + MoneyUtils.showPrice(model.getAmount()) + ")");
                        mBinding.txtBalance.setTag(model.getAccountNumber());
                    }

                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
