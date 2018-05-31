package com.cdkj.myxb.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.cdkj.myxb.R;
import com.cdkj.myxb.models.event.EventWxPaySuc;
import com.cdkj.myxb.weight.WxUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * 微信支付结果界面
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private static WXCallBack wxCallBack;

    private SharedPreferences preferences;

    @Subscribe
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wx_entry);

        EventBus.getDefault().register(this);

        api = WXAPIFactory.createWXAPI(this, WxUtil.APPID);
        api.handleIntent(getIntent(), this);

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 结果回调方法
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode == 0){
                Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else if(resp.errCode == -1) {
                Toast.makeText(WXPayEntryActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            } else if(resp.errCode == -2) {
                Toast.makeText(WXPayEntryActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            } else {
//                checkPayResult(resp.errCode);
            }

            // 关闭审核界面
            EventBus.getDefault().post(new EventWxPaySuc());
            finish();
        }
    }


    public static void setWXCallBack(WXCallBack callBack){
        wxCallBack=callBack;
    }

    public interface WXCallBack {
        void msgCallBask(int msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}