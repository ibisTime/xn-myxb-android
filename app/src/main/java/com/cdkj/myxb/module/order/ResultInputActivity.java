package com.cdkj.myxb.module.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.SaleAmountAdapter;
import com.cdkj.myxb.databinding.ActivityResultInputBinding;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.event.EventInputCommentsSuc;
import com.cdkj.myxb.module.product.BrandListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_11;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_8;

/**
 * Created by cdkj on 2018/5/10.
 */

public class ResultInputActivity extends AbsBaseLoadActivity {

    private ActivityResultInputBinding mBinding;

    private AppointmentListModel model;

    private List<BrandListModel> saleList = new ArrayList<>();
    private SaleAmountAdapter mSaleAmountAdapter;

    private Calendar startCalendar;
    private Calendar endCalendar;

    /**
     * @param context
     */
    public static void open(Context context, AppointmentListModel model) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ResultInputActivity.class);
        intent.putExtra("model",model);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result_input, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        init();
        initListener();
        getListAdapter();
    }

    private void init() {
        mBaseBinding.titleView.setMidTitle("成果录入");

        if (getIntent() == null)
            return;

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        model = (AppointmentListModel) getIntent().getSerializableExtra("model");

        setView();
    }

    private void setView() {
        if (model.getUser() != null) {
            mBinding.tvName.setText(model.getUser().getRealName());
            ImgUtils.loadQiniuLogo(this, model.getUser().getPhoto(),mBinding.imgLogo);
        }

        if (model.getMryUser() != null) {
            mBinding.tvCName.setText(model.getMryUser().getRealName());
        }

        mBinding.tvOrderId.setText("订单编号：" + model.getCode());
        mBinding.tvState.setText(OrderHelper.getAppoitmentState(model.getStatus()));
        mBinding.tvTime.setText(DateUtil.formatStringData(model.getApplyDatetime(), DateUtil.DATE_YYMMddHHmm));
    }

    private void initListener() {
        mBinding.llDateTime.setOnClickListener(view -> getNowDate());

        mBinding.ivAdd.setOnClickListener(view -> {

            BrandListActivity.open(this);

        });

        mBinding.btnToComment.setOnClickListener(view -> {
            if (check())
                input();

        });
    }

    private boolean check(){
        if (TextUtils.equals(mBinding.tvDateTime.getText().toString(), "")){
            ToastUtil.show(this, "请选择时间");
            return false;
        }

        if (TextUtils.equals(mBinding.edtDays.getText().toString(), "")){
            ToastUtil.show(this, "请输入工作天数");
            return false;
        }

        if (TextUtils.equals(mBinding.edtClient.getText().toString(), "")){
            ToastUtil.show(this, "请输入见客户数");
            return false;
        }

        if (TextUtils.equals(mBinding.edtSuc.getText().toString(), "")){
            ToastUtil.show(this, "请输入成交客户数");
            return false;
        }

        if (saleList.size() == 0){
            ToastUtil.show(this, "请输入销售业绩");
            return false;
        }

        return true;
    }

    public class DetailList{
        private String amount;
        private String brandCode;
        private String remark;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBrandCode() {
            return brandCode;
        }

        public void setBrandCode(String brandCode) {
            this.brandCode = brandCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    private void input(){

        List<DetailList> detailList = new ArrayList<>();
        for (BrandListModel brandListModel :saleList){
            DetailList d = new DetailList();
            d.setAmount(brandListModel.getSaleAmount());
            d.setBrandCode(brandListModel.getCode());
            detailList.add(d);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("code", model.getCode());
        map.put("detailList", detailList);
        map.put("clientNumber", mBinding.edtClient.getText().toString());
        map.put("realDatetime", mBinding.tvDateTime.getTag().toString());
        map.put("realDays", mBinding.edtDays.getText().toString());
//        map.put("saleAmount", (int) (Double.parseDouble(mBinding.edtSale.getText().toString().trim()) * 1000));
        map.put("sucNumber", mBinding.edtSuc.getText().toString());
        map.put("updater", SPUtilHelpr.getUserId());

        String code;

        if (TextUtils.equals(model.getStatus(), APPOINTMENT_8) || TextUtils.equals(model.getStatus(), APPOINTMENT_11)){
            code = "805519";
        }else {
            code = "805514";
        }

        Call call = RetrofitUtils.getBaseAPiService().successRequest(code, StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()){

                    UITipDialog.showSuccess(ResultInputActivity.this, getString(R.string.do_success), dialogInterface -> {

                        EventBus.getDefault().post(new EventInputCommentsSuc());

                        finish();
                    });

                }else {
                    UITipDialog.showFall(ResultInputActivity.this, getString(R.string.do_fail));
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

//    @Override
//    public void positiveListener() {
//        int hour = mTimePickerDialog.getHour();
//        int minute = mTimePickerDialog.getMinute();
//
//        mBinding.tvDateTime.setText(mTimePickerDialog.getYear()
//                + "-"
//                + mTimePickerDialog.getMonth()
//                + "-"
//                + mTimePickerDialog.getDay()
//                + " "
//                + hour
//                + ":"
//                + minute);
//    }
//
//    @Override
//    public void negativeListener() {
//
//    }


    public void getListAdapter() {
        mSaleAmountAdapter = new SaleAmountAdapter(saleList);

        mSaleAmountAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BrandListModel brandListModel = mSaleAmountAdapter.getItem(position);
            brandListModel.setSaleAmount("");
            saleList.remove(brandListModel);
            mSaleAmountAdapter.notifyDataSetChanged();

            if (saleList.size() == 0)
                mBinding.rvSale.setVisibility(View.GONE);
        });

        mBinding.rvSale.setLayoutManager(getLinearLayoutManager());
        mBinding.rvSale.setAdapter(mSaleAmountAdapter);
    }

    @Subscribe
    public void addSale(BrandListModel brandListModel){
        mBinding.rvSale.setVisibility(View.VISIBLE);

        List<BrandListModel> l = new ArrayList<>();

        l.add(brandListModel);
        saleList.addAll(l);

        mSaleAmountAdapter.notifyDataSetChanged();

    }

    /**
     * 获取布局管理器
     *
     * @return
     */
    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {  //禁止自滚动
                return false;
            }
        };
    }

    /**
     * 显示日期picker
     */
    private void showDatePicker() {
        TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调

            mBinding.tvDateTime.setText(DateUtil.format(date, DateUtil.DATE_YYMMddHHmm));
            mBinding.tvDateTime.setTag(DateUtil.format(date, DateUtil.DEFAULT_DATE_FMT));

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
