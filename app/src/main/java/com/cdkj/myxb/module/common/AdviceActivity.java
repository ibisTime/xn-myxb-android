package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAdviceEditBinding;
import com.cdkj.myxb.models.AdviceSucc;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 平台建议评价
 * Created by cdkj on 2018/3/1.
 */

public class AdviceActivity extends AbsBaseLoadActivity {

    private ActivityAdviceEditBinding mBinding;

    private int score; //用户的评分

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AdviceActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_advice_edit, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {
        finish();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("撰写建议");
        mBaseBinding.titleView.setRightTitle("取消");

        mBinding.ratingbar.setOnRatingChangeListener(ra -> {
            score = (int) ra;
        });

        mBinding.btnToRelease.setOnClickListener(view -> {
            if (score <= 0 && TextUtils.isEmpty(mBinding.editContent.getText().toString())) {
                UITipDialog.showInfo(this, "请进行评分或撰写建议");
                return;
            } else if (score <= 0) {
                UITipDialog.showInfo(this, "请进行评分");
                return;
            }
            releaseRequest();
        });
    }

    public void releaseRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("commenter", SPUtilHelpr.getUserId());
        map.put("score", score + "");
        map.put("content", mBinding.editContent.getText().toString());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("805400", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                checkReleaseState(data);
            }

            @Override
            protected void onFinish() {

            }
        });

    }

    /**
     * 监测发布状态
     *
     * @param data
     */
    private void checkReleaseState(CodeModel data) {
        String s = "filter";//是否包含敏感词汇
        if (!TextUtils.isEmpty(data.getCode()) && !StringUtils.contains(data.getCode(), s)) {

            EventBus.getDefault().post(new AdviceSucc());

            UITipDialog.showSuccess(AdviceActivity.this, "发布成功", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });

        } else if (StringUtils.contains(data.getCode(), s)) {

            EventBus.getDefault().post(new AdviceSucc());

            UITipDialog.showSuccess(AdviceActivity.this, "发布成功, 您的评价包含敏感字符,我们将进行审核", new DialogInterface.OnDismissListener() {
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
