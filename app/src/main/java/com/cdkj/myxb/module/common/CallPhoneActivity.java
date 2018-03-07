package com.cdkj.myxb.module.common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.PermissionHelper;
import com.cdkj.baselibrary.utils.StringUtils;

/**
 * 拨打电话
 * Created by cdkj on 2018/2/24.
 */

public class CallPhoneActivity extends AbsBaseLoadActivity {


    private PermissionHelper mPermissionHelper;
    private String mobile;


    /**
     * @param context
     * @param mobile  要拨打的电话
     */
    public static void open(Context context, String mobile) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CallPhoneActivity.class);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }


    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        return null;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mPermissionHelper = new PermissionHelper(this);

        if (getIntent() != null) {
            mobile = getIntent().getStringExtra("mobile");
        }

        if (TextUtils.isEmpty(mobile)) {
            finish();
            return;
        }

        showCallPhoneDialog(mobile, view -> {
            finish();
        }, view -> {
            if (!StringUtils.isTel(mobile)) {
                showSureDialog("错误的电话号码，无法拨打", view1 -> finish());
                return;
            }
            permissionRequest();
        });
    }

    /**
     * 拨打电话权限请求
     */
    private void permissionRequest() {
        mPermissionHelper.requestPermissions(new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {

                AppUtils.callPhonePage(CallPhoneActivity.this, mobile);
                finish();
            }

            @Override
            public void doAfterDenied(String... permission) {
                showSureDialog("没有权限，无法拨打电话，请到设置--->权限管理里授予用户拨打电话权限", view -> finish());
            }
        }, Manifest.permission.CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void showCallPhoneDialog(String str, CommonDialog.OnNegativeListener onNegativeListener, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }

        CommonDialog commonDialog = new CommonDialog(this).builder()
                .setTitle("拨打电话").setContentMsg(str)
                .setPositiveBtn("确定", onPositiveListener)
                .setNegativeBtn("取消", onNegativeListener, false);

        commonDialog.show();
    }

}
