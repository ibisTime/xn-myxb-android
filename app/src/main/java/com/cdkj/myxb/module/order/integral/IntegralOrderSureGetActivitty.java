package com.cdkj.myxb.module.order.integral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.models.IntegralOrderSureGetSucc;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 确认收获界面
 * Created by 李先俊 on 2018/2/23.
 */

public class IntegralOrderSureGetActivitty extends AbsBaseLoadActivity {

    private String mOrderCode;


    public static void open(Context context, String orderCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegralOrderSureGetActivitty.class);
        intent.putExtra("orderCode", orderCode);
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
            mOrderCode = getIntent().getStringExtra("orderCode");
        }

        showSureDialog();
    }

    public void showSureDialog() {

        showDoubleWarnListen("确认收货？", view -> finish(), view -> sureRequest());

    }


    /**
     * 确认收货
     */
    public void sureRequest() {

        if (TextUtils.isEmpty(mOrderCode)) return;
        Map<String, String> map = new HashMap<>();

        map.put("orderCode", mOrderCode);
        map.put("updater", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805296", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack(this) {
            @Override
            protected void onSuccess(Object data, String SucMessage) {
                finish();
                EventBus.getDefault().post(new IntegralOrderSureGetSucc());
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                finish();
            }

            @Override
            protected void onFinish() {

            }
        });

    }

}
