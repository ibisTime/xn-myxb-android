package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.LayoutCommonRecyclerRefreshBinding;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommonAppointmentListAdapter;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 预约 列表
 * Created by cdkj on 2018/2/9.
 */

public class CommonAppointmentListActivity extends AbsBaseLoadActivity {


    private RefreshHelper mRefreshHelper;

    private LayoutCommonRecyclerRefreshBinding mBinding;

    public static final String INTENTTYPE = "type";

    private String mType; //预约类型


    /**
     * @param context
     * @param type    预约类型
     */
    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CommonAppointmentListActivity.class);
        intent.putExtra(INTENTTYPE, type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_common_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {
        AppointmentSearchActivity.open(this, mType);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mType = getIntent().getStringExtra(INTENTTYPE);
        }

        mBaseBinding.titleView.setMidTitle(UserHelper.getAppointmentTypeByState(mType));

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
                CommonAppointmentListAdapter shopperListAdapter = new CommonAppointmentListAdapter(listData);
                shopperListAdapter.setOnItemClickListener((adapter, view, position) -> {

                    if (shopperListAdapter.getItem(position) == null) return;

                    CommonAppointmentUserDetailActivity.open(CommonAppointmentListActivity.this, shopperListAdapter.getItem(position).getUserId(), mType);
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

        if (TextUtils.isEmpty(mType)) return;

        Map map = RetrofitUtils.getRequestMap();
        map.put("kind", mType);
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("status", "0");//上架

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
