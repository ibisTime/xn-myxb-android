package com.cdkj.myxb.module.product;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.BrandListAdapter;
import com.cdkj.myxb.adapters.BrandProductListAdapter;
import com.cdkj.myxb.databinding.FragmentBrandProductListBinding;
import com.cdkj.myxb.databinding.HeaderBrandProductListBinding;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.zzhoujay.richtext.RichText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌产品列表
 * Created by cdkj on 2018/2/24.
 */

public class BrandProductListFragment extends BaseLazyFragment {


    private static final String CODE = "code";
    private static final String DESCRIPTION = "description";
    private static final String ISFIRSTREQUEST = "isFirstRequest";
    private static final String IS_SHOW_CATEGORY = "isShowCategory";

    private String mBrandCode;//品牌编号

    private String code;//编号
    private String description;// 品牌简介

    private FragmentBrandProductListBinding mBinding;

    private HeaderBrandProductListBinding mHeaderBinding;

    private RefreshHelper mRefreshHelper;

    /**
     * @param code 编号 isShowCategory为true时 code为大类编号，false时code为小类编号
     * @return
     */
    public static BrandProductListFragment getInstance(String code, String description, boolean isFirstRequest, boolean isShowCategory) {
        BrandProductListFragment fragment = new BrandProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CODE, code);
        bundle.putString(DESCRIPTION, description);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        bundle.putBoolean(IS_SHOW_CATEGORY, isShowCategory);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        if (mBinding != null) {
            getBrandListRequest(true);
        }
    }

    @Override
    protected void onInvisible() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_brand_product_list, null, false);

        mHeaderBinding = DataBindingUtil.inflate(inflater, R.layout.header_brand_product_list, null, false);

        init();

        return mBinding.getRoot();
    }

    private void init(){
        if (getArguments() != null ) {
            code = getArguments().getString(CODE);
            description = getArguments().getString(DESCRIPTION);

            mHeaderBinding.tvDescription.setVisibility(TextUtils.isEmpty(description) ? View.GONE : View.VISIBLE);
            RichText.from(TextUtils.isEmpty(description) ? "" : "品牌简介:" + description).into(mHeaderBinding.tvDescription);

        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {

            if (getArguments().getBoolean(IS_SHOW_CATEGORY)){
                getBrandListRequest(true);
            }else {
                mBinding.rvBrand.setVisibility(View.GONE);
                mBinding.viewV.setVisibility(View.GONE);

                mBrandCode = code;
                mRefreshHelper.onDefaluteMRefresh(true);
            }

        }
    }


    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                super.onRefresh(pageindex, limit);
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.rvProduct;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getListRequest(pageindex, limit, isShowDialog);
            }
        });
        mRefreshHelper.init(limit);

    }

    public BrandProductListAdapter getListAdapter(List listData) {
        BrandProductListAdapter brandProductListAdapter = new BrandProductListAdapter(listData);

        brandProductListAdapter.setHeaderAndEmpty(true);
        brandProductListAdapter.setHeaderView(mHeaderBinding.getRoot());

        brandProductListAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandProductModel brandProductModel = brandProductListAdapter.getItem(position);

            ProductDetailsActivity.open(mActivity, brandProductModel.getCode());

        });

        return brandProductListAdapter;
    }

    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        if (TextUtils.isEmpty(mBrandCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("brandCode", mBrandCode);
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("status", "2");//TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
        map.put("orderColumn", "order_no");
        map.put("orderDir", "asc");
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandProductList("805266", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<BrandProductModel>>(mActivity) {


            @Override
            protected void onSuccess(ResponseInListModel<BrandProductModel> data, String SucMessage) {

                mRefreshHelper.setData(data.getList(), "暂无商品", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    public void getBrandListRequest(boolean isShowDialog) {
        if (TextUtils.isEmpty(code)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("categoryCode", code);
        map.put("status", "2"); //TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
//        map.put("orderColumn", "order_no");
//        map.put("orderDir", "asc");
        map.put("start", "1");
        map.put("limit", "10");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandList("805258", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BrandListModel>(mActivity) {


            @Override
            protected void onSuccess(List<BrandListModel> data, String SucMessage) {
                if (data == null || data.size() == 0)
                    return;

                mBrandCode = data.get(0).getCode();
                data.get(0).setChoose(true);

                setListAdapter(data);
                mRefreshHelper.onDefaluteMRefresh(isShowDialog);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    public void setListAdapter(List<BrandListModel> listData) {
        BrandListAdapter brandListAdapter = new BrandListAdapter(listData);

        mHeaderBinding.tvDescription.setVisibility(TextUtils.isEmpty(listData.get(0).getDescription()) ? View.GONE : View.VISIBLE);
        RichText.from("品牌简介:" + listData.get(0).getDescription()).into(mHeaderBinding.tvDescription);

        brandListAdapter.setOnItemClickListener((adapter, view, position) -> {

            for (BrandListModel model : brandListAdapter.getData()) {
                model.setChoose(false);
            }

            BrandListModel model = brandListAdapter.getItem(position);
            model.setChoose(true);

            mHeaderBinding.tvDescription.setVisibility(TextUtils.isEmpty(model.getDescription()) ? View.GONE : View.VISIBLE);
            RichText.from("品牌简介:" + model.getDescription()).into(mHeaderBinding.tvDescription);

            mBrandCode = model.getCode();
            brandListAdapter.notifyDataSetChanged();
            mRefreshHelper.onDefaluteMRefresh(true);

        });

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.rvBrand.setLayoutManager(linearLayoutManager);
        mBinding.rvBrand.setAdapter(brandListAdapter);
    }

}
