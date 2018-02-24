package com.cdkj.myxb.module.product;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.BaseApiServer;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.BrandListAdapter;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌产品列表
 * Created by 李先俊 on 2018/2/24.
 */

public class BrandProductListFragment extends BaseLazyFragment {


    private static final String BRANDCODE = "brandCode";
    private static final String ISFIRSTREQUEST = "isFirstRequest";
    private LayoutRecyclerRefreshBinding mBinding;

    private String mBrandCode;//品牌编号

    private RefreshHelper mRefreshHelper;


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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_recycler_refresh, null, false);

        if (getArguments() != null) {
            mBrandCode = getArguments().getString(BRANDCODE);
        }

        initRefreshHelper();

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }

        return mBinding.getRoot();
    }

    /**
     * 初始化刷新辅助类
     */
    private void initRefreshHelper() {

        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));//添加分割线
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return new BrandListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getBrandProductListRequest(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);
    }


    /**
     * 获取品牌数据
     *
     * @param pageindex
     * @param limit
     * @param isShowDialog
     */
    public void getBrandProductListRequest(int pageindex, int limit, boolean isShowDialog) {

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
    public void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
