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
import com.cdkj.baselibrary.views.MyDividerItemDecoration;
import com.cdkj.myxb.adapters.HappyMsgListAdapter;
import com.cdkj.myxb.models.HappyMsgModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 喜报预报列表
 * Created by cdkj on 2018/3/1.
 */

public class HappyMsgListActivity extends AbsRefreshListActivity {

    // （0喜报，1预报）
    public static final String HAPPYMSG = "0";
    public static final String TODOMSG = "1";
    public static final String MSGTYPE = "msg";

    public String mType = "";

    /**
     * @param context
     * @param msgType // （0喜报，1预报）
     */
    public static void open(Context context, String msgType) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, HappyMsgListActivity.class);
        intent.putExtra(MSGTYPE, msgType);
        context.startActivity(intent);
    }


    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        HappyMsgListAdapter happyMsgListAdapter = new HappyMsgListAdapter(listData);

        happyMsgListAdapter.setOnItemClickListener((adapter, view, position) -> {

            if (happyMsgListAdapter.getItem(position) == null) return;

            HappyMsgDetailsActivity.open(this, happyMsgListAdapter.getItem(position).getCode(), mType);
        });

        return happyMsgListAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mType)) return;

        Map<String, String> map = new HashMap<>();
        map.put("type", mType);          // （0喜报，1预报）
        map.put("start", pageindex + "");
        map.put("status", "1"); //已上架
        map.put("limit", limit + "");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getHappyMsgList("805435", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<HappyMsgModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<HappyMsgModel> data, String SucMessage) {
                if (TextUtils.equals(mType, HAPPYMSG)) {
                    mRefreshHelper.setData(data.getList(), "暂无喜报", 0);
                } else {
                    mRefreshHelper.setData(data.getList(), "暂无预报", 0);
                }


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

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mType = getIntent().getStringExtra(MSGTYPE);
        }

        if (TextUtils.equals(mType, HAPPYMSG)) {
            mBaseBinding.titleView.setMidTitle("喜报");
        } else {
            mBaseBinding.titleView.setMidTitle("预报");
        }


        initRefreshHelper(10);
        mRefreshBinding.recyclerView.addItemDecoration(new MyDividerItemDecoration(this, MyDividerItemDecoration.VERTICAL_LIST));
        mRefreshHelper.onDefaluteMRefresh(true);
    }
}
