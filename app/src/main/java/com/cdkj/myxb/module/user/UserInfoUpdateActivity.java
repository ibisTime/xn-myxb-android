package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserInfoUpdateBinding;

/**
 * 用户资料修改
 * Created by cdkj on 2018/2/27.
 */

public class UserInfoUpdateActivity extends AbsBaseLoadActivity {

    private ActivityUserInfoUpdateBinding mBinding;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserInfoUpdateActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_info_update, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("我的资料");
        mBaseBinding.titleView.setRightTitle("保存");

    }
}
