package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.MyDividerItemDecoration;
import com.cdkj.myxb.adapters.AdviceListAdapter;
import com.cdkj.myxb.models.AdviceListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 建议列表
 * Created by cdkj on 2018/3/1.
 */

public class AdviceListActivity extends AbsRefreshListActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AdviceListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new AdviceListAdapter(listData);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("平台建议");

        initRefreshHelper(10);

        mRefreshBinding.recyclerView.addItemDecoration(new MyDividerItemDecoration(this, MyDividerItemDecoration.VERTICAL_LIST));

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", "AB");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAdviceList("805405", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<AdviceListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<AdviceListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无建议", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
