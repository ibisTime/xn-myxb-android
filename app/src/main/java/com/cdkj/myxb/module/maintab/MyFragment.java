package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.FragmentMyBinding;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.ListCountAppointmentModel;
import com.cdkj.myxb.models.ListCountOrderModel;
import com.cdkj.myxb.models.LogoUpdateSucc;
import com.cdkj.myxb.models.UpdateUserInfo;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.models.event.EventChangeCountModel;
import com.cdkj.myxb.models.event.EventDoChangeCountModel;
import com.cdkj.myxb.models.event.EventOpenAppointmentModel;
import com.cdkj.myxb.models.event.EventOpenOrderModel;
import com.cdkj.myxb.models.event.EventShowMainTab;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.appointment.MyAppointmentActivity;
import com.cdkj.myxb.module.common.CallPhoneActivity;
import com.cdkj.myxb.module.integral.IntegraListActivity;
import com.cdkj.myxb.module.order.MyOrderActivity;
import com.cdkj.myxb.module.order.ResultActivity;
import com.cdkj.myxb.module.user.ExpertRankListActivity;
import com.cdkj.myxb.module.user.GroupTripListActivity;
import com.cdkj.myxb.module.user.MyCommentActivity;
import com.cdkj.myxb.module.user.SignActivity;
import com.cdkj.myxb.module.user.UserAtlasActivity;
import com.cdkj.myxb.module.user.UserAtlasTypeCActivity;
import com.cdkj.myxb.module.user.UserAtlasTypeTActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.module.user.UserInfoUpdateActivity;
import com.cdkj.myxb.module.user.UserSettingActivity;
import com.cdkj.myxb.module.user.account.AccountActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.user.UserHelper.getUserTypeByKind;

/**
 * 我的
 * Created by cdkj on 2018/2/7.
 */

public class MyFragment extends BaseLazyFragment {

    private FragmentMyBinding mBinding;

    private UserModel mUserInfoMode;

    private boolean isSign;

    public static MyFragment getInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    // ListCount
    private int toPayCount = 0;
    private int toReceiceCount = 0;

