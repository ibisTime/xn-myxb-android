package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.models.AppointmentServiceDoneSucc;
import com.cdkj.myxb.models.AppointmentServiceSucc;
import com.cdkj.myxb.module.order.OrderHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 预约操作 上门 下课
 * Created by 李先俊 on 2018/2/26.
 */

public class AppointmentDoActivity extends AbsBaseLoadActivity {

    private String mState;

    private String mCode;

    public static void open(Context context, String code, String state) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentDoActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("state", state);
        context.startActivity(intent);
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        return null;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mState = getIntent().getStringExtra("state");
        }

        if (TextUtils.isEmpty(mCode)) {
            finish();
            return;
        }

        showDoDialog();
    }

    /**
     * 显示操作弹框
     */
    private void showDoDialog() {
        if (TextUtils.equals(OrderHelper.APPOINTMENT_2, mState)) { //上门
            showDoubleWarnListen("确认已上门？", view -> {
                finish();
            }, view -> {
                sureService();
            });

        } else if (TextUtils.equals(OrderHelper.APPOINTMENT_4, mState)) {//下课
            showDoubleWarnListen("确认已下课？", view -> {
                finish();
            }, view -> {
                sureServicDone();
            });
        }
    }

    ;


    /**
     * 确认已上门
     */
    public void sureService() {
        Map map = new HashMap<String, String>();

        map.put("code", mCode);
        map.put("updater", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.getBaseAPiService().successRequest("805512", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                EventBus.getDefault().post(new AppointmentServiceSucc());
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 确认下课
     */
    public void sureServicDone() {
        Map map = new HashMap<String, String>();

        map.put("code", mCode);
        map.put("updater", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.getBaseAPiService().successRequest("805513", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                EventBus.getDefault().post(new AppointmentServiceDoneSucc());
                finish();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

}
