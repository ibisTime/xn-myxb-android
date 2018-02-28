package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommentItemAdapter;
import com.cdkj.myxb.databinding.ActivityCommentsEditBinding;
import com.cdkj.myxb.models.AppointmentCommentsSucc;
import com.cdkj.myxb.models.CommentItemModel;
import com.cdkj.myxb.models.CommentRequest;
import com.cdkj.myxb.models.IntegralOrderCommentsSucc;
import com.cdkj.myxb.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 预约评价
 * Created by 李先俊 on 2018/2/23.
 */

public class AppointmentCommentActivity extends AbsBaseLoadActivity {

    private ActivityCommentsEditBinding mBinding;


    private String mOrderCode;
    private CommentItemAdapter commentItemAdapter;

    private String mType;   //用户类型
    private final static String TYPE = "state";
    private final static String ORDERCODE = "ordercode";

    /**
     * @param context
     * @param orderCode 订单编号
     */
    public static void open(Context context, String orderCode, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentCommentActivity.class);
        intent.putExtra(ORDERCODE, orderCode);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_comments_edit, null, false);
        return mBinding.getRoot();
    }


    @Override
    public void topTitleViewRightClick() {

        if (!checkInputInfoIsEmpty()) {
            return;
        }

        releaseRequest();
    }

    private boolean checkInputInfoIsEmpty() {

        for (int i = 0; i < commentItemAdapter.getData().size(); i++) {

            CommentItemModel commentItemModel = commentItemAdapter.getItem(i);

            if (commentItemModel == null) continue;

            if (TextUtils.equals("0", commentItemModel.getcScore())) {
                UITipDialog.showInfo(this, "请对" + commentItemModel.getCvalue() + "进行评分");
                return false;
            }
        }

        if (TextUtils.isEmpty(mBinding.editInfo.getText().toString())) {
            UITipDialog.showInfo(this, "请输入内容");
            return false;
        }
        return true;

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("提交评价");
        mBaseBinding.titleView.setRightTitle("发布");

        if (getIntent() != null) {
            mOrderCode = getIntent().getStringExtra(ORDERCODE);

            mType = getIntent().getStringExtra(TYPE);
        }
        initRecyclerView();
        getCommentItem();
    }

    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mBinding.recyclerViewComments.setLayoutManager(linearLayoutManager);

        mBinding.recyclerViewComments.setVisibility(View.VISIBLE);

    }

    /**
     * 发布评论
     */
    public void releaseRequest() {

        if (TextUtils.isEmpty(mOrderCode) || TextUtils.isEmpty(mType)) {
            return;
        }

        Map<String, Object> map = new HashMap<>();

        map.put("commenter", SPUtilHelpr.getUserId());
        map.put("content", mBinding.editInfo.getText().toString());
        map.put("orderCode", mOrderCode);
        map.put("type", mType);/*Lecturer("L", "讲师"), Specialist("S", "专家"), Tutor("T", "美导"), PRODUCT("P", "产品"), IP：积分商品*/

        map.put("req", getCommentRequests());

        Call call = RetrofitUtils.createApi(BaseApiServer.class).codeRequest("805420", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {

                checkReleaseState(data);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 获取评价项进行请求
     *
     * @return
     */
    @NonNull
    private List<CommentRequest> getCommentRequests() {
        List<CommentRequest> req = new ArrayList<>();


        for (int i = 0; i < commentItemAdapter.getData().size(); i++) {

            CommentItemModel commentItemModel = commentItemAdapter.getItem(i);

            if (commentItemModel == null) continue;

            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setScore(commentItemModel.getcScore());
            commentRequest.setCkey(commentItemModel.getCkey());

            req.add(commentRequest);
        }
        return req;
    }


    /**
     * 获取要评分的项
     */
    public void getCommentItem() {


        Map map = RetrofitUtils.getRequestMap();

        map.put("type", "ISR");//type=ISW物品 ISR人

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentItem("805914", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<CommentItemModel>(this) {

            @Override
            protected void onSuccess(List<CommentItemModel> data, String SucMessage) {
                commentItemAdapter = new CommentItemAdapter(data);
                mBinding.recyclerViewComments.setAdapter(commentItemAdapter);
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
    private void checkReleaseState(CodeModel data) {
        String s = "filter";//是否包含敏感词汇
        if (!TextUtils.isEmpty(data.getCode()) && !StringUtils.contains(data.getCode(), s)) {

            EventBus.getDefault().post(new AppointmentCommentsSucc());

            UITipDialog.showSuccess(AppointmentCommentActivity.this, "发布评价成功", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });
        } else if (StringUtils.contains(data.getCode(), s)) {
            EventBus.getDefault().post(new IntegralOrderCommentsSucc());
            UITipDialog.showSuccess(AppointmentCommentActivity.this, "评价成功, 您的评价包含敏感字符,我们将进行审核", new DialogInterface.OnDismissListener() {
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
