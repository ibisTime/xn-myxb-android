package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentOrderBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.models.event.EventPaySuccessDoClose;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.address.AddressListActivity;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.utils.MoneyUtils.MONEYSING;

/**
 * 产品下单界面
 * Created by cdkj on 2018/2/24.
 */

public class ProductOrderActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentOrderBinding mBinding;

    private BrandProductModel mModel;

    private static final String MODEL = "model";

    private AddressModel mAddress;

    /**
     * @param context
     * @param model
     */
    public static void open(Context context, BrandProductModel model) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductOrderActivity.class);
        intent.putExtra(MODEL, model);
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

        initListener();

        if (getIntent() != null) {
            mModel = getIntent().getParcelableExtra(MODEL);

            mBinding.editQuantity.setText("1");
        }


    }

    private void initListener() {
        mBinding.tvCart.setOnClickListener(view -> {
            addToShopCart();
        });

        //确认下单
        mBinding.tvSureOrder.setOnClickListener(view -> {
            changePayRequest();
        });

        mBinding.editQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.equals(editable.toString(),"")){
                    mBinding.tvAmount.setText(MONEYSING+"0.00");
                }else {
                    BigDecimal quantity = new BigDecimal(editable.toString().trim());
                    mBinding.tvAmount.setText(MoneyUtils.getShowPriceSign(quantity.multiply(mModel.getPrice())));
                }
            }
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

        if (TextUtils.isEmpty(mModel.getCode())) {
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
        if (TextUtils.isEmpty(mBinding.editQuantity.getText().toString()) || StringUtils.parseInt(mBinding.editQuantity.getText().toString()) <= 0) {
            UITipDialog.showInfo(this, "请填写正确的下单数量");
            return;
        }

        if (!SPUtilHelpr.isLogin(this, false)) {
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("productCode", mModel.getCode());
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
                UITipDialog.showSuccess(ProductOrderActivity.this, "下单成功", dialogInterface -> {
//                    MyOrderActivity.open(ProductOrderActivity.this);

                    ProductPayActivity.open(ProductOrderActivity.this, data);
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


    private void addToShopCart() {
        Map<String, String> map = new HashMap<>();
        map.put("productCode", mModel.getCode());
        map.put("quantity", mBinding.editQuantity.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805290", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<String>(this) {

            @Override
            protected void onSuccess(String data, String SucMessage) {
                UITipDialog.showSuccess(ProductOrderActivity.this, getString(R.string.do_success));
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ProductOrderActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Subscribe
    public void doColose(EventPaySuccessDoClose addressModel) {
        finish();
    }


}
