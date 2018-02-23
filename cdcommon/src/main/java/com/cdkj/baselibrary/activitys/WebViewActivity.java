package com.cdkj.baselibrary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.ActivityWebviewBinding;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * 介绍类webview
 */
public class WebViewActivity extends AbsBaseLoadActivity {

    private ActivityWebviewBinding mBinding;
    private WebView webView;

    private boolean isZoom;//webview是否支持缩放

    @Override
    protected void onDestroy() {
        if (null != webView) {
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    /**
     * 加载activity
     *
     * @param activity 上下文
     */
    public static void openkey(Activity activity, String title, String code) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("title", title);
        intent.putExtra("iszoom", false);
        activity.startActivity(intent);

    }

    /**
     * 加载activity
     *
     * @param activity 上下文
     */
    public static void openURL(Activity activity, String title, String url) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("iszoom", true);
        activity.startActivity(intent);

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            isZoom = getIntent().getBooleanExtra("iszoom", false);
        }
        initLayout();
        initData();
    }

    private void initLayout() {
        //输入法
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);

        WebSettings webSettings = webView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);//js
            webSettings.setDefaultTextEncodingName("UTF-8");

            if (isZoom) {
                webSettings.setSupportZoom(true);   //// 支持缩放
                webSettings.setBuiltInZoomControls(true);//// 支持缩放
                webSettings.setDomStorageEnabled(true);//开启DOM
                webSettings.setLoadWithOverviewMode(true);//// 缩放至屏幕的大小
                webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
                webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
            }
        }

        webView.setWebChromeClient(new MyWebViewClient1());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("lxj", error.toString() + "");
                handler.proceed(); // 接受网站证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mBinding.llAboutUs.addView(webView, 1);

    }

    private void initData() {
        if (getIntent() == null) {
            return;
        }

        mBaseBinding.titleView.setMidTitle(getIntent().getStringExtra("title"));

        if (TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            getKeyUrl(getIntent().getStringExtra("code"));
        } else {
            webView.loadUrl(getIntent().getStringExtra("url"));
        }
    }


    public void getKeyUrl(String key) {

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

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }
                webView.loadData(data.getCvalue(), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(WebViewActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    private class MyWebViewClient1 extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mBinding.pb.setProgress(newProgress);
            if (newProgress > 90) {
                mBinding.pb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        if (null != webView && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

}
