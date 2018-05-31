package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.RouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.ActivityModifyPasswordBinding;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCoodePresenter;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.module.LoginTypeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;

/**
 * 找回密码
 */
@Route(path = RouteHelper.CDFINDPWD)
public class FindPwdActivity extends AbsBaseLoadActivity implements SendCodeInterface {

    private ActivityModifyPasswordBinding mBinding;

    private String mPhoneNumber;

    private SendPhoneCoodePresenter mSendCOdePresenter;

    public static final String PHONENUMBERSING = "phonenumber";

    private OptionsPickerView mLoginTypePicker;//登录类型选择

    private List<LoginTypeModel> mLoginTypes;

    private String mLoginKind;//用户的登录类型

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String mPhoneNumber) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, FindPwdActivity.class);
        intent.putExtra(PHONENUMBERSING, mPhoneNumber);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_modify_password, null, false);
        return mBinding.getRoot();
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("修改密码");

        mSendCOdePresenter = new SendPhoneCoodePresenter(this);
        if (getIntent() != null) {
            mPhoneNumber = getIntent().getStringExtra(PHONENUMBERSING);
        }

        if (!TextUtils.isEmpty(mPhoneNumber)) {
            mBinding.edtPhone.setText(mPhoneNumber);
            mBinding.edtPhone.setSelection(mBinding.edtPhone.getText().toString().length());
        }

        initListener();
    }


    /**
     *
     */
    private void initListener() {

        //登录类型
        mBinding.linLoginType.setOnClickListener(view -> {
            if (mLoginTypePicker == null) {
                initPickerView();
            }
            mLoginTypePicker.setPicker(mLoginTypes);
            mLoginTypePicker.show();
        });


        //发送验证码
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendCOdePresenter.sendCodeRequest(mBinding.edtPhone.getText().toString(), "805063", MyCdConfig.USERTYPE, FindPwdActivity.this);
            }
        });


        //确定
        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.edtPhone.getText().toString())) {
                    UITipDialog.showFall(FindPwdActivity.this, "请输入手机号");
                    return;
                }

                if (TextUtils.isEmpty(mBinding.edtCode.getText().toString())) {
                    UITipDialog.showFall(FindPwdActivity.this, "请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(mBinding.edtPassword.getText().toString())) {
                    UITipDialog.showFall(FindPwdActivity.this, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.edtRepassword.getText().toString())) {
                    UITipDialog.showFall(FindPwdActivity.this, "请重新输入密码");
                    return;
                }

                if (mBinding.edtPassword.getText().length() < 6) {
                    UITipDialog.showFall(FindPwdActivity.this, "密码不少于6位");
                    return;
                }

                if (!mBinding.edtPassword.getText().toString().equals(mBinding.edtRepassword.getText().toString())) {
                    UITipDialog.showFall(FindPwdActivity.this, "两次密码输入不一致");
                    return;
                }

                if (TextUtils.isEmpty(mLoginKind)) {
                    UITipDialog.showFall(FindPwdActivity.this, "请选择角色");
                    return;
                }

                findPwdReqeust();
            }
        });
    }

    private void initPickerData() {

        mLoginTypes = new ArrayList<>();

        LoginTypeModel loginTypeModel = new LoginTypeModel();
        loginTypeModel.setType(UserHelper.C);
        loginTypeModel.setTypeString(getString(R.string.mry));

        mLoginTypes.add(loginTypeModel);

        LoginTypeModel loginTypeModel2 = new LoginTypeModel();
        loginTypeModel2.setType(UserHelper.B);
        loginTypeModel2.setTypeString(getString(R.string.partner));

        mLoginTypes.add(loginTypeModel2);

        LoginTypeModel loginTypeModel3 = new LoginTypeModel();
        loginTypeModel3.setType(UserHelper.S);
        loginTypeModel3.setTypeString(getString(R.string.experts));

        mLoginTypes.add(loginTypeModel3);

        LoginTypeModel loginTypeMode4 = new LoginTypeModel();
        loginTypeMode4.setType(UserHelper.T);
        loginTypeMode4.setTypeString(getString(R.string.shopper));

        mLoginTypes.add(loginTypeMode4);
    }


    public void initPickerView() {

        initPickerData();

        mLoginTypePicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {

            LoginTypeModel loginTypeModel = mLoginTypes.get(options1);

            mLoginKind = loginTypeModel.getType();

            mBinding.tvLogintype.setText(loginTypeModel.getTypeString());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();


    }

    /**
     * 找回密码请求
     */
    private void findPwdReqeust() {

        HashMap<String, String> hashMap = new LinkedHashMap<String, String>();

        hashMap.put("mobile", mBinding.edtPhone.getText().toString());
        hashMap.put("newLoginPwd", mBinding.edtPassword.getText().toString());
        hashMap.put("smsCaptcha", mBinding.edtCode.getText().toString());
        hashMap.put("kind", mLoginKind);
        hashMap.put("systemCode", MyCdConfig.SYSTEMCODE);
        hashMap.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805063", StringUtils.getJsonToString(hashMap));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(FindPwdActivity.this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    UITipDialog.showSuccess(FindPwdActivity.this, "密码修改成功", new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                } else {
                    UITipDialog.showFall(FindPwdActivity.this, "密码修改失败");
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(FindPwdActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


    @Override
    public void CodeSuccess(String msg) {
        mSubscription.add(AppUtils.startCodeDown(60, mBinding.btnSend));
    }

    @Override
    public void CodeFailed(String code, String msg) {
        UITipDialog.showFall(FindPwdActivity.this, msg);
    }

    @Override
    public void StartSend() {
        showLoadingDialog();
    }

    @Override
    public void EndSend() {
        disMissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSendCOdePresenter != null) {
            mSendCOdePresenter.clear();
            mSendCOdePresenter = null;
        }
    }

}
