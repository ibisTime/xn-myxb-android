package com.cdkj.myxb.module.banner;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AllRankListAdapter;
import com.cdkj.myxb.databinding.LayoutRankHeaderBinding;
import com.cdkj.myxb.models.RankModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌排名 美容院排名 专家
 * Created by cdkj on 2018/3/1.
 */

public class AllRankListActivity extends AbsRefreshListActivity {


    private LayoutRankHeaderBinding mHeaderBinding;
    private AllRankListAdapter rankListAdapter;

    public static final String BRANDTYPE = "0"; //品牌
    public static final String MRYTYPE = "2";//美容院
    public static final String EXPERTSTYPE = "3";//专家

    private String mType;


    /**
     *
     * @param context
     * @param type  排名类型
     */

    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AllRankListActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("type");
        }

        setTopTitle();


        mHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_rank_header, null, false);


        initRefreshHelper(6);

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    /**
     * 设置title
     */
    private void setTopTitle() {
        if (TextUtils.equals(BRANDTYPE, mType)) {
            mBaseBinding.titleView.setMidTitle("店铺排名");
        } else if (TextUtils.equals(MRYTYPE, mType)) {
            mBaseBinding.titleView.setMidTitle("美容院排名");
        } else if (TextUtils.equals(EXPERTSTYPE, mType)) {
            mBaseBinding.titleView.setMidTitle("专家排名");
        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        rankListAdapter = new AllRankListAdapter(listData);
        rankListAdapter.setHeaderAndEmpty(true);
        rankListAdapter.addHeaderView(mHeaderBinding.getRoot());
        mRefreshBinding.refreshLayout.setEnableRefresh(false);
        mRefreshBinding.refreshLayout.setEnableLoadmore(false);
        return rankListAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        Call call = null;

        if (TextUtils.equals(BRANDTYPE, mType)) {
            call = RetrofitUtils.createApi(MyApiServer.class).getRankList("805301", StringUtils.getJsonToString(map));
        } else if (TextUtils.equals(MRYTYPE, mType)) {
            call = RetrofitUtils.createApi(MyApiServer.class).getRankList("805133", StringUtils.getJsonToString(map));
        } else if (TextUtils.equals(EXPERTSTYPE, mType)) {
            call = RetrofitUtils.createApi(MyApiServer.class).getRankList("805132", StringUtils.getJsonToString(map));
        } else {
            return;
        }


        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<RankModel>(this) {

            @Override
            protected void onSuccess(List<RankModel> data, String SucMessage) {
                setShowData(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void setShowData(List<RankModel> data) {
        if (data.size() <= 3) {
            mRefreshHelper.setData(data, "", 0);
            if (rankListAdapter != null) {
                rankListAdapter.removeHeaderView(mHeaderBinding.getRoot());
            }
            return;
        }

        List<RankModel> topData = data.subList(0, 3);
        List<RankModel> endData = data.subList(3, data.size());

        mRefreshHelper.setData(endData, "暂无排名", 0);

        for (int i = 0; i < topData.size(); i++) {

            RankModel rankModel = topData.get(i);

            if (rankModel == null) continue;

            switch (i) {
                case 0:
                    ImgUtils.loadQiNiuBorderLogo(this, rankModel.getPhoto(), mHeaderBinding.imgRank1, R.color.gray);
                    mHeaderBinding.tvName1.setText(rankModel.getName());
                    mHeaderBinding.tvPrice1.setText(MoneyUtils.getShowPriceSign(rankModel.getAmount()));
                    break;
                case 1:
                    ImgUtils.loadQiNiuBorderLogo(this, rankModel.getPhoto(), mHeaderBinding.imgRank2, R.color.gray);
                    mHeaderBinding.tvName2.setText(rankModel.getName());
                    mHeaderBinding.tvPrice2.setText(MoneyUtils.getShowPriceSign(rankModel.getAmount()));
                    break;
                case 2:
                    ImgUtils.loadQiNiuBorderLogo(this, rankModel.getPhoto(), mHeaderBinding.imgRank3, R.color.gray);
                    mHeaderBinding.tvName3.setText(rankModel.getName());
                    mHeaderBinding.tvPrice3.setText(MoneyUtils.getShowPriceSign(rankModel.getAmount()));
                    break;
            }


        }


    }

}
