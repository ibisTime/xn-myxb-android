package com.cdkj.myxb.module.banner;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityHappymsgDetailsBinding;
import com.cdkj.myxb.models.HappyMsgModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.weight.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.banner.HappyMsgListActivity.HAPPYMSG;
import static com.cdkj.myxb.module.banner.HappyMsgListActivity.MSGTYPE;

/**
 * 喜报/预报详情
 * Created by cdkj on 2018/3/1.
 */

public class HappyMsgDetailsActivity extends AbsBaseLoadActivity {


    private ActivityHappymsgDetailsBinding mBinding;
    public static final String CODE = "code";
    public String mType = "";
    private String mCode;
    private List<String> mbannerUrlList;

    /**
     * @param context
     * @param msgType
     */
    public static void open(Context context, String msgCode, String msgType) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, HappyMsgDetailsActivity.class);
        intent.putExtra(MSGTYPE, msgType);
        intent.putExtra(CODE, msgCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_happymsg_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mType = getIntent().getStringExtra(MSGTYPE);
            mCode = getIntent().getStringExtra(CODE);
        }

        if (TextUtils.equals(mType, HAPPYMSG)) {
            mBaseBinding.titleView.setMidTitle("喜报详情");
        } else {
            mBaseBinding.titleView.setMidTitle("预报详情");
        }
        initBanner();
        getMsgDetails();

    }


    private void getMsgDetails() {

        if (TextUtils.isEmpty(mCode)) return;

        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getHappyMsgDetail("805436", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<HappyMsgModel>(this) {
            @Override
            protected void onSuccess(HappyMsgModel data, String SucMessage) {
                setShowData(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void setShowData(HappyMsgModel data) {
        setBannerData(data.getAdvPic());
        ImgUtils.loadQiniuImg(this, data.getPic(), mBinding.imgMsg);

        mBinding.tvTitle.setText(data.getTitle());
        mBinding.webView.loadData(data.getDescription(), "text/html;charset=utf-8", "utf-8");
    }

    private void initBanner() {

        mbannerUrlList = new ArrayList<>();

        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());

    }

    /**
     * 设置轮播图数据
     *
     * @param urls
     */
    private void setBannerData(String urls) {
        mbannerUrlList = StringUtils.splitAsPicList(urls);
        mBinding.banner.setImages(mbannerUrlList);
        mBinding.banner.start();
    }


    @Override
    protected void onDestroy() {
        mBinding.banner.stopAutoPlay();
        mBinding.webView.clearHistory();
        ((ViewGroup) mBinding.webView.getParent()).removeView(mBinding.webView);
        mBinding.webView.loadUrl("about:blank");
        mBinding.webView.stopLoading();
        mBinding.webView.setWebChromeClient(null);
        mBinding.webView.setWebViewClient(null);
        mBinding.webView.destroy();
        super.onDestroy();
    }
}
