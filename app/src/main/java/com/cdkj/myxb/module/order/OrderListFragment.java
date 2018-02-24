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
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.IntegralOrderListAdapter;
import com.cdkj.myxb.adapters.OrderListAdapter;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.IntegralOrderCommentsSucc;
import com.cdkj.myxb.models.IntegralOrderListModel;
import com.cdkj.myxb.models.IntegralOrderSureGetSucc;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.integral.IntegralOrderCommentActivity;
import com.cdkj.myxb.module.order.integral.IntegralOrderDetailsActivity;
import com.cdkj.myxb.module.order.integral.IntegralOrderSureGetActivitty;

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


    private LayoutRecyclerRefreshBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private static final String ORDERSTATE = "state";
    private static final String ISFIRSTREQUEST = "isFirstRequest";

    private String mOrderState; //要查看的订单状态

    /**
     * @param state          订单状态
     * @param isFirstRequest 创建时是否进行请求
     * @return
     */
    public static OrderListFragment getInstanse(String state, boolean isFirstRequest) {
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

        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_recycler_refresh, null, false);

        if (getArguments() != null) {
            mOrderState = getArguments().getString(ORDERSTATE);

        }

        initRefreshHelper();
        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }
        return mBinding.getRoot();
    }

    private void initRefreshHelper() {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
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
                return getOrderListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getOrderListRequest(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);
    }

    /**
     * 获取数据适配器
     *
     * @param listData
     * @return
     */
    @NonNull
    private OrderListAdapter getOrderListAdapter(List listData) {

        OrderListAdapter integralOrderListAdapter = new OrderListAdapter(listData);

        integralOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {

            OrderListModel listModel = integralOrderListAdapter.getItem(position);

            if (listModel == null) return;

            IntegralOrderDetailsActivity.open(mActivity, listModel.getCode());
        });

        //按钮点击
        integralOrderListAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            if (view.getId() == R.id.tv_state_do) {

                OrderListModel mo = integralOrderListAdapter.getItem(position);

                if (mo == null) return;

                IntegralOrderCommentActivity.open(mActivity, mo.getCode());
            }

        });
        return integralOrderListAdapter;
    }

    /**
     *
     */
    public void getOrderListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", mOrderState);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getOrderList("805273", StringUtils.getJsonToString(map));

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

    @Subscribe
    public void commentSucc(IntegralOrderCommentsSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.INTEGRALORDERWAITEEVALUATION) || TextUtils.isEmpty(mOrderState)) { //评价成功 如果是待评价页面则刷新
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
}
