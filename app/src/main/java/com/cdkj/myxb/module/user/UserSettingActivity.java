package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserSettingBinding;
import com.cdkj.myxb.module.common.address.AddressListActivity;

/**
 * 用户设置
 * Created by 李先俊 on 2018/2/22.
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

        mBinding.rowAddress.setOnClickListener(view -> AddressListActivity.open(this, false));

    }
}
