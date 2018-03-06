package com.cdkj.myxb.module.appointment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommentListAdapter;
import com.cdkj.myxb.databinding.ActivityShopperAppointmentBinding;
import com.cdkj.myxb.models.CommentCountAndAverage;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.CallPhoneActivity;
import com.cdkj.myxb.module.product.ProductCommentListActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.weight.dialog.TripTimeDialog;
import com.cdkj.myxb.weight.views.TripDateView;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.cdkj.myxb.module.appointment.CommonAppointmentListActivity.INTENTTYPE;

/**
 * 预约用户详情
 * Created by cdkj on 2018/2/9.
 */

public class CommonAppointmentUserDetailActivity extends AbsBaseLoadActivity {

    private ActivityShopperAppointmentBinding mBinding;

    private String mShoppperCode;
    private static final String USERCODE = "usercode";
    private UserModel mUserModel;

    private CommentListAdapter mCommentListAdapter;
    private String mType; //预约类型

    /**
     * @param context
     * @param code    用户code
     */
    public static void open(Context context, String code, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CommonAppointmentUserDetailActivity.class);
        intent.putExtra(USERCODE, code);
        intent.putExtra(INTENTTYPE, type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shopper_appointment, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mShoppperCode = getIntent().getStringExtra(USERCODE);
            mType = getIntent().getStringExtra(INTENTTYPE);
        }
        mBaseBinding.titleView.setMidTitle(UserHelper.getAppointmentTypeByState(mType));

        initListener();

        initCommentAdapter();

        getUserInfoRequest(false, true);

