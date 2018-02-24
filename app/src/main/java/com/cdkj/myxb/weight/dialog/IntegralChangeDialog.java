package com.cdkj.myxb.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.DialogIntegralChangeBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.module.common.address.AddressListActivity;
import com.cdkj.myxb.module.order.integral.MyIntegralOrderActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 积分兑换
 * Created by cdkj on 2018/2/10.
 */

public class IntegralChangeDialog extends Dialog {

    private DialogIntegralChangeBinding mBinding;

    private String mProductCode;
    private LoadingDialog loadingDialog;

    public IntegralChangeDialog(@NonNull Context context, String productCode) {
        super(context, R.style.TipsDialog);
        mProductCode = productCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_integral_change, null, false);
        int screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        setContentView(mBinding.getRoot());
        getWindow().setLayout((int) (screenWidth * 0.9f), ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        initListener();

    }

    private void initListener() {
        //取消
        mBinding.btnCancel.setOnClickListener(view -> {
            dismiss();
        });

        //地址选择
        mBinding.relaLayoutSelectAddress.setOnClickListener(view -> {
            AddressListActivity.open(getContext(), true);
        });

        //兑换
        mBinding.btnChange.setOnClickListener(view -> {
            changePayRequest();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void setAddress(AddressModel addressModel) {
        mBinding.tvIntegralAddress.setText(addressModel.getProvince() + " " + addressModel.getCity() + " " + addressModel.getDistrict() + "" + addressModel.getDetailAddress());
        mBinding.editName.setText(addressModel.getAddressee());
        mBinding.editPhone.setText(addressModel.getMobile());
    }


    public void changePayRequest() {

        if (TextUtils.isEmpty(mProductCode) || TextUtils.isEmpty(mBinding.tvIntegralAddress.getText().toString())
                || TextUtils.isEmpty(mBinding.editPhone.getText().toString()) || TextUtils.isEmpty(mBinding.editName.getText().toString())) {

            return;

        }

        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("productCode", mProductCode);
        map.put("quantity", "1");
        map.put("reAddress", mBinding.tvIntegralAddress.getText().toString());
        map.put("reMobile", mBinding.editPhone.getText().toString());
        map.put("receiver", mBinding.editName.getText().toString());

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805290", StringUtils.getJsonToString(map));

        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.showDialog();
        call.enqueue(new BaseResponseModelCallBack<String>(getContext()) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                if (!TextUtils.isEmpty(data)) {
                    MyIntegralOrderActivity.open(getContext());
                    dismiss();
                }
            }

            @Override
            protected void onFinish() {
                loadingDialog.dismiss();
            }
        });

    }

}
