package com.cdkj.myxb.module.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.adapters.BrandListAdapter;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌产品列表
 * Created by cdkj on 2018/2/24.
 */

public class BrandProductListFragment extends AbsRefreshListFragment {


    private static final String BRANDCODE = "brandCode";
    private static final String ISFIRSTREQUEST = "isFirstRequest";

    private String mBrandCode;//品牌编号

    /**
     * @param brandCode 品牌编号
     * @return
     */
    public static BrandProductListFragment getInstanse(String brandCode, boolean isFirstRequest) {
        BrandProductListFragment fragment = new BrandProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BRANDCODE, brandCode);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        if (mRefreshHelper != null) {
            mRefreshHelper.onDefaluteMRefresh(false);
        }
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            mBrandCode = getArguments().getString(BRANDCODE);
        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }

    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        BrandListAdapter brandListAdapter = new BrandListAdapter(listData);

        brandListAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandProductModel brandProductModel = brandListAdapter.getItem(position);

            if (brandListAdapter == null) return;

            ProductDetailsActivity.open(mActivity, brandProductModel.getCode());

        });

        return brandListAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        if (TextUtils.isEmpty(mBrandCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("brandCode", mBrandCode);
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", "2");//TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandProductList("805266", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<BrandProductModel>>(mActivity) {


            @Override
            protected void onSuccess(ResponseInListModel<BrandProductModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无产品", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
