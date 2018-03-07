package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.FragmentHelpCenter2Binding;
import com.cdkj.myxb.module.common.CallPhoneActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 帮助中心
 * Created by cdkj on 2018/2/8.
 */

public class HelpCenterFragment extends BaseLazyFragment {

    private FragmentHelpCenter2Binding mBinding;

    boolean isFirstRequest;

    public static HelpCenterFragment getInstanse() {
        HelpCenterFragment fragment = new HelpCenterFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_center_2, null, false);

        mBinding.tvTopRight.setOnClickListener(view -> getPhone("telephone"));
        mBinding.webView.setWebChromeClient(new MyWebViewLoadClient());
        mBinding.refreshLayout.setEnableLoadmore(false);
        mBinding.refreshLayout.setOnRefreshListener(refreshlayout -> {
            getHelpData();
        });

        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

        if (mBinding != null && !isFirstRequest) {
            getHelpData();
        }
    }

    @Override
    protected void onInvisible() {

    }


    public void getHelpData() {


        Map<String, String> map = new HashMap<>();
        map.put("ckey", "FAQ");
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
                isFirstRequest = true;
                mBinding.webView.loadData(data.getCvalue(), "text/html;charset=utf-8", "utf-8");
            }


            @Override
            protected void onFinish() {
                if (mBinding.refreshLayout.isRefreshing()) {
                    mBinding.refreshLayout.finishRefresh();
                }
            }
        });

    }


    public void getPhone(String key) {

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
                    UITipDialog.showInfo(mActivity, "暂无客服信息");
                    return;
                }
                CallPhoneActivity.open(mActivity, data.getCvalue());
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    public void onDestroy() {
        mBinding.webView.clearHistory();
        mBinding.webView.loadUrl("about:blank");
        mBinding.webView.stopLoading();
        mBinding.webView.setWebChromeClient(null);
        mBinding.webView.setWebViewClient(null);
        mBinding.webView.destroy();
        ((ViewGroup) mBinding.webView.getParent()).removeView(mBinding.webView);
        super.onDestroy();
    }

    private class MyWebViewLoadClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 90) {
                disMissLoading();
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
