package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
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

public class MsgListActivity extends AbsBaseLoadActivity {


    private RefreshHelper mRefreshHelper;

    private LayoutRecyclerRefreshBinding mBinding;


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


    public BaseQuickAdapter getAdapter(List<MsgListModel.ListBean> listData) {
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

    public void getMsgListRequest(int pageindex, int limit, boolean canShowDialog) {


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

        if (canShowDialog) showLoadingDialog();

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
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.center_msg));

        initRefreshHelper();

        mRefreshHelper.onDefaluteMRefresh(true);
    }

    private void initRefreshHelper() {

        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return MsgListActivity.this.getAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getMsgListRequest(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
