package com.cdkj.myxb.module.shopper;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by cdkj on 2018/2/9.
 */

public class ShopperAppointmentActivity extends AbsBaseLoadActivity {

    private ActivityShopperAppointmentBinding mBinding;

    private String mShoppperCode;
    private static final String USERCODE = "usercode";

    private UserModel mUserModel;

    private CommentListAdapter mCommentListAdapter;


    /**
     * @param context
     * @param code    用户code
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopperAppointmentActivity.class);
        intent.putExtra(USERCODE, code);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shopper_appointment, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.shopper_appoint));


        if (getIntent() != null) {
            mShoppperCode = getIntent().getStringExtra(USERCODE);
        }

        initListener();

        initCommentAdapter();

        getUserInfoRequest(true);

        getNowDate();
    }

    /**
     *
     */
    private void initListener() {

        //预约
        mBinding.btnToApppintment.setOnClickListener(view -> {

            if (!SPUtilHelpr.isLogin(this, false)) {
                return;
            }
            ShopperAppointmentOrderActivity.open(this, mShoppperCode);
        });

        //评论点击
        mBinding.scoreLayout.linToComments.setOnClickListener(view -> ProductCommentListActivity.open(this, mShoppperCode, UserHelper.T));


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
                if (dateModels == null || dateModels.size() < position) return;

                MouthAppointmentModel mouthAppointmentModel = dateModels.get(position);

                if (mouthAppointmentModel == null || !mouthAppointmentModel.isSame()) return;

                new TripTimeDialog(ShopperAppointmentActivity.this, mouthAppointmentModel).show();
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
     */
    public void getUserInfoRequest(final boolean isShowdialog) {

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
        mBinding.headerLayout.tvSpecialty.setText("美导  专长：" + mUserModel.getStyle());
        mBinding.headerLayout.ratingbar.setStar(mUserModel.getLevel());
        mBinding.headerLayout.tvUserName.setText(mUserModel.getRealName());


        setTagLayout();

        if (TextUtils.isEmpty(mUserModel.getMobile())) {                    //有电话则可以进行电话拨打
            mBinding.callPhone.setVisibility(View.GONE);
        } else {
            mBinding.callPhone.setVisibility(View.VISIBLE);
            mBinding.callPhone.setOnClickListener(view -> {
                CallPhoneActivity.open(this, mUserModel.getMobile());
            });
        }

    }


    private void setTagLayout() {

        for (String searString : StringUtils.splitAsList(mUserModel.getStyle(), ",")) {
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
     * 获取评论总数和平均分
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
                    mBinding.scoreLayout.tvCount.setText("999+条评论");
                } else {
                    mBinding.scoreLayout.tvCount.setText(data.getTotalCount() + "条评论");
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
     * 获取评论列表
     */
    public void getCommentList() {

        if (TextUtils.isEmpty(mShoppperCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", "10");
        map.put("start", "1");
        map.put("status", "AB"); //审核通过
        map.put("type", UserHelper.T);
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
                Date mNowDate = DateUtil.parse(data, DateUtil.DEFAULT_DATE_FMT);
                mBinding.tripDate.setDate(mNowDate,true);
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

                List<MouthAppointmentModel> appointmentModels = new ArrayList<>();


                appointmentModels.addAll(data);

                //找出是相同一天的日期
                for (MouthAppointmentModel dataAppModel : data) {

                    List<MouthAppointmentModel> appointmentModels2 = new ArrayList<>();

                    for (MouthAppointmentModel appointmentModel : appointmentModels) {

                        if (DateUtil.inSameDay(new Date(dataAppModel.getStartDatetime()), new Date(appointmentModel.getStartDatetime()))) {

                            appointmentModels2.add(appointmentModel);

                        }

                    }
                    dataAppModel.setOneDayDateTime(appointmentModels2);
                }


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
