package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommentListAdapter;
import com.cdkj.myxb.databinding.ActivityProductDetailsBinding;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.models.CommentCountAndAverage;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.CallPhoneActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.weight.GlideImageLoader;
import com.cdkj.myxb.weight.views.MyScrollView;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 产品详情
 * Created by cdkj on 2018/2/10.
 */

public class ProductDetailsActivity extends AbsBaseLoadActivity {

    private static final String PRODUCTCODE = "productcode";
    private ActivityProductDetailsBinding mBinding;
    private String mProductCode;//产品编号
    private List<String> mbannerUrlList;

    private CommentListAdapter mCommentListAdapter;

    /**
     * @param context
     * @param productCode 产品编号
     */
    public static void open(Context context, String productCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(PRODUCTCODE, productCode);
        context.startActivity(intent);
    }


    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_product_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mProductCode = getIntent().getStringExtra(PRODUCTCODE);
        }

        initTopTitleAlphaChange();

        initListener();
        initBanner();
        initCommentAdapter();
        getProductDetailsRequest();
        getCommentsCountAndAverage();
        getCommentList();
    }


    private void initBanner() {

        mbannerUrlList = new ArrayList<>();

        mBinding.bannerProduct.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.bannerProduct.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.bannerProduct.setImageLoader(new GlideImageLoader());


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
     * 设置轮播图数据
     *
     * @param urls
     */
    private void setBannerData(String urls) {
        mbannerUrlList = StringUtils.splitAsPicList(urls);
        mBinding.bannerProduct.setImages(mbannerUrlList);
        mBinding.bannerProduct.start();
    }


    private void initListener() {
        mBinding.fraLayoutTitleLeft.setOnClickListener(view -> finish());
        mBinding.fraLayoutTitleLeftImg.setOnClickListener(view -> finish());
        mBinding.btnToOrder.setOnClickListener(view -> {
            if (!SPUtilHelpr.isLogin(this, false)) {
                return;
            }
            ProductOrderActivity.open(this, mProductCode);
        });

        mBinding.scoreLayout.linToComments.setOnClickListener(view -> ProductCommentListActivity.open(this, mProductCode, "P"));

    }

    /**
     * 顶部title 透明度渐变
     */
    private void initTopTitleAlphaChange() {

        mBinding.fraLayoutTitle.setAlpha(0);
        mBinding.fraLayoutTitleLeftImg.setAlpha(1);

        mBinding.scrollViewProduct.setOnScrollListener(new MyScrollView.MyOnScrollListener() {
            @Override
            public void onScroll(int y) {
            }

            @Override
            public void onCurrentY(int y) {
                //设置默认透明度
                float titleLayoutAlpha = 0;
                float titleImageAlpha = 0;
                //渐变滑动距离 banner高度 - title高度
                int baseHeight = getResources().getDimensionPixelOffset(R.dimen.product_details_banner_height) - getResources().getDimensionPixelOffset(R.dimen.title_height);
                if (y <= 0) {   //到顶部时
                    titleLayoutAlpha = 0;
                    titleImageAlpha = 1;
                } else {
                    titleLayoutAlpha = y / (baseHeight * 1.0f);
                    titleImageAlpha = 1 - titleLayoutAlpha;
                }
                mBinding.fraLayoutTitle.setAlpha(titleLayoutAlpha);
                mBinding.fraLayoutTitleLeftImg.setAlpha(titleImageAlpha);
            }
        });
    }

    /**
     * 获取产品数据
     */
    public void getProductDetailsRequest() {

        if (TextUtils.isEmpty(mProductCode)) return;

        Map<String, String> map = new HashMap<>();

        map.put("code", mProductCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandProductDetails("805267", StringUtils.getJsonToString(map));

        addCall(call);


        call.enqueue(new BaseResponseModelCallBack<BrandProductModel>(this) {
            @Override
            protected void onSuccess(BrandProductModel data, String SucMessage) {
                setBannerData(data.getAdvPic());
                setShowData(data);
            }

            @Override
            protected void onFinish() {
            }
        });

    }


    /**
     * 获取评价总数和平均分
     */
    public void getCommentsCountAndAverage() {
        if (TextUtils.isEmpty(mProductCode)) return;

        Map<String, String> map = new HashMap<>();

        map.put("entityCode", mProductCode);


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
                mBinding.scoreLayout.tvStarNum.setText(StringUtils.formatInteger(data.getAverage()) + "星");
                mBinding.scoreLayout.ratingbar.setStar(data.getAverage());

            }


            @Override
            protected void onFinish() {

            }
        });


    }

    /**
     * 获取评价列表
     */
    public void getCommentList() {

        if (TextUtils.isEmpty(mProductCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", "10");
        map.put("start", "1");
        map.put("status", "AB"); //审核通过
        map.put("type", "P");
        map.put("entityCode", mProductCode);


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
     * 设置显示数据
     *
     * @param data
     */
    private void setShowData(BrandProductModel data) {
        if (data == null) return;

        mBinding.tvProductName.setText(data.getName());
        mBinding.tvSlogan.setText(data.getSlogan());
        mBinding.tvPrice.setText(MoneyUtils.showPrice(data.getPrice()));
        mBinding.tvSellNum.setText("已出售：" + data.getSoldOutCount());

        mBinding.webView.loadData(data.getDescription(), "text/html;charset=utf-8", "utf-8");

        if (TextUtils.isEmpty(data.getMobile())) {                    //有电话则可以进行电话拨打
            mBinding.callPhone.setVisibility(View.GONE);
        } else {
            mBinding.callPhone.setVisibility(View.VISIBLE);
            mBinding.callPhone.setOnClickListener(view -> {
                CallPhoneActivity.open(this, data.getMobile());
            });
        }

    }

    @Override
    protected void onDestroy() {
        mBinding.bannerProduct.stopAutoPlay();

        mBinding.webView.clearHistory();
        mBinding.webView.loadUrl("about:blank");
        mBinding.webView.stopLoading();
        mBinding.webView.setWebChromeClient(null);
        mBinding.webView.setWebViewClient(null);
        mBinding.webView.destroy();
        ((ViewGroup) mBinding.webView.getParent()).removeView(mBinding.webView);
        super.onDestroy();

    }
}
