package com.cdkj.myxb.module.integral;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.IntegraProductDetailsModel;
import com.cdkj.myxb.databinding.ActivityIntegralProductDetailsBinding;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.weight.GlideImageLoader;
import com.cdkj.myxb.weight.dialog.IntegralChangeDialog;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 积分产品详情
 * Created by 李先俊 on 2018/2/23.
 */

public class IntegralProductDetailsActivity extends AbsBaseLoadActivity {


    private ActivityIntegralProductDetailsBinding mBinding;

    public static final String PRODUCTCODE = "productCode";

    private String mProductCode;//产品编号

    private List<String> mbannerUrlList;

    private WebView webView;//用于展示富文本


    /**
     * @param context
     * @param productCode 产品编号
     */
    public static void open(Context context, String productCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IntegralProductDetailsActivity.class);
        intent.putExtra(PRODUCTCODE, productCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_integral_product_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("产品详情");

        if (getIntent() != null) {
            mProductCode = getIntent().getStringExtra(PRODUCTCODE);
        }

        initlistener();

        initBanner();

        initWebView();

        getProductRequest();
    }

    private void initlistener() {
        mBinding.btnWantChange.setOnClickListener(view -> {
            new IntegralChangeDialog(this,mProductCode).show();
        });

    }

    private void initWebView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);

        WebSettings webSettings = webView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);//js
            webSettings.setDefaultTextEncodingName("UTF-8");
//            webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
            webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
//            webSettings.setSupportZoom(true);   //// 支持缩放
//            webSettings.setBuiltInZoomControls(true);//// 支持缩放
//            webSettings.setDomStorageEnabled(true);//开启DOM
        }

        mBinding.linWebView.addView(webView);

    }


    private void initBanner() {

        mbannerUrlList = new ArrayList<>();

        mBinding.bannerIntegral.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.bannerIntegral.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.bannerIntegral.setImageLoader(new GlideImageLoader());


    }


    /**
     * 获取产品请求
     */
    public void getProductRequest() {

        if (TextUtils.isEmpty(mProductCode)) return;

        Map<String, String> map = new HashMap<>();

        map.put("code", mProductCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getIntegralProduct("805286", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntegraProductDetailsModel>(this) {
            @Override
            protected void onSuccess(IntegraProductDetailsModel data, String SucMessage) {
                setBannerData(data.getPic());
                setPageData(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 设置页面数据
     *
     * @param data
     */
    private void setPageData(IntegraProductDetailsModel data) {

        webView.loadData(data.getDescription(), "text/html;charset=utf-8", "utf-8");

        mBinding.tvIntegralName.setText(data.getName());
        mBinding.tvIntegralSlogan.setText(data.getSlogan());
        mBinding.tvIntegralPrice.setText(MoneyUtils.showPrice(data.getPrice()));
        mBinding.tvIntegralQuantity.setText(data.getQuantity() + "");
    }

    /**
     * 设置轮播图数据
     *
     * @param urls
     */
    private void setBannerData(String urls) {
        mbannerUrlList = StringUtils.splitAsPicList(urls);
        mBinding.bannerIntegral.setImages(mbannerUrlList);
        mBinding.bannerIntegral.start();
    }

    @Override
    protected void onDestroy() {
        mBinding.bannerIntegral.stopAutoPlay();
        super.onDestroy();

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

    }


}
