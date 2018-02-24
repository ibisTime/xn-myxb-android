package com.cdkj.myxb.module.integral;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityCommentsEditBinding;
import com.cdkj.myxb.models.IntegralOrderCommentsSucc;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 积分订单评价
 * Created by 李先俊 on 2018/2/23.
 */

public class IntegralOrderCommentActivity extends AbsBaseLoadActivity {

    private ActivityCommentsEditBinding mBinding;


    private String mOrderCode;

    /**
     * @param context
     * @param orderCode 订单编号
     */
    public static void open(Context context, String orderCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegralOrderCommentActivity.class);
        intent.putExtra("orderCode", orderCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_comments_edit, null, false);
        return mBinding.getRoot();
    }


    @Override
    public void topTitleViewRightClick() {
        releaseRequest();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("提交评论");
        mBaseBinding.titleView.setRightTitle("发布");

        if (getIntent() != null) {
            mOrderCode = getIntent().getStringExtra("orderCode");
        }

    }

    /**
     * 发布评论
     */
    public void releaseRequest() {

        if (TextUtils.isEmpty(mOrderCode)) {
            return;
        }

        if (TextUtils.isEmpty(mBinding.editInfo.getText().toString())) {
            UITipDialog.showInfo(this, "请输入内容");
            return;
        }


        Map<String, String> map = new HashMap<>();

        map.put("commenter", SPUtilHelpr.getUserId());
        map.put("content", mBinding.editInfo.getText().toString());
        map.put("orderCode", mOrderCode);
        map.put("type", "IP");/*Lecturer("L", "讲师"), Specialist("S", "专家"), Tutor("T", "美导"), PRODUCT("P", "产品"), IP：积分商品*/


        Call call = RetrofitUtils.createApi(BaseApiServer.class).codeRequest("805420", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {

                releaseState(data);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 监测发布状态
     *
     * @param data
     */
    private void releaseState(CodeModel data) {
        String s = "filter";//是否包含敏感词汇
        if (!TextUtils.isEmpty(data.getCode()) && !StringUtils.contains(data.getCode(), s)) {

            UITipDialog.showSuccess(IntegralOrderCommentActivity.this, "发布评论成功", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });
        } else if (StringUtils.contains(data.getCode(), s)) {
            EventBus.getDefault().post(new IntegralOrderCommentsSucc());
            UITipDialog.showSuccess(IntegralOrderCommentActivity.this, "评论成功, 您的评论包含敏感字符,我们将进行审核", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });
        } else {
            showToast("发布失败");
            finish();
        }
    }

}
