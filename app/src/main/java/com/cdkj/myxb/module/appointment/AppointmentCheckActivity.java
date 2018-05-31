package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityAppointmentCheckBinding;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.weight.dialog.TripTimeDialog;
import com.cdkj.myxb.weight.views.TripDateView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/23.
 */

public class AppointmentCheckActivity extends AbsBaseLoadActivity {

    private ActivityAppointmentCheckBinding mBinding;

    private String mCode;
    private static final String USERCODE = "usercode";

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentCheckActivity.class);

        intent.putExtra(USERCODE, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_appointment_check, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("行程日历");

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra(USERCODE);
        }

        getNowDate();
        initListener();
    }

    private void initListener() {
        /**
         * 日历点击时显示行程dialog
         */
        mBinding.tripDate.setItemClickListener(new TripDateView.OnClickListener() {
            @Override
            public void OnItemClick(List<MouthAppointmentModel> dateModels, int position) {

                new TripTimeDialog(AppointmentCheckActivity.this, dateModels).show();
            }

            @Override
            public void OnPreviousClick(Date pDate) {
                getAppointmentByMouth(DateUtil.format(pDate, DateUtil.DATE_YM), true);
            }

            @Override
            public void OnNextClick(Date nDate) {
                getAppointmentByMouth(DateUtil.format(nDate, DateUtil.DATE_YM), true);
            }
        });
    }

    /**
     * 获取当前时间用于初始化日历
     */
    public void getNowDate() {

        Map<String, String> map = new HashMap<>();

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("805126", StringUtils.getJsonToString(map));

        showLoadingDialog();

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                Date nowDate = DateUtil.parse(data, DateUtil.DEFAULT_DATE_FMT);
                mBinding.tripDate.setmStartDate(nowDate);
                mBinding.tripDate.setDate(nowDate);

                try {
                    getAppointmentByMouth(data, false);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 根据月份获取预约行程
     *
     * @param mouth
     */
    public void getAppointmentByMouth(String mouth, boolean isShowDialog) {

        if (TextUtils.isEmpty(mouth) || TextUtils.isEmpty(mCode)) {
            return;
        }

        int year = Integer.parseInt(mouth.split("-")[0]);
        int monthOfYear = Integer.parseInt(mouth.split("-")[1]);

        Map<String, String> map = new HashMap<>();

        map.put("year", year+"");
        map.put("month", monthOfYear+"");
        map.put("userId", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getMouthAppointment("805509", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<MouthAppointmentModel>(this) {
            @Override
            protected void onSuccess(List<MouthAppointmentModel> data, String SucMessage) {


                try {
                    mBinding.tripDate.setCompareData(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
