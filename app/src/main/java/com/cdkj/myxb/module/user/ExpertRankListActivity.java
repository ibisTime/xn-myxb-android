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
import com.cdkj.myxb.adapters.ExpertsRankListAdapter;
import com.cdkj.myxb.models.ExpertRankListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 专家排名列表
 * Created by cdkj on 2018/2/28.
 */

public class ExpertRankListActivity extends AbsRefreshListActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ExpertRankListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("我的排名");
        initRefreshHelper(10);
        mRefreshHelper.onDefaluteMRefresh(true);
    }


    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new ExpertsRankListAdapter(listData);
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("refNo", SPUtilHelpr.getUserId());
        map.put("start", pageindex + "");
        map.put("limit", limit + "");

        if (isShowDialog) showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getExpertRankList("805123", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<ExpertRankListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<ExpertRankListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无排名", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

}
