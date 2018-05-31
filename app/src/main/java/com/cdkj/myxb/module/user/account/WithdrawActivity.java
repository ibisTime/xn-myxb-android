package com.cdkj.myxb.module.user.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityWithdrawBinding;
import com.cdkj.myxb.models.WithdrawTipModel;
import com.cdkj.myxb.models.event.EventPaySuccessDoClose;
import com.cdkj.myxb.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/8.
 */

public class WithdrawActivity extends AbsBaseLoadActivity {

    private ActivityWithdrawBinding mBinding;

    private double CUSERQXFL;

    private String balance;
    private String accountNumber;

    /**
     * @param context
     * @param
     */
    public static void open(Context context, String accountNumber, String balance) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, WithdrawActivity.class);
        intent.putExtra("balance",balance);
        intent.putExtra("accountNumber",accountNumber);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_withdraw, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        init();
        initEditText();
        initListener();

        getTip();
    }

    private void init() {
        mBaseBinding.titleView.setMidTitle("取现");

        if (getIntent() == null)
            return;

        balance = getIntent().getStringExtra("balance");
        accountNumber = getIntent().getStringExtra("accountNumber");

        mBinding.txtCanUsePrice.setText("可提现金额¥" + balance + "元");
    }

    private void initListener() {


        mBinding.tvConfirm.setOnClickListener(view -> {
            if (check()){
                withdraw();
            }
        });
    }

    private boolean check() {
        if (mBinding.edtName.getText().toString().equals("")) {
            ToastUtil.show(this, "请填写您的姓名");
            return false;
        }
        if (mBinding.edtBankName.getText().toString().equals("")) {
            ToastUtil.show(this, "请填写银行名称");
            return false;
        }
        if (mBinding.edtCardId.getText().toString().length() < 16 || mBinding.edtCardId.getText().toString().length() > 19) {
            ToastUtil.show(this, "请填写正确的银行卡号");
            return false;
        }

        if (mBinding.edtPrice.getText().toString().toString().equals("")) {
            ToastUtil.show(this, "请输入提现金额");
            return false;
        }

        if (Double.parseDouble(mBinding.edtPrice.getText().toString().trim()) == 0.0) {
            ToastUtil.show(this, "金额必须大于等于0.01元");
        }
        return true;
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

        mBinding.edtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().equals("")){
                    mBinding.txtTip4.setText("* 本次提现手续费:"+ MoneyUtils.doubleFormatSXF(Double.parseDouble(editable.toString()) * CUSERQXFL));
                }else {
                    mBinding.txtTip4.setText("* 本次提现手续费:0");
                }
            }
        });

    }

    private void withdraw() {
        Map<String, Object> object = RetrofitUtils.getRequestMap();

        object.put("accountNumber", accountNumber);
        object.put("amount", (int) (Double.parseDouble(mBinding.edtPrice.getText().toString().trim()) * 1000));
        object.put("payCardNo", mBinding.edtCardId.getText().toString().trim());
        object.put("realName", mBinding.edtName.getText().toString().trim());
        // 开户行
        object.put("payCardInfo", mBinding.edtBankName.getText().toString());
        object.put("applyNote", "Android用户端取现");
        object.put("applyUser", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("802751", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<String>(this) {

            @Override
            protected void onSuccess(String data, String SucMessage) {
                UITipDialog.showSuccess(WithdrawActivity.this, "提现申请成功", dialogInterface -> {

                    // 关闭下单界面
                    EventBus.getDefault().post(new EventPaySuccessDoClose());

                    finish();
                });

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
    public void getTip() {
        Map<String, String> object = new HashMap<>();
        object.put("type", "3");
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);
        object.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getWithdrawTip("802029", StringUtils.getJsonToString(object));

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<WithdrawTipModel>(this) {
            @Override
            protected void onSuccess(WithdrawTipModel data, String SucMessage) {

                mBinding.txtTip.setText("1.每月最大取现次数为"+data.getCUSERMONTIMES()+"次");
                mBinding.txtTip2.setText("2.提现金额是" + data.getCUSERQXBS() + "的倍数，单笔最高" + data.getQXDBZDJE());
                mBinding.txtTip3.setText("3.取现手续费:" + (data.getCUSERQXFL()*100)+"%");

                CUSERQXFL = data.getCUSERQXFL();

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
