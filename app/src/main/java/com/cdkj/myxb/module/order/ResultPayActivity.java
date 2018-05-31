package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.model.pay.WxAliPayRequestModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityResultPayBinding;
import com.cdkj.myxb.models.event.EventResultPaySuccessDoClose;
import com.cdkj.myxb.models.event.EventWxPaySuc;
import com.cdkj.myxb.weight.AliPayUtil;
import com.cdkj.myxb.weight.WxUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/4.
 */

public class ResultPayActivity extends AbsBaseLoadActivity {


    private static final String MODEL = "model";
    private static final String AMOUNT = "amount";
    private static final String ORDER_CODE = "order_code";

    private ActivityResultPayBinding mBinding;
    private String type;

    private String code;
    private String deductAmount;
    private String payType = "2";


    /**
     * @param context
     * @param
     */
    public static void open(Context context,String code, String deductAmount) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultPayActivity.class);
        intent.putExtra("code",code);
        intent.putExtra("deductAmount",deductAmount);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result_pay, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        getKeyUrl();
        initListener();
    }

    private void init() {
        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra("code");
        deductAmount = getIntent().getStringExtra("deductAmount");

        mBaseBinding.titleView.setMidTitle("支付");

        mBinding.tvAmount.setText(deductAmount);
    }


    private void intImage() {
        mBinding.imgBalance.setBackgroundResource(R.drawable.pay_unchoose);
        mBinding.imgWx.setBackgroundResource(R.drawable.pay_unchoose);
        mBinding.imgZfb.setBackgroundResource(R.drawable.pay_unchoose);
    }

    private void initListener() {
        mBinding.llBalance.setOnClickListener(view -> {
            intImage();

            payType = "1";
            mBinding.imgBalance.setBackgroundResource(R.drawable.pay_choose);
        });

        mBinding.llWx.setOnClickListener(view -> {
            intImage();

            payType = "2";
            mBinding.imgWx.setBackgroundResource(R.drawable.pay_choose);
        });

        mBinding.llZfb.setOnClickListener(view -> {
            intImage();

            payType = "3";
            mBinding.imgZfb.setBackgroundResource(R.drawable.pay_choose);
        });

        mBinding.tvPay.setOnClickListener(view -> {
            pay();
        });
    }

    private void pay() {
        Map<String, String> object = new HashMap<>();
        object.put("code", code);
        object.put("payType", payType);
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().wxAliPayRequest("805518", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<WxAliPayRequestModel>(this) {

            @Override
            protected void onSuccess(WxAliPayRequestModel data, String SucMessage) {

                if (payType.equals("1")){
                    UITipDialog.showSuccess(ResultPayActivity.this, getString(R.string.pay_success), dialogInterface -> {


                        closePay(new EventWxPaySuc());
                    });
                }else if (payType.equals("2")) {
                    if (WxUtil.check(ResultPayActivity.this)) {
                        WxUtil.pay(ResultPayActivity.this, data);
                    }

                }else {
                    AliPayUtil.AliPay(ResultPayActivity.this, data.getSignOrder());
                }




            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ResultPayActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void getKeyUrl() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "deductAmount");
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }
                mBinding.tvAmount.setText(MoneyUtils.MONEYSING+data.getCvalue());
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ResultPayActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Subscribe
    public void closePay(EventWxPaySuc eventWxPaySuc){
        // 关闭审核界面
        EventBus.getDefault().post(new EventResultPaySuccessDoClose());

        finish();
    }

}
