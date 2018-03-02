package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentDetailsBinding;
import com.cdkj.myxb.databinding.ActivityResultOrderDetailsBinding;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.appointment.AppointmentCommentActivity;
import com.cdkj.myxb.module.appointment.AppointmentDoActivity;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 预约成果订单详情
 * Created by cdkj on 2018/2/26.
 */

public class ResultOrderDetailActivity extends AbsBaseLoadActivity {

    private ActivityResultOrderDetailsBinding mBinding;
    private String mCode;

    private String mType;  //用户类型

    private AppointmentListModel mAppmModel;

    /**
     * @param context
     */
    public static void open(Context context, String code, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultOrderDetailActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result_order_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mType = getIntent().getStringExtra("type");
        }

        mBaseBinding.titleView.setMidTitle(UserHelper.getUserTypeByKind(mType) + "详情");

        initListener();

    }

    private void initListener() {

        mBinding.btnStateDo.setOnClickListener(view -> { //上门 下课
            if (mAppmModel == null) return;
            AppointmentDoActivity.open(this, mAppmModel.getCode(), mAppmModel.getStatus());
        });
        mBinding.btnToComment.setOnClickListener(view -> {  //评价
            if (mAppmModel == null) return;
            AppointmentCommentActivity.open(this, mAppmModel.getCode(), mType);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppointmentDetail();
    }

    /**
     * 获取预约详情
     */
    private void getAppointmentDetail() {

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
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void sheShowData(AppointmentListModel data) {
        if (data.getMryUser() != null) {
            mBinding.tvName.setText(data.getMryUser().getRealName());
        }

        mBinding.tvAppioTime.setText(DateUtil.formatStringData(data.getApplyDatetime(), DateUtil.DATE_YYMMddHHmm));
        mBinding.tvAppioDays.setText(data.getAppointDays() + "天");

        mBinding.tvAppioTimePlan.setText(DateUtil.formatStringData(data.getPlanDatetime(), DateUtil.DATE_YYMMddHHmm));
        mBinding.tvAppioDaysPlan.setText(data.getAppointDays() + "天");

        mBinding.linPlanTime.setVisibility(TextUtils.isEmpty(data.getPlanDatetime()) ? View.GONE : View.VISIBLE);
        mBinding.linPlanDays.setVisibility(TextUtils.isEmpty(data.getPlanDatetime()) ? View.GONE : View.VISIBLE);


        mBinding.tvState.setText(OrderHelper.getAppoitmentState(data.getStatus()));

        mBinding.btnStateDo.setVisibility(OrderHelper.canShowAppointmentButton(data.getStatus()) ? View.VISIBLE : View.GONE);
//        mBinding.btnToComment.setVisibility(OrderHelper.canAppointmentComment(data) ? View.VISIBLE : View.GONE);

        mBinding.btnStateDo.setText(OrderHelper.getAppointmentBtnStateString(data.getStatus()));


    }


}
