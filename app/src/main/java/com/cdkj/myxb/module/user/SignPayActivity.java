package com.cdkj.myxb.module.user;

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
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivitySignPayBinding;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.event.EventSignPaySuccessDoClose;
import com.cdkj.myxb.models.event.EventWxPaySuc;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.weight.AliPayUtil;
import com.cdkj.myxb.weight.WxUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/4.
 */

public class SignPayActivity extends AbsBaseLoadActivity {


    private static final String MODEL = "model";
    private static final String AMOUNT = "amount";
    private static final String ORDER_CODE = "order_code";

    private ActivitySignPayBinding mBinding;
    private String type;

    private String payType = "1";


    /**
     * @param context
     * @param
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SignPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_sign_pay, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        getKeyUrl();
        initListener();
        getUserAccount(true);
    }

    private void init() {

        mBaseBinding.titleView.setMidTitle("支付");
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
        object.put("payType", payType);
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().wxAliPayRequest("805190", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<WxAliPayRequestModel>(this) {

            @Override
            protected void onSuccess(WxAliPayRequestModel data, String SucMessage) {

                if (payType.equals("1")){
                    UITipDialog.showSuccess(SignPayActivity.this, getString(R.string.pay_success), dialogInterface -> {
//                        // 关闭签约界面
//                        EventBus.getDefault().post(new EventSignPaySuccessDoClose());
//
//                        finish();

                        closePay(new EventWxPaySuc());
                    });
                }else if (payType.equals("2")) {
                    if (WxUtil.check(SignPayActivity.this)) {
                        WxUtil.pay(SignPayActivity.this, data);
                    }

                }else {
                    AliPayUtil.AliPay(SignPayActivity.this, data.getSignOrder());
                }


            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(SignPayActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void getKeyUrl() {

        Map<String, String> map = new HashMap<>();
        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
            map.put("ckey", "SG_MRY_PAY_AMOUNT");
        }else if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.T)){
            map.put("ckey", "SG_MD_PAY_AMOUNT");
        }
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
                UITipDialog.showFall(SignPayActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 获取用户信息
     */
    public void getUserAccount(boolean isShowDialog) {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("currency", "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountList("802503", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AccountListModel>(this) {

            @Override
            protected void onSuccess(List<AccountListModel> data, String SucMessage) {

                if (data == null || data.size() == 0)
                    return;

                for (AccountListModel model : data){
                    if (!model.getCurrency().equals("JF")){
                        mBinding.tvBalance.setText("销帮币("+MoneyUtils.showPrice(model.getAmount())+")");
                    }

                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Subscribe
    public void closePay(EventWxPaySuc eventWxPaySuc){
        // 关闭签约界面
        EventBus.getDefault().post(new EventSignPaySuccessDoClose());

        finish();
    }

}
