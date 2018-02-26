package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.FirstPageBrandAdapter;
import com.cdkj.myxb.databinding.FragmentFirstPageBinding;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.MsgListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.MsgListActivity;
import com.cdkj.myxb.module.product.BrandListActivity;
import com.cdkj.myxb.module.product.SpecificBrandListActivity;
import com.cdkj.myxb.module.shopper.ShopperAppointmentListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 首页
 * Created by cdkj on 2018/2/7.
 */

public class FirstPageFragment extends BaseLazyFragment {

    private FragmentFirstPageBinding mBinding;

    private RefreshHelper mRefreshHelper;


    public static FirstPageFragment getInstanse() {
        FirstPageFragment fragment = new FirstPageFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_page, null, false);

        initRefresh();

        initListener();

        mRefreshHelper.onDefaluteMRefresh(true);

        getMsgRequest();

        return mBinding.getRoot();
    }

    private void initListener() {

        mBinding.headrLayout.linMsg.setOnClickListener(view -> {
            MsgListActivity.open(mActivity);
        });

        /*美导*/
        mBinding.headrLayout.llinayoutShopper.setOnClickListener(view -> {
            ShopperAppointmentListActivity.open(mActivity);
        });
        /*品牌*/
        mBinding.headrLayout.llayoutBrand.setOnClickListener(view -> {
            BrandListActivity.open(mActivity);
        });

    }

    /**
     * 初始化刷新相关
     */
    private void initRefresh() {

        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
//                mBinding.refreshLayout.setEnableRefresh(false);
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getFirstPageBrandAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getBrandListBrand(pageindex, limit, isShowDialog);
            }


        });

        mRefreshHelper.init(10);
        mRefreshHelper.setLayoutManager(new ScrollGridLayoutManager(mActivity, 2));
    }

    /**
     * 获取数据适配器
     *
     * @param listData
     * @return
     */
    @NonNull
    private FirstPageBrandAdapter getFirstPageBrandAdapter(List listData) {

        FirstPageBrandAdapter firstPageBrandAdapter = new FirstPageBrandAdapter(listData);

        firstPageBrandAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandListModel brandListModel = firstPageBrandAdapter.getItem(position);

            if (brandListModel == null) return;

            SpecificBrandListActivity.open(mActivity, brandListModel);

        });

        return firstPageBrandAdapter;
    }


    public void getMsgRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("channelType", "4");
        map.put("start", "1");
        map.put("limit", "1");
        map.put("pushType", "41");
        map.put("toKind", "C");
        map.put("status", "1");
        map.put("fromSystemCode", MyCdConfig.SYSTEMCODE);
        map.put("toSystemCode", MyCdConfig.SYSTEMCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getMsgList("804040", StringUtils.getJsonToString(map));

        addCall(call);


        call.enqueue(new BaseResponseModelCallBack<MsgListModel>(mActivity) {
            @Override
            protected void onSuccess(MsgListModel data, String SucMessage) {
                if (data.getList() == null || data.getList().size() < 1) {
                    return;
                }

                mBinding.headrLayout.tvMsg.setText(data.getList().get(0).getSmsTitle());
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {

            }


            @Override
            protected void onFinish() {
            }
        });
    }

    /**
     * 初始化布局
     */
    private void initLayout() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }


    /**
     * 获取品牌列表 TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
     * <p>
     * 0
     */
    public void getBrandListBrand(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("status", "2");
        map.put("location", "1");//0否，1是
        map.put("limit", limit + "");
        map.put("start", pageindex + "");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getSpeBrandList("805256", StringUtils.getJsonToString(map));


        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<BrandListModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<BrandListModel> data, String SucMessage) {
                if (data.getList() == null || data.getList().isEmpty()) {
                    if (pageindex == 1) {
                        mRefreshHelper.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
                    }
                } else if (pageindex == 1) {
                    mRefreshHelper.setLayoutManager(new ScrollGridLayoutManager(mActivity, 2));
                }

                mRefreshHelper.setData(data.getList(), getString(R.string.no_recommended_brand), 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
