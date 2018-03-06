package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.FragmentMyBinding;
import com.cdkj.myxb.models.LogoUpdateSucc;
import com.cdkj.myxb.models.UpdateUserInfo;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.appointment.CommonAppointmentUserDetailActivity;
import com.cdkj.myxb.module.appointment.MyAppointmentActivity;
import com.cdkj.myxb.module.common.CallPhoneActivity;
import com.cdkj.myxb.module.integral.IntegralMallActivity;
import com.cdkj.myxb.module.order.MyOrderActivity;
import com.cdkj.myxb.module.order.ResultsOrderActivity;
import com.cdkj.myxb.module.user.ExpertRankListActivity;
import com.cdkj.myxb.module.user.MyCommentsAllActivity;
import com.cdkj.myxb.module.user.TripListActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.module.user.UserInfoUpdateActivity;
import com.cdkj.myxb.module.user.UserSettingActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
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


    public static MyFragment getInstanse() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, null, false);

        initListener();

        return mBinding.getRoot();
    }

    private void initListener() {

        mBinding.headerLayout.linUserInfo.setOnClickListener(view -> UserSettingActivity.open(mActivity));


        mBinding.layoutMy.rowMyOrder.setOnClickListener(view -> {
            MyOrderActivity.open(mActivity);
        });
        /*普通角色*/

        //成果订单
        mBinding.layoutMy.rowMyOrder.setOnClickListener(view -> {

            if (mUserInfoMode == null) return;
            ResultsOrderActivity.open(mActivity, mUserInfoMode.getKind());
        });
        //积分商场
        mBinding.layoutMy.rowIntegral.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));

        //用户资料
        mBinding.layoutMy.rowUserInfo.setOnClickListener(view -> {

            if (mUserInfoMode == null) return;

            UpdateUserInfo updateUserInfo = changeUserInfo();
            UserInfoUpdateActivity.open(mActivity, updateUserInfo);

        });

        //行程列表
        mBinding.layoutMy.rowTrip.setOnClickListener(view -> TripListActivity.open(mActivity));

        //我的排名
        mBinding.layoutMy.rowMyRank.setOnClickListener(view -> ExpertRankListActivity.open(mActivity));

        mBinding.layoutMy.rowHelpCenter.setOnClickListener(view -> WebViewActivity.openkey(mActivity, "帮助中心", "help_center"));



        /*美容院*/

        //预约
        mBinding.layoutMyBoos.rowMyAppointment.setOnClickListener(view -> {
            if (mUserInfoMode == null) return;
            MyAppointmentActivity.open(mActivity, mUserInfoMode.getKind());
        });
        //订单
        mBinding.layoutMyBoos.rowMyOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity));
        //积分商城
        mBinding.layoutMyBoos.rowIntegral.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));
        //我的评价
        mBinding.layoutMyBoos.rowMyComment.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));
        //我的评价
        mBinding.layoutMyBoos.rowMyComment.setOnClickListener(view -> MyCommentsAllActivity.open(mActivity));
        //团队顾问

        mBinding.layoutMyBoos.rowRowTeam.setOnClickListener(view -> {
            getUserInfoRequest(true, true);
        });

        mBinding.layoutMyBoos.rowHelpCenter.setOnClickListener(view -> WebViewActivity.openkey(mActivity, "帮助中心", "help_center"));
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
        SPUtilHelpr.saveUserLevel(data.getLevel() + "");
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
        ImgUtils.loadQiNiuBorderLogo(mActivity, mUserInfoMode.getPhoto(), mBinding.headerLayout.imgUserLogo, R.color.white_50);

        mBinding.headerLayout.tvUserName.setText(TextUtils.isEmpty(mUserInfoMode.getRealName()) ? "暂无" : mUserInfoMode.getRealName());

        if (TextUtils.isEmpty(mUserInfoMode.getSpeciality())) {
            mBinding.headerLayout.tvUserType.setText(getUserTypeByKind(mUserInfoMode.getKind()));
        } else {
            mBinding.headerLayout.tvUserType.setText(getUserTypeByKind(mUserInfoMode.getKind()) + " . " + mUserInfoMode.getSpeciality());
        }


        if (mUserInfoMode.isMan()) {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.man_2);
        } else {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.women_2);
        }

        setShowLayoutByType();


    }

    //根据用户类型显示相应布局
    private void setShowLayoutByType() {

        if (TextUtils.equals(mUserInfoMode.getKind(), UserHelper.C)) {          //美容院
            mBinding.layoutMyBoos.linBoos.setVisibility(View.VISIBLE);
            mBinding.layoutMy.linMy.setVisibility(View.GONE);
        } else {
            mBinding.layoutMyBoos.linBoos.setVisibility(View.GONE);
            mBinding.layoutMy.linMy.setVisibility(View.VISIBLE);

            if (TextUtils.equals(mUserInfoMode.getKind(), UserHelper.S)) {     //专家显示排名
                mBinding.layoutMy.rowMyRank.setVisibility(View.VISIBLE);
            } else {
                mBinding.layoutMy.rowMyRank.setVisibility(View.GONE);
            }

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
    protected void lazyLoad() {
        getUserInfoRequest(false, false);
    }

    @Override
    protected void onInvisible() {

    }
}
