package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.adapters.MyCommentAdapter;
import com.cdkj.myxb.models.MyCommentListMode;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/13.
 */

public class MyCommentActivity extends AbsRefreshListActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyCommentActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new MyCommentAdapter(listData);
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
//        map.put("status", "AB"); //审核通过
//        map.put("type", mType);
        map.put("userId", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.createApi(MyApiServer.class).getMyCommentList("805429", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<MyCommentListMode>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<MyCommentListMode> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无评价", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("我收到的评论");

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);
    }
}
