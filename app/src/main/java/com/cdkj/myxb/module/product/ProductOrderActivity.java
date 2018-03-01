package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.cdkj.myxb.databinding.ActivityAppointmentOrderBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.address.AddressListActivity;
import com.cdkj.myxb.module.order.MyOrderActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 产品下单界面
 * Created by cdkj on 2018/2/24.
 */

public class ProductOrderActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentOrderBinding mBinding;

    private String mProductCode;

    private static final String PRODUCTCODE = "productcode";

    private AddressModel mAddress;

    /**
     * @param context
     * @param productCode
     */
    public static void open(Context context, String productCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductOrderActivity.class);
        intent.putExtra(PRODUCTCODE, productCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_order, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("下单");


        if (getIntent() != null) {
            mProductCode = getIntent().getStringExtra(PRODUCTCODE);
        }

        initListener();
    }

    private void initListener() {
        //确认下单
        mBinding.btnSureOrder.setOnClickListener(view -> {
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

        if (TextUtils.isEmpty(mProductCode)) {
            UITipDialog.showInfo(this, "请选择地址xx");
            return;
        }

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
        if (TextUtils.isEmpty(mBinding.editQuantity.getText().toString()) || Integer.parseInt(mBinding.editQuantity.getText().toString()) <= 0) {
            UITipDialog.showInfo(this, "请填写数量");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editApplyNote.getText().toString())) {
            UITipDialog.showInfo(this, "请填写下单说明");
            return;
        }

        if (!SPUtilHelpr.isLogin(this, false)) {
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("productCode", mProductCode);
        map.put("quantity", mBinding.editQuantity.getText().toString());
        map.put("reAddress", mBinding.layoutAddress.tvAddress.getText().toString());
        map.put("reMobile", mBinding.layoutAddress.tvGetPhone.getText().toString());
        map.put("receiver", mBinding.layoutAddress.tvGetName.getText().toString());
        map.put("applyNote", mBinding.editApplyNote.getText().toString());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805270", StringUtils.getJsonToString(map));

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog();
        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                MyOrderActivity.open(ProductOrderActivity.this);
                finish();
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


}
