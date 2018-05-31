package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.ShopCartListAdapter;
import com.cdkj.myxb.databinding.ActivityShopCartOrderBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.models.ShopCartListModel;
import com.cdkj.myxb.models.event.EventPaySuccessDoClose;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.address.AddressListActivity;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 购物车下单界面
 * Created by cdkj on 2018/2/24.
 */

public class ShopCartOrderActivity extends AbsBaseLoadActivity {

    private ActivityShopCartOrderBinding mBinding;

    private static String MODEL = "mShopCartList";
    private static String AMOUNT = "amount";

    private String amount;
    private List<ShopCartListModel> mShopCartList;

    private AddressModel mAddress;

    /**
     * @param context
     */
    public static void open(Context context, List<ShopCartListModel> mShopCartList, String amount) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopCartOrderActivity.class);
        intent.putExtra(MODEL, (Serializable) mShopCartList);
        intent.putExtra(AMOUNT, amount);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_cart_order, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("下单");

        initListener();

        if (getIntent() != null) {
            amount = getIntent().getStringExtra(AMOUNT);
            mShopCartList = (List<ShopCartListModel>) getIntent().getSerializableExtra(MODEL);

            initView();
        }


    }

    private void initView() {

        mBinding.tvAmount.setText(amount);

        mBinding.rvCartList.setAdapter(getListAdapter());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mBinding.rvCartList.setLayoutManager(linearLayoutManager);

    }

    public ShopCartListAdapter getListAdapter() {
        ShopCartListAdapter shopCartListAdapter = new ShopCartListAdapter(mShopCartList,true);

        return shopCartListAdapter;
    }

    private void initListener() {

        //确认下单
        mBinding.tvSureOrder.setOnClickListener(view -> {
            changePayRequest();
        });


        //选择地址
        mBinding.layoutAddress.linChangeAddress.setOnClickListener(view -> AddressListActivity.open(this, true));
        mBinding.layoutAddress.layoutNoAddress.setOnClickListener(view -> AddressListActivity.open(this, true));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressRequest(true);
    }

    public void changePayRequest() {

        if (mAddress == null) {
            UITipDialog.showInfo(this, "请选择地址");
            return;
        }

        if (TextUtils.isEmpty(mBinding.layoutAddress.tvAddress.getText().toString())) {
            UITipDialog.showInfo(this, "请设置收货地址");
            return;
        }
        if (TextUtils.isEmpty(mBinding.layoutAddress.tvGetPhone.getText().toString())) {
            UITipDialog.showInfo(this, "请设置收货人电话");
            return;
        }
        if (TextUtils.isEmpty(mBinding.layoutAddress.tvGetPhone.getText().toString())) {
            UITipDialog.showInfo(this, "请设置收货人姓名");
            return;
        }

        if (!SPUtilHelpr.isLogin(this, false)) {
            return;
        }

        List<String> cartCodeList = new ArrayList<>();
        for (ShopCartListModel model : mShopCartList){
            cartCodeList.add(model.getCode());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("cartCodeList", cartCodeList);
        map.put("reAddress", mBinding.layoutAddress.tvAddress.getText().toString());
        map.put("reMobile", mBinding.layoutAddress.tvGetPhone.getText().toString());
        map.put("receiver", mBinding.layoutAddress.tvGetName.getText().toString());
        map.put("applyNote", mBinding.editApplyNote.getText().toString());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805271", StringUtils.getJsonToString(map));

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog();
        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                UITipDialog.showSuccess(ShopCartOrderActivity.this, "下单成功", dialogInterface -> {
//                    MyOrderActivity.open(ProductOrderActivity.this);

                    ProductPayActivity.open(ShopCartOrderActivity.this, data);
                });
            }


            @Override
            protected void onFinish() {
                loadingDialog.dismiss();
            }
        });

    }


    /**
     * 获取默认地址请求
     *
     * @param canShowDialog
     */
    public void getAddressRequest(boolean canShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("isDefault", "1");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getAddress("805165", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AddressModel>(this) {

            @Override
            protected void onSuccess(List<AddressModel> data, String SucMessage) {
                if (data != null && !data.isEmpty()) {
                    mAddress = data.get(0);
                    mBinding.layoutAddress.layoutNoAddress.setVisibility(View.GONE);
                    mBinding.layoutAddress.linChangeAddress.setVisibility(View.VISIBLE);
                } else {
                    mBinding.layoutAddress.layoutNoAddress.setVisibility(View.VISIBLE);
                    mBinding.layoutAddress.linChangeAddress.setVisibility(View.GONE);
                }

                setAddressData();

            }

            @Override
            protected void onFinish() {
                mBinding.layoutAddress.fraAddress.setVisibility(View.VISIBLE);
                if (canShowDialog) disMissLoading();
            }
        });
    }

    private void setAddressData() {
        if (mAddress == null) return;
        mBinding.layoutAddress.tvAddress.setText(mAddress.getProvince() + " " + mAddress.getCity() + " " + mAddress.getDistrict() + "" + mAddress.getDetailAddress());
        mBinding.layoutAddress.tvGetName.setText(mAddress.getAddressee());
        mBinding.layoutAddress.tvGetPhone.setText(mAddress.getMobile());
    }


    @Subscribe
    public void setAddress(AddressModel addressModel) {
        mAddress = addressModel;
        setAddressData();
    }

    @Subscribe
    public void doColose(EventPaySuccessDoClose addressModel) {
        finish();
    }

}
