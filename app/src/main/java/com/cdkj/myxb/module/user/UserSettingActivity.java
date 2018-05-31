package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.activitys.UpdatePhoneActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.eventmodels.EventFinishAll;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserSettingBinding;
import com.cdkj.myxb.models.LogoUpdateSucc;
import com.cdkj.myxb.module.common.address.AddressListActivity;

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

    private int PHOTOFLAG = 111;

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

        ImgUtils.loadQiNiuBorderLogo(this, SPUtilHelpr.getUserPhoto(), mBinding.imgUserLogo,R.color.bg_gray);
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
        mBinding.linLogoUpdate.setOnClickListener(view -> ImageSelectActivity.launch(this, PHOTOFLAG, false));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(CameraHelper.staticPath);
            LogUtil.E("拍照获取路径" + path);
            showLoadingDialog();
            new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
                @Override
                public void onSuccess(String key) {
                    updateUserPhoto(key);
                }

                @Override
                public void onFal(String info) {
                    disMissLoading();
                }
            }, path);

        }
    }

    /**
     * 更新头像
     *
     * @param key
     */
    private void updateUserPhoto(final String key) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("photo", key);
        map.put("token", SPUtilHelpr.getUserToken());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805080", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(UserSettingActivity.this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(UserSettingActivity.this, "头像更换成功");
                ImgUtils.loadQiniuLogo(UserSettingActivity.this, key, mBinding.imgUserLogo);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(UserSettingActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
