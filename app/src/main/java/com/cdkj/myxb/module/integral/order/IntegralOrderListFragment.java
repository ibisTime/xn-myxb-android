package com.cdkj.myxb.module.integral.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.IntegralOrderListAdapter;
import com.cdkj.myxb.models.IntegralOrderCommentsSucc;
import com.cdkj.myxb.models.IntegralOrderListModel;
import com.cdkj.myxb.models.IntegralOrderSureGetSucc;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListFragment;
import com.cdkj.myxb.module.integral.IntegralOrderCommentActivity;
import com.cdkj.myxb.module.order.OrderHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 积分订单列表
 * Created by cdkj on 2018/2/23.
 */

public class IntegralOrderListFragment extends AbsRefreshListFragment {


    private static final String ORDERSTATE = "state";
    private static final String ISFIRSTREQUEST = "isFirstRequest";

    private String mOrderState; //要查看的订单状态

    /**
     * @param state          订单状态
     * @param isFirstRequest 创建时是否进行请求
     * @return
     */
    public static IntegralOrderListFragment getInstance(String state, boolean isFirstRequest) {
        IntegralOrderListFragment fragment = new IntegralOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERSTATE, state);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mOrderState = getArguments().getString(ORDERSTATE);

        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        IntegralOrderListAdapter integralOrderListAdapter = new IntegralOrderListAdapter(listData);

        integralOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {

            IntegralOrderListModel listModel = integralOrderListAdapter.getItem(position);

            if (listModel == null) return;

            IntegralOrderDetailsActivity.open(mActivity, listModel.getCode());
        });

        //按钮点击
        integralOrderListAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            if (view.getId() == R.id.tv_state_do) {

                IntegralOrderListModel mo = integralOrderListAdapter.getItem(position);

                if (mo == null) return;

                if (TextUtils.equals(mo.getStatus(), OrderHelper.INTEGRALORDERWAITESEND)) { //待发货

                    showDoubleWarnListen("您确定要取消该订单吗?",view1 -> {
                        cancel(mo.getCode());
                    });

                } else if (TextUtils.equals(mo.getStatus(), OrderHelper.INTEGRALORDERWAITEGET)) { //待收货
                    IntegralOrderSureGetActivitty.open(mActivity, mo.getCode());
                } else if (TextUtils.equals(mo.getStatus(), OrderHelper.INTEGRALORDERWAITEECOMMENT)) {//待评价
                    IntegralOrderCommentActivity.open(mActivity, mo.getCode());
                }
            }

        });
        return integralOrderListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("status", mOrderState);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getIntegralOrderList("805294", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<IntegralOrderListModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<IntegralOrderListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无订单", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Subscribe
    public void commentSucc(IntegralOrderCommentsSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.INTEGRALORDERWAITEECOMMENT) || TextUtils.isEmpty(mOrderState)) { //评价成功 如果是待评价页面则刷新
            if (mRefreshHelper != null) {
                mRefreshHelper.onDefaluteMRefresh(false);
            }
        }
    }

    /**
     * 确认收货成功
     *
     * @param da
     */
    @Subscribe
    public void sureGetSucc(IntegralOrderSureGetSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.INTEGRALORDERWAITEGET) || TextUtils.isEmpty(mOrderState)) { //收货成功
            if (mRefreshHelper != null) {
                mRefreshHelper.onDefaluteMRefresh(false);
            }
        }
    }


    @Override
    protected void lazyLoad() {
        if (mRefreshHelper == null) return;
        mRefreshHelper.onDefaluteMRefresh(false);
    }

    @Override
    protected void onInvisible() {

    }

    private void cancel(String code) {
        Map<String, String> object = new HashMap<>();
        object.put("code", code);
        object.put("updater", SPUtilHelpr.getUserId());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805272", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data != null && data.isSuccess()) {
                    UITipDialog.showSuccess(mActivity, getString(R.string.do_success));

                    if (mRefreshHelper != null) {
                        mRefreshHelper.onDefaluteMRefresh(false);
                    }
                } else {
                    UITipDialog.showFall(mActivity, getString(R.string.do_fail));
                }


            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(mActivity, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
