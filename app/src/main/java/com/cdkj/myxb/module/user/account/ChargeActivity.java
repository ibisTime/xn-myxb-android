package com.cdkj.myxb.module.user.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.pay.WxAliPayRequestModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityChargeBinding;
import com.cdkj.myxb.models.event.EventWxPaySuc;
import com.cdkj.myxb.weight.AliPayUtil;
import com.cdkj.myxb.weight.WxUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/8.
 */

public class ChargeActivity extends AbsBaseLoadActivity {

    private ActivityChargeBinding mBinding;

    private static final String ORDER_CODE = "accountNumber";

    private String accountNumber;

    private String payType = "36";


    /**
     * @param context
     * @param
     */
    public static void open(Context context, String accountNumber) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ChargeActivity.class);
        intent.putExtra(ORDER_CODE, accountNumber);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_charge, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        initListener();
        initEditText();
    }

    private void init() {

        mBaseBinding.titleView.setMidTitle("充值");

        if (getIntent() == null)
            return;

        accountNumber = getIntent().getStringExtra(ORDER_CODE);

        initView();
    }

    private void initView() {



    }

    private void initEditText() {
        mBinding.edtPrice.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        //设置字符过滤
        mBinding.edtPrice.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});

    }

    private void intImage() {
        mBinding.imgWx.setBackgroundResource(R.drawable.pay_unchoose);
        mBinding.imgZfb.setBackgroundResource(R.drawable.pay_unchoose);
    }

    private void initListener() {
        mBinding.llWx.setOnClickListener(view -> {
            intImage();

            payType = "36";
            mBinding.imgWx.setBackgroundResource(R.drawable.pay_choose);
        });

        mBinding.llZfb.setOnClickListener(view -> {
            intImage();

            payType = "30";
            mBinding.imgZfb.setBackgroundResource(R.drawable.pay_choose);
        });

        mBinding.tvPay.setOnClickListener(view -> {
            if (check()){
                charge();
            }
        });
    }

    private boolean check() {
        if (Double.parseDouble(mBinding.edtPrice.getText().toString().trim()) == 0.0) {
            ToastUtil.show(this, "金额必须大于等于0.01元");
        }
        return true;
    }

    private void charge() {
        Map<String, Object> object = new HashMap<>();
        object.put("amount", (int) (Double.parseDouble(mBinding.edtPrice.getText().toString().trim()) * 1000));
        object.put("channelType", payType);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("applyUser", SPUtilHelpr.getUserId());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().wxAliPayRequest("802710", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<WxAliPayRequestModel>(this) {

            @Override
            protected void onSuccess(WxAliPayRequestModel data, String SucMessage) {

                if (payType.equals("36")) {
                    if (WxUtil.check(ChargeActivity.this)) {
                        WxUtil.pay(ChargeActivity.this, data);
                    }
                }else {
                    AliPayUtil.AliPay(ChargeActivity.this, data.getSignOrder());
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

        finish();
    }
}