        getNowDate();
    }

    /**
     *
     */
    private void initListener() {
        //拨打电话
        mBinding.callPhone.setOnClickListener(view -> {

            getUserInfoRequest(true, true);


        });


        //预约
        mBinding.btnToApppintment.setOnClickListener(view -> {

            if (!SPUtilHelpr.isLogin(this, false)) {
                return;
            }
            CommonAppointmentOrderActivity.open(this, mShoppperCode, mType);
        });

        //评价点击
        mBinding.scoreLayout.linToComments.setOnClickListener(view -> ProductCommentListActivity.open(this, mShoppperCode, mType));


        //行程点击
        ObjectAnimator animStart = ObjectAnimator.ofFloat(mBinding.imgTrip, "rotation", 0f, 90f);
        ObjectAnimator animEnd = ObjectAnimator.ofFloat(mBinding.imgTrip, "rotation", 90f, 0f);
        mBinding.llayoutTrip.setOnClickListener(view -> {
            if (mBinding.tripDate.getVisibility() == GONE) {
                animStart.start();
                mBinding.tripDate.setVisibility(VISIBLE);

            } else {
                animEnd.start();
                mBinding.tripDate.setVisibility(GONE);
            }

        });

        /**
         * 日历点击时显示行程dialog
         */
        mBinding.tripDate.setItemClickListener(new TripDateView.OnClickListener() {
            @Override
            public void OnItmeClick(List<MouthAppointmentModel> dateModels, int position) {

                new TripTimeDialog(CommonAppointmentUserDetailActivity.this, dateModels).show();
            }

            @Override
            public void OnPreviousClick(Date pDate) {
                getAppointmentByMouth(DateUtil.format(pDate, DateUtil.DATE_YMD), true);
            }

            @Override
            public void OnNextClick(Date nDate) {
                getAppointmentByMouth(DateUtil.format(nDate, DateUtil.DATE_YMD), true);
            }
        });

    }


    private void initCommentAdapter() {

        mCommentListAdapter = new CommentListAdapter(new ArrayList<>());

        mBinding.recyclerViewComments.setAdapter(mCommentListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mBinding.recyclerViewComments.setLayoutManager(linearLayoutManager);

    }


    /**
     * 获取用户信息
     *
     * @param isCallPhone  是否是为了拨打电话而请求
     * @param isShowdialog
     */
    public void getUserInfoRequest(boolean isCallPhone, boolean isShowdialog) {

        if (TextUtils.isEmpty(mShoppperCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("userId", mShoppperCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("805121", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowdialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserModel>(this) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {
                mUserModel = data;
                if (isCallPhone) {
                    if (TextUtils.isEmpty(mUserModel.getMobile())) {
                        UITipDialog.showInfo(CommonAppointmentUserDetailActivity.this, "暂无信息");
                        return;
                    }
                    CallPhoneActivity.open(CommonAppointmentUserDetailActivity.this, mUserModel.getMobile());
                    return;
                }
                setShowData();
                getCommentList();
                getCommentsCountAndAverage();
            }

            @Override
            protected void onFinish() {
                if (isShowdialog) disMissLoading();
            }
        });
    }

    /**
     * 设置显示数据
     */
    private void setShowData() {

        if (mUserModel == null) return;

        mBinding.webView.loadData(mUserModel.getIntroduce(), "text/html;charset=utf-8", "utf-8");

        ImgUtils.loadQiniuLogo(this, mUserModel.getPhoto(), mBinding.headerLayout.imgLogo);

        mBinding.headerLayout.tvUserName.setText(mUserModel.getRealName());

        mBinding.headerLayout.ratingbar.setStar(mUserModel.getLevel());
        mBinding.headerLayout.tvUserName.setText(mUserModel.getRealName());
        if (TextUtils.isEmpty(mUserModel.getSpeciality())) {
            mBinding.headerLayout.tvSpecialty.setVisibility(GONE);
        } else {
            mBinding.headerLayout.tvSpecialty.setVisibility(VISIBLE);
            mBinding.headerLayout.tvSpecialty.setText(UserHelper.getUserTypeByKind(mType) + "  专长：" + mUserModel.getSpeciality());
        }


        setTagLayout();

        if (TextUtils.isEmpty(mUserModel.getMobile())) {                    //有电话则可以进行电话拨打
            mBinding.callPhone.setVisibility(View.GONE);
        } else {
            mBinding.callPhone.setVisibility(View.VISIBLE);
        }

    }


    private void setTagLayout() {

        for (String searString : StringUtils.splitAsList(mUserModel.getStyleName(), ",")) {
            if (TextUtils.isEmpty(searString)) return;
            mBinding.headerLayout.flexboxLayout.addView(createNewFlexItemTextView(searString));
        }
    }

    /**
     * 动态创建TextView
     *
     * @param
     * @return
     */
    private TextView createNewFlexItemTextView(String str) {
        final TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(str);
        textView.setTextSize(12);
        textView.setTextColor(ContextCompat.getColor(this, R.color.shopper_lable_1));
        textView.setBackgroundResource(R.drawable.bg_shopper_lable);
        textView.setTag(str);

        int padding = DisplayHelper.dip2px(this, 3);
        int paddingLeftAndRight = DisplayHelper.dip2px(this, 12);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DisplayHelper.dip2px(this, 5);
        int marginTop = DisplayHelper.dip2px(this, 5);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }


    /**
     * 获取评价总数和平均分
     */
    public void getCommentsCountAndAverage() {
        if (TextUtils.isEmpty(mShoppperCode)) return;

        Map<String, String> map = new HashMap<>();

        map.put("entityCode", mShoppperCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentCountAndAverage("805423", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<CommentCountAndAverage>(this) {
            @Override
            protected void onSuccess(CommentCountAndAverage data, String SucMessage) {
                if (data.getTotalCount() > 1000) {
                    mBinding.scoreLayout.tvCount.setText("999+条评价");
                } else {
                    mBinding.scoreLayout.tvCount.setText(data.getTotalCount() + "条评价");
                }
                mBinding.scoreLayout.tvStarNum.setText(data.getAverage() + "星");
                mBinding.scoreLayout.ratingbar.setStar(data.getAverage());

            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    /**
     * 获取评价列表
     */
    public void getCommentList() {

        if (TextUtils.isEmpty(mShoppperCode) || TextUtils.isEmpty(mType)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", "10");
        map.put("start", "1");
        map.put("status", "AB"); //审核通过
        map.put("type", mType);
        map.put("entityCode", mShoppperCode);


        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentList("805425", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CommentListMode>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<CommentListMode> data, String SucMessage) {
                mCommentListAdapter.replaceData(data.getList());
            }

            @Override
            protected void onFinish() {

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
                getAppointmentByMouth(data, false);
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

        if (TextUtils.isEmpty(mouth) || TextUtils.isEmpty(mShoppperCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("startMonth", mouth);
        map.put("userId", mShoppperCode);


        Call call = RetrofitUtils.createApi(MyApiServer.class).getMouthAppointment("805508", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<MouthAppointmentModel>(this) {
            @Override
            protected void onSuccess(List<MouthAppointmentModel> data, String SucMessage) {
//                data.addAll(data);
//                MouthAppointmentModel model = new MouthAppointmentModel();
//                model.setStartDatetime("Mar 1, 2018 12:00:00 AM");
//                model.setEndDatetime("Mar 18, 2018 12:00:00 AM");
//                data.add(model);
//                MouthAppointmentModel mo2 = new MouthAppointmentModel();
//                mo2.setStartDatetime("Mar 2, 2018 12:00:00 AM");
//                mo2.setEndDatetime("Mar 19, 2018 12:00:00 AM");
//                data.add(mo2);
//                MouthAppointmentModel mo3 = new MouthAppointmentModel();
//                mo3.setStartDatetime("Mar 3, 2018 12:00:00 AM");
//                mo3.setEndDatetime("Mar 10, 2018 12:00:00 AM");
//                data.add(mo3);
                mBinding.tripDate.setCompareData(data);
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    protected void onDestroy() {
        mBinding.webView.clearHistory();
        ((ViewGroup) mBinding.webView.getParent()).removeView(mBinding.webView);
        mBinding.webView.loadUrl("about:blank");
        mBinding.webView.stopLoading();
        mBinding.webView.setWebChromeClient(null);
        mBinding.webView.setWebViewClient(null);
        mBinding.webView.destroy();
        mBinding.tripDate.destroyView();
        super.onDestroy();

    }


}
