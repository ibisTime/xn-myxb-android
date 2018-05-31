package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityBeSomeOneBinding;
import com.cdkj.myxb.models.event.EventSignPaySuccessDoClose;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/8.
 */

public class BeSomeOneActivity extends AbsBaseLoadActivity {

    private ActivityBeSomeOneBinding mBinding;

    private static String KEY = "key";

    private String key;
    private String toKey = "";

    /**
     * @param context
     * @param key
     */
    public static void open(Context context, String key) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BeSomeOneActivity.class);
        intent.putExtra(KEY, key);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_be_some_one, null,false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        initListener();

    }

    private void init() {



        if (getIntent() == null)
            return;

        key = getIntent().getStringExtra(KEY);

        if (TextUtils.equals("FWSZC", key)){
            toKey = "FWSHT";
            mBaseBinding.titleView.setMidTitle("成为服务团队");
        }else if (TextUtils.equals("FWJYZC", key)){
            toKey = "FWJYHT";
            mBaseBinding.titleView.setMidTitle("成为服务团队");
        }

        getKeyUrl();
    }

    private void initListener() {
        mBinding.btnConfirm.setOnClickListener(view -> {

            SignActivity.open(this, toKey);

        });
    }

    public void getKeyUrl() {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("ckey", key);
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
                mBinding.webView.loadData(data.getCvalue(), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(BeSomeOneActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Subscribe
    public void doColose(EventSignPaySuccessDoClose addressModel) {
        finish();
    }
}
