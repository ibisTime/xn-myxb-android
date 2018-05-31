package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.adapters.UserAtlasAdapter;
import com.cdkj.myxb.models.AtlasOuterModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasActivity extends AbsRefreshListActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserAtlasActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        UserAtlasAdapter userAtlasAdapter = new UserAtlasAdapter(listData);

        return userAtlasAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAtlasModel("805146", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<AtlasOuterModel>(this) {


            @Override
            protected void onSuccess(AtlasOuterModel data, String SucMessage) {

                mRefreshHelper.setData(data.getLineList(), "暂无图谱", 0);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("网络图谱");

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);

        mRefreshBinding.refreshLayout.setEnableLoadmore(false);

    }

}
