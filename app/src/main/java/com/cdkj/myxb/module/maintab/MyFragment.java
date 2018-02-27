package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.appointment.MyAppointmentActivity;
import com.cdkj.myxb.module.integral.IntegralMallActivity;
import com.cdkj.myxb.module.order.MyOrderActivity;
import com.cdkj.myxb.module.order.ResultsOrderActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.module.user.UserSettingActivity;

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
        mBinding.layoutMy.rowMyOrder.setOnClickListener(view -> ResultsOrderActivity.open(mActivity) );
        /*积分商场*/
        mBinding.layoutMy.rowIntegral.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));


        /*美容院*/

        //预约
        mBinding.layoutMyBoos.rowMyAppointment.setOnClickListener(view -> MyAppointmentActivity.open(mActivity));
        //订单
        mBinding.layoutMyBoos.rowMyOrder.setOnClickListener(view -> MyOrderActivity.open(mActivity));
        //积分商城
        mBinding.layoutMyBoos.rowIntegral.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));
        //我的评论
        mBinding.layoutMyBoos.rowMyComment.setOnClickListener(view -> IntegralMallActivity.open(mActivity, mUserInfoMode));
    }


    /**
     * 获取用户信息
     */
    public void getUserInfoRequest(final boolean isShowdialog) {

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
                SPUtilHelpr.saveisTradepwdFlag(data.isTradepwdFlag());
                SPUtilHelpr.saveUserPhoneNum(data.getMobile());
                SPUtilHelpr.saveUserName(data.getRealName());
                SPUtilHelpr.saveUserNickName(data.getNickname());
                SPUtilHelpr.saveUserPhoto(data.getPhoto());
                setShowData();
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(mActivity, errorMessage);
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
        if (isUserInfoIsNull()) {
            return;
        }

        ImgUtils.loadQiniuLogo(this, mUserInfoMode.getPhoto(), mBinding.headerLayout.imgUserLogo);

        mBinding.headerLayout.tvUserName.setText(mUserInfoMode.getRealName());

        mBinding.headerLayout.tvUserType.setText(getUserTypeByKind(mUserInfoMode.getKind()) + " . " + mUserInfoMode.getSpeciality());

        if (mUserInfoMode.isMan()) {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.man_2);
        } else {
            mBinding.headerLayout.imgGender.setImageResource(R.drawable.women_2);
        }

        //根据用户类型显示相应布局
        if (TextUtils.equals(mUserInfoMode.getKind(), UserHelper.C)) {
            mBinding.layoutMyBoos.linBoos.setVisibility(View.VISIBLE);
            mBinding.layoutMy.linMy.setVisibility(View.GONE);
        } else {
            mBinding.layoutMyBoos.linBoos.setVisibility(View.GONE);
            mBinding.layoutMy.linMy.setVisibility(View.VISIBLE);
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
        getUserInfoRequest(false);
    }

    @Override
    protected void onInvisible() {

    }
}
