package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;

/**
 * 消息列表
 * Created by cdkj on 2018/2/7.
 */

public class MsgListActivity extends AbsBaseLoadActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MsgListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        return null;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.center_msg));
    }
}
