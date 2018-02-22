package com.cdkj.myxb;

import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.module.user.LoginActivity;
import com.cdkj.myxb.weight.dialog.IntegralChangeDialog;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 * Created by 李先俊 on 2017/6/8.
 */

public class WelcomeAcitivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity

        try {
            if (getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception e) {
        }
//        setContentView(R.layout.activity_welcom);
//        ImageView img = (ImageView) findViewById(R.id.img_start);
//        img.setImageResource(R.drawable.welcome);
        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

         /*           HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("systemCode", MyCdConfig.SYSTEMCODE);
                    hashMap.put("companyCode", MyCdConfig.COMPANYCODE);
                    hashMap.put("mobile", "13765051712");
                    hashMap.put("bizType", "805041");

                    HashMap<String, String> hashMap2= new HashMap<>();
                    hashMap2.put("systemCode", MyCdConfig.SYSTEMCODE);
                    hashMap2.put("companyCode", MyCdConfig.COMPANYCODE);
                    hashMap2.put("kind", "C");
                    hashMap2.put("loginPwd", "123456");
                    hashMap2.put("mobile", "13765051712");
                    hashMap2.put("smsCaptcha", "xxxx");


                    LogUtil.E("发送验证码"+ StringUtils.getJsonToString(hashMap));
                    LogUtil.E("注册"+ StringUtils.getJsonToString(hashMap2));*/


                    new IntegralChangeDialog(this).show();

//                    MainActivity.open(this);
//                    LoginActivity.open(this, false);
                }, Throwable::printStackTrace));
    }


}
