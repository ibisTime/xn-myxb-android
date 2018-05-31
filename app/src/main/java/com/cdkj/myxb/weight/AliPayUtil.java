package com.cdkj.myxb.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.pay.PayResult;

/**
 * Created by cdkj on 2018/5/22.
 */

public class AliPayUtil {

    private static Activity mActivity;

    public static void AliPay(Activity activity, String info) {

        mActivity = activity;

        Runnable payRunnable = new Runnable() {

            @Override

            public void run() {

                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果

                String result = alipay.pay(info, true);

                Message msg = new Message();

                msg.what = 1;

                msg.obj = result;

                mHandler.sendMessage(msg);

            }

        };

        Thread payThread = new Thread(payRunnable);

        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:

                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签

                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    System.out.println("resultInfo=" + resultInfo);
                    System.out.println("resultStatus=" + resultStatus);

                    if (resultStatus.equals("9000")) {
                        UITipDialog.showSuccess(mActivity, "支付成功",dialogInterface -> {
                            mActivity.finish();
                        });

                    }

                    break;

            }
        }


    };
}
