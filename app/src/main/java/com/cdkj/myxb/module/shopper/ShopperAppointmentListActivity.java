package com.cdkj.myxb.module.shopper;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.LayoutCommonRecyclerRefreshBinding;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.ShopperListAdapter;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 美导预约 列表
 * Created by cdkj on 2018/2/9.
 */

public class ShopperAppointmentListActivity extends AbsBaseLoadActivity {


    private RefreshHelper mRefreshHelper;

    private LayoutCommonRecyclerRefreshBinding mBinding;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopperAppointmentListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_common_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle(getString(R.string.shopper_appoint));
        mBaseBinding.titleView.setRightImg(R.drawable.search_waite);

        initRefresh();

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    private void initRefresh() {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.rv;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                ShopperListAdapter shopperListAdapter = new ShopperListAdapter(listData);
                shopperListAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ShopperAppointmentActivity.open(ShopperAppointmentListActivity.this);
                });
                return shopperListAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getShopperListRequest(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);
    }


    public void getShopperListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map map = RetrofitUtils.getRequestMap();
        map.put("kind", UserHelper.T);
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("status", "0");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserList("805120", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<UserModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<UserModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无可预约美导", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

}
