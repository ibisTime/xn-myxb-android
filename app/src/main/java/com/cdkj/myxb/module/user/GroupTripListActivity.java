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
import com.cdkj.myxb.adapters.GroupTripListAdapter;
import com.cdkj.myxb.models.GroupTripListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 团队行程列表
 * Created by cdkj on 2018/2/28.
 */

public class GroupTripListActivity extends AbsRefreshListActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GroupTripListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("团队行程列表");

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new GroupTripListAdapter(listData);
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getGroupTripList("805523", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<GroupTripListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<GroupTripListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无团队行程", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

}
