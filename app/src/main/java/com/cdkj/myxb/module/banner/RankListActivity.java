package com.cdkj.myxb.module.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.models.HappyMsgModel;
import com.cdkj.myxb.models.RankModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌排名 美容院排名 专家
 * Created by cdkj on 2018/3/1.
 */

public class RankListActivity extends AbsRefreshListActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RankListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {

        initRefreshHelper(6);

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return null;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("orderColumn", "rank");
        map.put("orderDir", "asc");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getRankList("805278", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<RankModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<RankModel> data, String SucMessage) {
//                mRefreshHelper.setData(data.getList(), "暂无喜报", 0);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                mRefreshHelper.loadError(errorMessage, 0);
            }


            @Override
            protected void onNoNet(String msg) {
                mRefreshHelper.loadError(msg, 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

}
