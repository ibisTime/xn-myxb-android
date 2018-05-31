package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityResultAuditBinding;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.models.event.EventResultPaySuccessDoClose;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.user.UserHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/11.
 */

public class ResultAuditActivity extends AbsBaseLoadActivity {

    private ActivityResultAuditBinding mBinding;

    private String mCode;
    private String mType;

    private AppointmentListModel mAppmModel;

    private int PHOTOFLAG = 119;
    private String confirmUrl = "";

    /**
     * @param context
     */
    public static void open(Context context, String code, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultAuditActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result_audit, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mType = getIntent().getStringExtra("type");
        }


        mBaseBinding.titleView.setMidTitle(UserHelper.getUserTypeByKind(mType) + "详情");

        mBinding.tvAppioType.setText("预约" + UserHelper.getUserTypeByKind(mType));

        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppointmentDetail(false);
    }

    private void initListener() {

        mBinding.btnConfirm.setOnClickListener(view -> {
            approve("1");
        });
        mBinding.btnNo.setOnClickListener(view -> {
            approve("0");
        });

        mBinding.imgConfirm.setOnClickListener(view -> {
            ImageSelectActivity.launch(this, PHOTOFLAG, false);
        });
    }

    /**
     * 获取预约详情
     */
    private void getAppointmentDetail(boolean isOpenPay) {

        if (TextUtils.isEmpty(mCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAppointmentDetails("805521", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<AppointmentListModel>(this) {
            @Override
            protected void onSuccess(AppointmentListModel data, String SucMessage) {
                mAppmModel = data;
                sheShowData(data);

                if (isOpenPay){
                    ResultPayActivity.open(ResultAuditActivity.this, mAppmModel.getCode(), MoneyUtils.getShowPriceSign(mAppmModel.getDeductAmount()));
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void sheShowData(AppointmentListModel data) {
        if (data.getUser() != null) {
            mBinding.tvName.setText(data.getUser().getRealName());
        }

        mBinding.tvAppioTime.setText(DateUtil.formatStringData(data.getApplyDatetime(), DateUtil.DATE_YYMMddHHmm));
        mBinding.tvAppioDays.setText(data.getAppointDays() + "天");
        mBinding.tvAppioTimePlan.setText(DateUtil.formatStringData(data.getPlanDatetime(), DateUtil.DATE_YYMMddHHmm));
        mBinding.linPlanTime.setVisibility(TextUtils.isEmpty(data.getPlanDatetime()) ? View.GONE : View.VISIBLE);

        mBinding.tvState.setText(OrderHelper.getAppoitmentState(data.getStatus()));

        mBinding.edtClient.setText(data.getClientNumber()+"");
        mBinding.tvDateTime.setText(DateUtil.formatStringData(data.getRealDatetime(), DateUtil.DATE_YYMMddHHmm));
        mBinding.edtDays.setText(data.getRealDays()+"");
        mBinding.edtDays.setText(data.getRealDays()+"");
        mBinding.edtSale.setText(MoneyUtils.showPrice(data.getSaleAmount()));
        mBinding.edtSuc.setText(data.getSucNumber()+"");
        ImgUtils.loadQiniuImg(this, data.getPdf(), mBinding.imgConfirm);

    }

    private void approve(String approveResult) {
        if (TextUtils.equals(confirmUrl, "")){
            ToastUtil.show(this, "请上传成果确认函");
            return;
        }

        Map<String, String> object = new HashMap<>();
        object.put("approveNote", "");
        object.put("pdf", confirmUrl);
        object.put("approveResult", approveResult);
        object.put("approver", SPUtilHelpr.getUserId());
        object.put("code", mAppmModel.getCode());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805517", StringUtils.getJsonToString(object));


        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    UITipDialog.showSuccess(ResultAuditActivity.this, getString(R.string.do_success), dialogInterface -> {

                        if (TextUtils.equals(mType, UserHelper.S)){
                            getAppointmentDetail(true);
                        }else {
                            finish();
                        }

                    });
                }else {

                    UITipDialog.showFall(ResultAuditActivity.this, getString(R.string.do_fail));

                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ResultAuditActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Subscribe
    public void doColose(EventResultPaySuccessDoClose addressModel) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(CameraHelper.staticPath);
            LogUtil.E("拍照获取路径" + path);
            showLoadingDialog();
            new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
                @Override
                public void onSuccess(String key) {
                    confirmUrl = key;
                    ImgUtils.loadQiniuImg(ResultAuditActivity.this, key, mBinding.imgConfirm);
                }

                @Override
                public void onFal(String info) {
                    disMissLoading();
                }
            }, path);

        }
    }
}
