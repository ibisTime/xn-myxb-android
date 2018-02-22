package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;

/**用户设置
 * Created by 李先俊 on 2018/2/22.
 */

public class UserSettingActivity extends AbsBaseLoadActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserSettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        return null;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
