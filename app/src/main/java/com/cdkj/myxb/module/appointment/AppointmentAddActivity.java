package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentAddBinding;
import com.cdkj.myxb.models.AppointmentTypeModel;
import com.cdkj.myxb.models.TripListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/23.
 */

public class AppointmentAddActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentAddBinding mBinding;

    private Calendar startCalendar;
    private Calendar endCalendar;

    private String mKind;//用户的登录类型
    private List<AppointmentTypeModel> mTypes;
    private OptionsPickerView mTypePicker;//登录类型选择

    private String code;

    public static void open(Context context,String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentAddActivity.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_add, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {



        if(getIntent() == null)
            return;

        code = getIntent().getStringExtra("code");

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        initListener();


        if (!TextUtils.isEmpty(code)){
            getAppointmentRequest();
            mBaseBinding.titleView.setMidTitle("修改行程");
            mBinding.btnAdd.setText("确定修改");
        }else {
            mBaseBinding.titleView.setMidTitle("新增行程");
            mBinding.btnAdd.setText("确定新增");
        }
    }

    private void initListener() {
        mBinding.linStartDate.setOnClickListener(view -> {
            getNowDate(true);
        });

        mBinding.linStopDate.setOnClickListener(view -> {
            getNowDate(false);
        });

        mBinding.linType.setOnClickListener(view -> {
            if (mTypePicker == null) {
                initPickerView();
            }
            mTypePicker.setPicker(mTypes);
            mTypePicker.show();
        });

        mBinding.btnAdd.setOnClickListener(view -> {

            if (check())
                addRequest();
        });
    }

    public void initPickerView() {

        initPickerData();

        mTypePicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {

            AppointmentTypeModel loginTypeModel = mTypes.get(options1);

            mKind = loginTypeModel.getType();

            mBinding.tvType.setText(loginTypeModel.getTypeString());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();


    }

    private void initPickerData() {

        mTypes = new ArrayList<>();

        AppointmentTypeModel typeModel = new AppointmentTypeModel();
        typeModel.setType("1");
        typeModel.setTypeString("可预约");

        mTypes.add(typeModel);

        AppointmentTypeModel typeModel2 = new AppointmentTypeModel();
        typeModel2.setType("2");
        typeModel2.setTypeString("可调配时间");

        mTypes.add(typeModel2);
    }

    private boolean check(){

        if (TextUtils.isEmpty(mBinding.tvStartDate.getText().toString())) {
            UITipDialog.showInfo(this, "请选择开始时间");
            return false;
        }

        if (TextUtils.isEmpty(mBinding.tvStopDate.getText().toString())) {
            UITipDialog.showInfo(this, "请选择结束时间");
            return false;
        }

        if (TextUtils.isEmpty(mBinding.tvType.getText().toString())) {
            UITipDialog.showInfo(this, "请选择类型");
            return false;
        }

        return true;
    }

    public void addRequest() {


        Map<String, String> map = new HashMap<>();
        map.put("updater", SPUtilHelpr.getUserId());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("startDatetime", mBinding.tvStartDate.getTag().toString());
        map.put("endDatetime", mBinding.tvStopDate.getTag().toString());
        map.put("type", mKind);

        String requestCode;
        if (!TextUtils.isEmpty(code)){
            map.put("code", code);

            requestCode = "805502";

        }else {
            requestCode = "805500";
        }

        Call call = RetrofitUtils.getBaseAPiService().stringRequest(requestCode, StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<String>(AppointmentAddActivity.this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {

                String tipString;
                if (!TextUtils.isEmpty(code)){
                    tipString = "修改成功";

                }else {
                    tipString = "新增成功";
                }

                UITipDialog.showSuccess(AppointmentAddActivity.this, tipString, dialogInterface -> {
                    finish();
                });

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 获取当前时间
     */
    public void getNowDate(boolean isStart) {

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
                showDatePicker(isStart);

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
    private void showDatePicker(boolean isStart) {
        TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
            if (isStart){
                mBinding.tvStartDate.setText(DateUtil.format(date, DateUtil.DATE_YYMMddHHmm));
                mBinding.tvStartDate.setTag(DateUtil.format(date, DateUtil.DEFAULT_DATE_FMT));
            }else {
                mBinding.tvStopDate.setText(DateUtil.format(date, DateUtil.DATE_YYMMddHHmm));
                mBinding.tvStopDate.setTag(DateUtil.format(date, DateUtil.DEFAULT_DATE_FMT));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", null)
                .setRange(startCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.YEAR))
                .isCyclic(true)
                .build();

        pvTime.show();
    }


    public void getAppointmentRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getTrip("805507", StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<TripListModel>(AppointmentAddActivity.this) {
            @Override
            protected void onSuccess(TripListModel data, String SucMessage) {

                if (data == null)
                    return;

                mBinding.tvStartDate.setText(DateUtil.formatStringData(data.getStartDatetime(), DateUtil.DATE_YYMMddHHmm));
                mBinding.tvStartDate.setTag(DateUtil.formatStringData(data.getStartDatetime(), DateUtil.DEFAULT_DATE_FMT));

                mBinding.tvStopDate.setText(DateUtil.formatStringData(data.getEndDatetime(), DateUtil.DATE_YYMMddHHmm));
                mBinding.tvStopDate.setTag(DateUtil.formatStringData(data.getEndDatetime(), DateUtil.DEFAULT_DATE_FMT));

                mKind = data.getType();
                switch (data.getType()){
                    case "1":
                        mBinding.tvType.setText("可预约");
                        break;

                    case "2":
                        mBinding.tvType.setText("可调配时间");
                        break;
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

}
