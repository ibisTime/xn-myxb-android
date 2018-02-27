package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;

/**
 * 用户资料修改
 * Created by cdkj on 2018/2/27.
 */

public class UserInfoUpdateActivity extends AbsBaseLoadActivity {



    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserInfoUpdateActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        return null;
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
