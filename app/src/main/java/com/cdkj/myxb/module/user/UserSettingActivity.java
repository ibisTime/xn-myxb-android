package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.activitys.UpdatePhoneActivity;
import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.eventmodels.EventFinishAll;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserSettingBinding;
import com.cdkj.myxb.models.LogoUpdateSucc;
import com.cdkj.myxb.module.common.address.AddressListActivity;
import com.cdkj.myxb.weight.dialog.LogoSelectDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 用户设置
 * Created by cdkj on 2018/2/22.
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

        ImgUtils.loadQiniuLogo(this, SPUtilHelpr.getUserPhoto(), mBinding.imgUserLogo);
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
            FindPwdActivity.open(this, SPUtilHelpr.getUserPhoneNum());
        });

        mBinding.rowUpdatePhone.setOnClickListener(view -> UpdatePhoneActivity.open(this));
        mBinding.linLogoUpdate.setOnClickListener(view -> LogoSelectActivity.open(this));

    }

    //更新头像

    @Subscribe
    public void updateLogo(LogoUpdateSucc logoUpdateSucc) {
        if (logoUpdateSucc == null) return;
        ImgUtils.loadQiniuLogo(this, logoUpdateSucc.getUrl(), mBinding.imgUserLogo);
    }


    private void logOut() {
        SPUtilHelpr.logOutClear();
        LoginActivity.open(this, true);
        EventBus.getDefault().post(new EventFinishAll()); //结束所有界面
        finish();
    }


}
