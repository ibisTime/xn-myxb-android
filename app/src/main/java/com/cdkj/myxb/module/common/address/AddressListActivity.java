package com.cdkj.myxb.module.common.address;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AddressListAdapter;
import com.cdkj.myxb.databinding.ActivityAddressListBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 地址列表
 * Created by cdkj on 2018/2/22.
 */

public class AddressListActivity extends AbsBaseLoadActivity {

    private ActivityAddressListBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private boolean isSelect;

    /**
     * 是否进行地址选择
     *
     * @param context
     * @param isSelect
     */
    public static void open(Context context, boolean isSelect) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AddressListActivity.class);
        intent.putExtra("isSelect", isSelect);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_address_list, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.address_manager));
        if (getIntent() != null) {
            isSelect = getIntent().getBooleanExtra("isSelect", false);
        }
        initRefreshHelper();
        mBinding.tvAdd.setOnClickListener(view -> AddAddressActivity.open(this, null, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshHelper.onDefaluteMRefresh(false);
    }


    /**
     * 初始化刷新相关
     */
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

                AddressListAdapter addressListAdapter = new AddressListAdapter(listData);
                addressListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.layout_delete://删除
                                deleteAddress(position);
                                break;
                            case R.id.layout_edit://编辑
                                AddressModel addressModel = (AddressModel) addressListAdapter.getItem(position);
                                AddAddressActivity.open(AddressListActivity.this, addressModel, false);
                                break;

                            case R.id.real_address://设置默认地址
                                AddressModel addr = (AddressModel) addressListAdapter.getItem(position);
                                if (addr == null || addr.isDefaultAddress()) return;
                                setDefaultAddress(addr.getCode());
                                break;
                        }

                    }
                });

                addressListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (isSelect) { //选择地址
                            AddressModel addr = (AddressModel) addressListAdapter.getItem(position);
                            EventBus.getDefault().post(addr);
                            finish();
                        }
                    }
                });

                return addressListAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getAddressRequest(false);
            }
        });
        mRefreshHelper.init(10);

    }

    /**
     * 获取地址请求
     *
     * @param canShowDialog
     */
    public void getAddressRequest(boolean canShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getAddress("805165", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AddressModel>(this) {

            @Override
            protected void onSuccess(List<AddressModel> data, String SucMessage) {
                mRefreshHelper.setData(data, getString(R.string.no_address), 0);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                mRefreshHelper.loadError(errorMessage, 0);
            }

            @Override
            protected void onFinish() {
                if (canShowDialog) disMissLoading();
            }
        });
    }

    /**
     * 删除
     */
    private void deleteAddress(final int po) {
        showDoubleWarnListen(getString(R.string.sure_delete_address), new CommonDialog.OnPositiveListener() {
            @Override
            public void onPositive(View view) {
                deleteRequest(po);
            }
        });
    }


    /**
     * 删除请求
     */
    private void deleteRequest(final int position) {

        AddressModel addressModel = (AddressModel) mRefreshHelper.getmAdapter().getItem(position);

        if (addressModel == null || TextUtils.isEmpty(addressModel.getCode())) {
            return;
        }

        Map<String, String> object = new HashMap<>();

        object.put("code", addressModel.getCode());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyCdConfig.SYSTEMCODE);
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805161", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data != null && data.isSuccess()) {
                    if (mRefreshHelper.getmAdapter() != null)
                        mRefreshHelper.onDefaluteMRefresh(true);
                } else {
                    UITipDialog.showFall(AddressListActivity.this, getString(R.string.delete_fall));
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(AddressListActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    /**
     * 删除
     */
    private void setDefaultAddress(final String po) {
        showDoubleWarnListen(getString(R.string.sure_setdefault_address), new CommonDialog.OnPositiveListener() {
            @Override
            public void onPositive(View view) {
                setDefaultAddressRequest(po);
            }
        });
    }

    /**
     * 设置默认地址请求
     */
    public void setDefaultAddressRequest(String code) {

        if (TextUtils.isEmpty(code)) {
            return;
        }
        Map<String, String> map = new HashMap<>();

        map.put("code", code);
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        Call call = RetrofitUtils.createApi(MyApiServer.class).setDefultAddress("805163", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<Boolean>(this) {

            @Override
            protected void onSuccess(Boolean data, String SucMessage) {
                if (data) {
                    mRefreshHelper.onDefaluteMRefresh(false);
                } else {
                    UITipDialog.showFall(AddressListActivity.this, "操作失败");
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(AddressListActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
