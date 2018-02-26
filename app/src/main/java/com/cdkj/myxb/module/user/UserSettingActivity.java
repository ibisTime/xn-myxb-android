package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.activitys.UpdatePhoneActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.eventmodels.EventFinishAll;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserSettingBinding;
import com.cdkj.myxb.module.common.address.AddressListActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 用户设置
 * Created by 李先俊 on 2018/2/22.
 */

public class UserSettingActivity extends AbsBaseLoadActivity {

    private ActivityUserSettingBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserSettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_setting, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("账户设置");
        mBinding.rowAddress.setOnClickListener(view -> AddressListActivity.open(this, false));

        initListener();

    }

    private void initListener() {

        //退出登录
        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoubleWarnListen("确认退出登录？", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {
                        logOut();
                    }
                });
            }
        });

        mBinding.rowUpdatePassword.setOnClickListener(view -> {
            FindPwdActivity.open(this,SPUtilHelpr.getUserPhoneNum());
        });

        mBinding.rowUpdatePhone.setOnClickListener(view -> UpdatePhoneActivity.open(this));

    }

    private void logOut() {
        SPUtilHelpr.logOutClear();
        LoginActivity.open(this, true);
        EventBus.getDefault().post(new EventFinishAll()); //结束所有界面
        finish();
    }
}
