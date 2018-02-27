package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentShopperBinding;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.appointment.CommonAppointmentListActivity.INTENTTYPE;

/**
 * 预约下单
 * Created by cdkj on 2018/2/9.
 */

public class CommonAppointmentOrderActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentShopperBinding mBinding;
    private Calendar startCalendar;
    private Calendar endCalendar;

    private String mShopperId;

    private static final String SHOPPERID = "shopperId";

    private String mType; //预约类型


    /**
     * @param context
     * @param shopperId 美导Id
     */
    public static void open(Context context, String shopperId,String title) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CommonAppointmentOrderActivity.class);
        intent.putExtra(SHOPPERID, shopperId);
        intent.putExtra(INTENTTYPE, title);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_shopper, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mShopperId = getIntent().getStringExtra(SHOPPERID);
            mType = getIntent().getStringExtra(INTENTTYPE);
        }

        mBaseBinding.titleView.setMidTitle(UserHelper.getAppointmentTypeByState(mType));

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        mBinding.linStartDate.setOnClickListener(view -> getNowDate());

        mBinding.btnSureOrder.setOnClickListener(view -> appointmentRequest());

    }

    /**
     * 预约请求
     */
    private void appointmentRequest() {

        if (TextUtils.isEmpty(mBinding.tvStartDate.getText().toString())) {
            UITipDialog.showInfo(this, "请选择预约开始时间");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editQuantity.getText().toString()) || Integer.parseInt(mBinding.editQuantity.getText().toString()) <= 0) {
            UITipDialog.showInfo(this, "请填写数量");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editApplyNote.getText().toString())) {
            UITipDialog.showInfo(this, "请填写预约说明");
            return;
        }

        if (TextUtils.isEmpty(mShopperId)) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("applyNote", mBinding.editApplyNote.getText().toString());
        map.put("appointDatetime", mBinding.tvStartDate.getTag().toString());
        map.put("appointDays", mBinding.editQuantity.getText().toString());
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("owner", mShopperId);

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("805510", StringUtils.getJsonToString(map));

        showLoadingDialog();

        addCall(call);


        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getCode())) {
                    MyAppointmentActivity.open(CommonAppointmentOrderActivity.this);
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    /**
     * 显示日期picker
     */
    private void showDatePicker() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mBinding.tvStartDate.setText(DateUtil.format(date, DateUtil.DATE_YYMMddHHmm));
                mBinding.tvStartDate.setTag(DateUtil.format(date, DateUtil.DEFAULT_DATE_FMT));
            }

        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", null)
                .setRange(startCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.YEAR))
                .isCyclic(true)
                .build();

        pvTime.show();
    }

    /**
     * 获取当前时间
     */
    public void getNowDate() {

        Map<String, String> map = new HashMap<>();

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805126", StringUtils.getJsonToString(map));

        showLoadingDialog();

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                Date date = DateUtil.parse(data, DateUtil.DEFAULT_DATE_FMT);
                startCalendar.setTime(date);
                endCalendar.setTime(date);
                int year = endCalendar.get(Calendar.YEAR);
                endCalendar.set(Calendar.YEAR, year + 5);
                showDatePicker();

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

}
