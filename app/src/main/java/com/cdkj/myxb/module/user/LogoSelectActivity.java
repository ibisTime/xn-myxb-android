package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityLogoSelectBinding;
import com.cdkj.myxb.weight.dialog.LogoSelectDialog;

/**
 * Created by 李先俊 on 2018/2/28.
 */

public class LogoSelectActivity extends AbsBaseLoadActivity {

    private ActivityLogoSelectBinding mBinding;

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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_logo_select, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        new LogoSelectDialog(this, null).show();

    }
}
