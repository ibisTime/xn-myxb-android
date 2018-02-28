package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.MsgListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 消息中心
 * Created by cdkj on 2017/8/9.
 */

public class MsgListActivity extends AbsRefreshListActivity {



    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MsgListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new BaseQuickAdapter<MsgListModel.ListBean, BaseViewHolder>(R.layout.item_msg, listData) {
            @Override
            protected void convert(BaseViewHolder helper, MsgListModel.ListBean item) {
                if (item == null) return;

                helper.setText(R.id.tv_content, item.getSmsContent());
                helper.setText(R.id.tv_title, item.getSmsTitle());
                helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getCreateDatetime(), DateUtil.DATE_YMD));

            }
        };
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("channelType", "4");
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("pushType", "41");
        map.put("toKind", "C");
        map.put("status", "1");
        map.put("fromSystemCode", MyCdConfig.SYSTEMCODE);
        map.put("toSystemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getMsgList("804040", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<MsgListModel>(this) {
            @Override
            protected void onSuccess(MsgListModel data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无消息", 0);
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
        mBaseBinding.titleView.setMidTitle(getString(R.string.center_msg));

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);
    }


}