    private int fwInputCount = 0;
    private int fwToBookCount = 0;
    private int fwToClassCount = 0;
    private int fwClassEndCount = 0;
    private int jxsToApproveCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, null, false);

        initListener();

        return mBinding.getRoot();
    }

    private void initListener() {

        mBinding.headerLayout.linUserInfo.setOnClickListener(view -> UserSettingActivity.open(mActivity));
        mBinding.headerLayout.llCoin.setOnClickListener(view -> {
            AccountActivity.open(mActivity, mBinding.headerLayout.tvCoin.getTag().toString(), mBinding.headerLayout.tvCoin.getText().toString());
        });

        mBinding.headerLayout.llTicket.setOnClickListener(view -> {
            IntegraListActivity.open(mActivity, mBinding.headerLayout.tvTicket.getTag().toString(), mBinding.headerLayout.tvTicket.getText().toString());
        });

        /** UserType S : 专家 **/
        mBinding.layoutUserS.rowTrip.setOnClickListener(view -> {
            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过

                EventChangeCountModel model = new EventChangeCountModel();
                model.setToPayCount(toPayCount);
                model.setToReceiceCount(toReceiceCount);
                model.setFwInputCount(fwInputCount);
                model.setFwToBookCount(fwToBookCount);
                model.setFwToClassCount(fwToClassCount);
                model.setFwClassEndCount(fwClassEndCount);
                model.setJxsToApproveCount(jxsToApproveCount);

                MyAppointmentActivity.open(mActivity, mUserInfoMode.getKind(), model);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }


        });
        mBinding.layoutUserS.rowResult.setOnClickListener(view -> {
            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ResultActivity.open(mActivity, mUserInfoMode.getKind());
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }

        });
        mBinding.layoutUserS.rowComment.setOnClickListener(view -> MyCommentActivity.open(mActivity));
        mBinding.layoutUserS.rowRank.setOnClickListener(view -> {
            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ExpertRankListActivity.open(mActivity);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }


        });
        mBinding.layoutUserS.rowOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity, toPayCount, toReceiceCount));
        mBinding.layoutUserS.rowInfo.setOnClickListener(view -> {
            if (mUserInfoMode == null) return;

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                UpdateUserInfo updateUserInfo = changeUserInfo();
                UserInfoUpdateActivity.open(mActivity, updateUserInfo);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }

        });

        /** UserType B : 合伙人 **/
        mBinding.layoutUserB.rowJoin.setOnClickListener(view -> UserAtlasActivity.open(mActivity));
        mBinding.layoutUserB.rowTrip.setOnClickListener(view -> GroupTripListActivity.open(mActivity));
        mBinding.layoutUserB.rowOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity, toPayCount, toReceiceCount));

        /** UserType T : 服务团队 **/
        mBinding.layoutUserT.rowTrip.setOnClickListener(view -> {
            if (mUserInfoMode == null) return;

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                EventChangeCountModel model = new EventChangeCountModel();
                model.setToPayCount(toPayCount);
                model.setToReceiceCount(toReceiceCount);
                model.setFwInputCount(fwInputCount);
                model.setFwToBookCount(fwToBookCount);
                model.setFwToClassCount(fwToClassCount);
                model.setFwClassEndCount(fwClassEndCount);
                model.setJxsToApproveCount(jxsToApproveCount);

                MyAppointmentActivity.open(mActivity, mUserInfoMode.getKind(),model);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }


        });
        mBinding.layoutUserT.rowGroupTrip.setOnClickListener(view -> {
            GroupTripListActivity.open(mActivity);
        });
        mBinding.layoutUserT.rowJoin.setOnClickListener(view -> {

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                UserAtlasTypeTActivity.open(mActivity);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                SignActivity.open(mActivity, "FWS_CONTRACT");
            }
        });
        mBinding.layoutUserT.rowComment.setOnClickListener(view -> {

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                MyCommentActivity.open(mActivity);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }
        });
        mBinding.layoutUserT.rowOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity, toPayCount, toReceiceCount));
        mBinding.layoutUserT.rowInfo.setOnClickListener(view -> {
            if (mUserInfoMode == null) return;

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                UpdateUserInfo updateUserInfo = changeUserInfo();
                UserInfoUpdateActivity.open(mActivity, updateUserInfo);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }
        });

        /** UserType C : 经销商 **/
        mBinding.layoutUserC.rowJoin.setOnClickListener(view -> {
            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                UserAtlasTypeCActivity.open(mActivity, mUserInfoMode.getMaxNumber()+"");
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                SignActivity.open(mActivity, "JXS_CONTRACT");
            }
        });
        mBinding.layoutUserC.rowOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity, toPayCount, toReceiceCount));
        mBinding.layoutUserC.rowAppointment.setOnClickListener(view -> {
            if (mUserInfoMode == null) return;

            if (TextUtils.equals("2",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\

                EventChangeCountModel model = new EventChangeCountModel();
                model.setToPayCount(toPayCount);
                model.setToReceiceCount(toReceiceCount);
                model.setFwInputCount(fwInputCount);
                model.setFwToBookCount(fwToBookCount);
                model.setFwToClassCount(fwToClassCount);
                model.setFwClassEndCount(fwClassEndCount);
                model.setJxsToApproveCount(jxsToApproveCount);

                MyAppointmentActivity.open(mActivity, mUserInfoMode.getKind(), model);
            }else if (TextUtils.equals("1",mUserInfoMode.getSignStatus())) { // 0=待签约/1=已签约待审/2=已签约审核通过\
                ToastUtil.show(mActivity, "您的签约正在审核");
            } else {
                ToastUtil.show(mActivity, "您还未签约");
            }


        });

    }

    /**
     * 把用户信息转换为更新信息类
     * @return
     */
    @NonNull
    private UpdateUserInfo changeUserInfo() {
        UpdateUserInfo updateUserInfo = new UpdateUserInfo();
        updateUserInfo.setRealName(mUserInfoMode.getRealName());
        updateUserInfo.setSlogan(mUserInfoMode.getSlogan());
        updateUserInfo.setSpeciality(mUserInfoMode.getSpeciality());
        updateUserInfo.setStyle(mUserInfoMode.getStyle());
        updateUserInfo.setDescription(mUserInfoMode.getIntroduce());
        return updateUserInfo;
    }

    private void callPhone() {
        if (mUserInfoMode == null || mUserInfoMode.getAdviserUser() == null) return;

        if (TextUtils.isEmpty(mUserInfoMode.getAdviserUser().getMobile())) {
            UITipDialog.showInfo(mActivity, "暂无顾问信息");
            return;
        }

        CallPhoneActivity.open(mActivity, mUserInfoMode.getAdviserUser().getMobile());
    }


    /**
     * 获取用户信息
     */
    public void getUserInfoRequest(boolean isCallPhone, final boolean isShowdialog) {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("805121", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowdialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserModel>(mActivity) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {
                mUserInfoMode = data;
                if (isCallPhone) {
                    callPhone();
                    return;
                }

                if (TextUtils.equals("2",mUserInfoMode.getSignStatus())){ // 0=待签约/1=已签约待审/2=已签约审核通过
                    isSign = true;
                }else {
                    isSign = false;
                }

                saveUserInfo(data);
                setShowData();
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                mBinding.headerLayout.linUserInfo.setVisibility(View.VISIBLE);
                UITipDialog.showFall(mActivity, errorMessage);
            }

            @Override
            protected void onFinish() {
                if (isShowdialog) disMissLoading();
            }
        });
    }

    /**
     * 保存用户相关信息
     *
     * @param data
     */
    private void saveUserInfo(UserModel data) {
        SPUtilHelpr.saveisTradepwdFlag(data.isTradepwdFlag());
        SPUtilHelpr.saveUserPhoneNum(data.getMobile());
        SPUtilHelpr.saveUserName(data.getRealName());
        SPUtilHelpr.saveUserNickName(data.getNickname());
        SPUtilHelpr.saveUserPhoto(data.getPhoto());
        SPUtilHelpr.saveUserLevel(data.getLevel());
        SPUtilHelpr.saveUserType(data.getKind());
    }

    /**
     * 设置显示数据
     */
    private void setShowData() {
        if (isUserInfoIsNull()) {
            return;
        }
        mBinding.headerLayout.linUserInfo.setVisibility(View.VISIBLE);
        ImgUtils.loadQiNiuBorderLogo(mActivity, mUserInfoMode.getPhoto(), mBinding.headerLayout.imgUserLogo, R.color.white);
        mBinding.headerLayout.tvUserName.setText(TextUtils.isEmpty(mUserInfoMode.getRealName()) ? "暂无" : mUserInfoMode.getRealName());

        mBinding.headerLayout.tvLevel.setText(UserHelper.getLevelWithUserKind(mUserInfoMode.getKind(), mUserInfoMode.getLevel()));
        mBinding.headerLayout.ivLevel.setBackgroundResource(UserHelper.getLevelIconWithUserKind(mUserInfoMode.getKind(), mUserInfoMode.getLevel()));


        if (isSign){
            if (TextUtils.isEmpty(mUserInfoMode.getSpeciality())) {
                mBinding.headerLayout.tvUserType.setText(getUserTypeByKind(mUserInfoMode.getKind()));
            } else {
                mBinding.headerLayout.tvUserType.setText(getUserTypeByKind(mUserInfoMode.getKind()) + " . " + mUserInfoMode.getSpeciality());
            }

            mBinding.layoutUserT.rowJoin.setTvLeft("网络图谱");
            mBinding.layoutUserC.rowJoin.setTvLeft("网络图谱");
        } else {
            mBinding.headerLayout.tvUserType.setText("游客");

            mBinding.layoutUserT.rowJoin.setTvLeft("我要成为服务团队");
            mBinding.layoutUserC.rowJoin.setTvLeft("我要成为经销商");
        }


        if (mUserInfoMode.isMan()) {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.man_2);
        } else {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.women_2);
        }

        setShowLayoutByType();

        showMainTabStatus();
    }

    private void showMainTabStatus() {
        EventShowMainTab eventShowMainTab = new EventShowMainTab();
        EventBus.getDefault().post(eventShowMainTab);
    }

    //根据用户类型显示相应布局
    private void setShowLayoutByType() {

        switch (mUserInfoMode.getKind()){
            case UserHelper.C:
                mBinding.layoutUserC.llRoot.setVisibility(View.VISIBLE);
                break;

            case UserHelper.S:
                mBinding.layoutUserS.llRoot.setVisibility(View.VISIBLE);
                break;

            case UserHelper.T:
                mBinding.layoutUserT.llRoot.setVisibility(View.VISIBLE);
                break;

            case UserHelper.B:
                mBinding.layoutUserB.llRoot.setVisibility(View.VISIBLE);
                break;

        }
    }

    //更新头像
    @Subscribe
    public void updateLogo(LogoUpdateSucc logoUpdateSucc) {
        if (logoUpdateSucc == null) return;
        ImgUtils.loadQiniuLogo(this, logoUpdateSucc.getUrl(), mBinding.headerLayout.imgUserLogo);
        if (mUserInfoMode != null) {
            mUserInfoMode.setPhoto(logoUpdateSucc.getUrl());
        }

    }

    /**
     * 用户数据是否为空
     *
     * @return 为空返回true
     */
    private boolean isUserInfoIsNull() {
        return mUserInfoMode == null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            getUserInfoRequest(false, false);
            getUserAccount(true);
        }

        if (!TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.B))
            getAppointmentListCount(true);

        getOrderListCount(true);

    }

    @Override
    protected void lazyLoad() {
        getUserInfoRequest(false, false);
        getUserAccount(true);
    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取用户信息
     */
    public void getUserAccount(boolean isShowDialog) {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("currency", "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAccountList("802503", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AccountListModel>(mActivity) {

            @Override
            protected void onSuccess(List<AccountListModel> data, String SucMessage) {

                if (data == null || data.size() == 0)
                    return;

                for (AccountListModel model : data){
                    if (model.getCurrency().equals("JF")){
                        mBinding.headerLayout.tvTicket.setText(MoneyUtils.showPrice(model.getAmount()));
                        mBinding.headerLayout.tvTicket.setTag(model.getAccountNumber());
                    }else {
                        mBinding.headerLayout.tvCoin.setText(MoneyUtils.showPrice(model.getAmount()));
                        mBinding.headerLayout.tvCoin.setTag(model.getAccountNumber());
                    }

                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getOrderListCount(boolean isShowDialog) {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, Object> map = RetrofitUtils.getRequestMap();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getListCountOrder("805527", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ListCountOrderModel>(mActivity) {

            @Override
            protected void onSuccess(ListCountOrderModel data, String SucMessage) {

                toPayCount = data.getToPayCount();
                toReceiceCount = data.getToReceiceCount();

                int count = toPayCount + toReceiceCount;

                if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
                    mBinding.layoutUserC.rowOrderNum.setText(count+"");
                    mBinding.layoutUserC.rowOrderNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }else if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.S)){
                    mBinding.layoutUserS.rowOrderNum.setText(count+"");
                    mBinding.layoutUserS.rowOrderNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }else if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.T)) {
                    mBinding.layoutUserT.rowOrderNum.setText(count+"");
                    mBinding.layoutUserT.rowOrderNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }else {
                    mBinding.layoutUserB.rowOrderNum.setText(count+"");
                    mBinding.layoutUserB.rowOrderNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }

                doChangeCount();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getAppointmentListCount(boolean isShowDialog) {

        if (!SPUtilHelpr.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, Object> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getListCountAppointment("805528", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ListCountAppointmentModel>(mActivity) {

            @Override
            protected void onSuccess(ListCountAppointmentModel data, String SucMessage) {

                fwInputCount = data.getFwInputCount();
                fwClassEndCount = data.getFwClassEndCount();
                fwToBookCount = data.getFwToBookCount();
                fwToClassCount = data.getFwToClassCount();
                jxsToApproveCount = data.getJxsToApproveCount();

                int count = fwInputCount + fwClassEndCount + fwToBookCount + fwToClassCount + jxsToApproveCount;

                if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.C)){
                    mBinding.layoutUserC.rowAppointmentNum.setText(count+"");
                    mBinding.layoutUserC.rowAppointmentNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }else if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.S)){
                    mBinding.layoutUserS.rowTripNum.setText(count+"");
                    mBinding.layoutUserS.rowTripNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }else if(TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.T)) {
                    mBinding.layoutUserT.rowTripNum.setText(count+"");
                    mBinding.layoutUserT.rowTripNum.setVisibility(count== 0 ? View.GONE : View.VISIBLE) ;
                }

                doChangeCount();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void doChangeCount(){
        EventChangeCountModel model = new EventChangeCountModel();
        model.setToPayCount(toPayCount);
        model.setToReceiceCount(toReceiceCount);
        model.setFwInputCount(fwInputCount);
        model.setFwToBookCount(fwToBookCount);
        model.setFwToClassCount(fwToClassCount);
        model.setFwClassEndCount(fwClassEndCount);
        model.setJxsToApproveCount(jxsToApproveCount);

        EventBus.getDefault().post(model);
    }

    @Subscribe
    public void doChangeCount(EventDoChangeCountModel model){
        if (model == null)
            return;

        if (model.isOrder()){
            getOrderListCount(true);
        }else {
            if (!TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.B))
                getAppointmentListCount(true);
        }

    }

    @Subscribe
    public void doOpenOrder(EventOpenOrderModel model){
        if (model == null)
            return;

        MyOrderActivity.open(mActivity, toPayCount, toReceiceCount);
    }

    @Subscribe
    public void doOpenOrder(EventOpenAppointmentModel openAppointmentModel){
        if (openAppointmentModel == null)
            return;

        EventChangeCountModel model = new EventChangeCountModel();
        model.setToPayCount(toPayCount);
        model.setToReceiceCount(toReceiceCount);
        model.setFwInputCount(fwInputCount);
        model.setFwToBookCount(fwToBookCount);
        model.setFwToClassCount(fwToClassCount);
        model.setFwClassEndCount(fwClassEndCount);
        model.setJxsToApproveCount(jxsToApproveCount);

        MyAppointmentActivity.open(mActivity, mUserInfoMode.getKind(), model);
    }
}
