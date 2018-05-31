package com.cdkj.myxb.module.order;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.OrderListAdapter;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.IntegralOrderCommentsSucc;
import com.cdkj.myxb.models.IntegralOrderSureGetSucc;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.models.event.EventDoChangeCountModel;
import com.cdkj.myxb.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 订单列表
 * Created by cdkj on 2018/2/23.
 */

public class OrderListFragment extends BaseLazyFragment {

    private static final String ORDERSTATE = "state";
    private static final String ISFIRSTREQUEST = "isFirstRequest";

    private String mOrderState; //要查看的订单状态

    protected LayoutRecyclerRefreshBinding mRefreshBinding;

    protected RefreshHelper mRefreshHelper;

    /**
     * @param state          订单状态
     * @param isFirstRequest 创建时是否进行请求
     * @return
     */
    public static OrderListFragment getInstance(String state, boolean isFirstRequest) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERSTATE, state);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRefreshBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);

        if (getArguments() != null) {
            mOrderState = getArguments().getString(ORDERSTATE);

        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }

        return mRefreshBinding.getRoot();
    }

    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mRefreshBinding.refreshLayout;
            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                super.onRefresh(pageindex, limit);

                EventDoChangeCountModel model = new EventDoChangeCountModel();
                model.setOrder(true);
                EventBus.getDefault().post(model);
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mRefreshBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getListRequest(pageindex, limit, isShowDialog);
            }
        });
        mRefreshHelper.init(limit);

    }

    public RecyclerView.Adapter getListAdapter(List listData) {

        OrderListAdapter integralOrderListAdapter = new OrderListAdapter(listData);

        integralOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {

            OrderListModel listModel = integralOrderListAdapter.getItem(position);

            if (listModel == null) return;

            OrderDetailsActivity.open(mActivity, listModel.getCode());
        });

        //按钮点击
        integralOrderListAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            if (view.getId() == R.id.tv_state_do) {

                OrderListModel mOrderListModel = integralOrderListAdapter.getItem(position);

                if (TextUtils.equals(mOrderListModel.getStatus(), OrderHelper.ORDER_WAITE_COMMENT)){

                    OrderCommentActivity.open(mActivity, mOrderListModel.getCode());

                }else if(TextUtils.equals(mOrderListModel.getStatus(), OrderHelper.ORDER_WAITE_PAY)) {

                    showDoubleWarnListen("您确定要取消该订单吗？",view1 -> {
                        cancel(mOrderListModel.getCode());
                    });
                }

            }

        });
        return integralOrderListAdapter;
    }

    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("status", mOrderState);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getOrderList("805276", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<OrderListModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<OrderListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无订单", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 评价成功
     *
     * @param
     */
    @Subscribe
    public void commentSucc(IntegralOrderCommentsSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.ORDER_WAITE_COMMENT) || TextUtils.isEmpty(mOrderState)) { //评价成功 如果是待评价页面则刷新
            if (mRefreshHelper != null) {
                mRefreshHelper.onDefaluteMRefresh(false);
            }
        }
    }

    /**
     * @param da
     */
    @Subscribe
    public void sureGetSucc(IntegralOrderSureGetSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.ORDER_WAITE_COMMENT) || TextUtils.isEmpty(mOrderState)) { //收货成功
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

    /**
     * 取消订单
     */
    private void cancel(String mOrderCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", mOrderCode);
        map.put("userId", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.getBaseAPiService().successRequest("805273", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(mActivity, "取消成功", dialogInterface -> {
                    mRefreshHelper.onDefaluteMRefresh(true);
                });
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
