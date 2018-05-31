package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.models.LogoListModel;
import com.cdkj.myxb.models.LogoUpdateSucc;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.weight.dialog.LogoSelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/2/28.
 */

public class LogoSelectActivity extends AbsBaseLoadActivity {


    private LogoSelectDialog logoSelectDialog;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LogoSelectActivity.class);
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
        logoSelectDialog = new LogoSelectDialog(LogoSelectActivity.this);
        logoSelectDialog.setListener(new LogoSelectDialog.LogoSelectDialogListener() {
            @Override
            public void onRefresh(int pageindex, int limit) {
                getLogoList(pageindex, limit, false, false);
            }

            @Override
            public void onLoadMore(int pageindex, int limit) {
                getLogoList(pageindex, limit, false, false);
            }

            @Override
            public void onSureClick(String url) {
                if (TextUtils.isEmpty(url)) {
                    finish();
                    return;
                }
                updateLogo(url);
            }
        });
        getLogoList(1, 10, true, true);

    }


    /**
     * 更新头像
     *
     * @param url
     */
    public void updateLogo(String url) {
        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("photo", url);
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805080", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {

                if (data.isSuccess()) {
                    LogoUpdateSucc logoUpdateSucc = new LogoUpdateSucc();
                    logoUpdateSucc.setUrl(url);
                    EventBus.getDefault().post(logoUpdateSucc);
                    SPUtilHelpr.saveUserPhoto(url);

                    UITipDialog.showSuccess(LogoSelectActivity.this, "头像更换成功", dialogInterface -> {
                        finish();
                    });

                } else {
                    UITipDialog.showSuccess(LogoSelectActivity.this, "头像更换失败", dialogInterface -> {
                        finish();
                    });
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    /**
     * 获取头像列表
     *
     * @param start
     * @param limit
     * @param isShowLoadingDialog
     * @param isShowSelectDialog
     */
    public void getLogoList(int start, int limit, boolean isShowLoadingDialog, boolean isShowSelectDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("kind", SPUtilHelpr.getUserType());
        map.put("level", SPUtilHelpr.getUserLevel());
        map.put("limit", limit + "");
        map.put("start", start + "");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getLogoList("805443", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowLoadingDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<LogoListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<LogoListModel> data, String SucMessage) {
                if (data.getList() == null || data.getList().isEmpty()) {
                    if (start == 1) {
                        logoSelectDialog.getmRefreshHelper().setLayoutManager(new LinearLayoutManager(LogoSelectActivity.this, LinearLayoutManager.VERTICAL, false));
                    }
                } else if (start == 1) {
                    logoSelectDialog.getmRefreshHelper().setLayoutManager(new ScrollGridLayoutManager(LogoSelectActivity.this, 3));
                }
                logoSelectDialog.getmRefreshHelper().setData(data.getList(), "暂无可选头像", 0);
                if (isShowSelectDialog) {
                    logoSelectDialog.show();
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
