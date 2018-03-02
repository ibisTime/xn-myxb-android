package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.FragmentInvitationFriendBinding;
import com.cdkj.myxb.models.InvitationModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/2/8.
 */

public class InvitationFriendFragment extends BaseLazyFragment {


    private FragmentInvitationFriendBinding mBinding;


    public static InvitationFriendFragment getInstanse() {
        InvitationFriendFragment fragment = new InvitationFriendFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation_friend, null, false);

        initListener();

        return mBinding.getRoot();
    }

    private void initListener() {

        mBinding.btnShareUrl.setOnClickListener(view -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            getShareUrl("SHARE_URL");
        });

    }

    @Override
    protected void lazyLoad() {
        if (mBinding != null) {
            getInvitationInfo();
            getActrule("ACT_RULE");
        }
    }

    @Override
    protected void onInvisible() {

    }


    //获取邀请信息
    public void getInvitationInfo() {

        if (!SPUtilHelpr.isLogin(mActivity, false)) {
            return;
        }

        Map map = new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getInvitationInfo("805703", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<InvitationModel>(mActivity) {
            @Override
            protected void onSuccess(InvitationModel data, String SucMessage) {
                mBinding.tvPeopleNum.setText(data.getTotalUser() + "");
                mBinding.tvAmount.setText(data.getTotalAmount() + "");
            }

            @Override
            protected void onFinish() {

            }
        });

    }


    /**
     * 获取活动规则
     *
     * @param key
     */
    public void getActrule(String key) {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("ckey", key);
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }

                mBinding.tvRule.setText(data.getCvalue());

            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 获取要分享的链接
     *
     * @param key
     */
    public void getShareUrl(String key) {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("ckey", key);
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
