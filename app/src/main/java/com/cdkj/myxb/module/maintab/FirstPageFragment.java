package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.FirstPageBrandAdapter;
import com.cdkj.myxb.databinding.FragmentFirstPageBinding;
import com.cdkj.myxb.module.common.MsgListActivity;
import com.cdkj.myxb.module.product.BrandListActivity;
import com.cdkj.myxb.module.product.ProductDetailsActivity;
import com.cdkj.myxb.module.shopper.ShopperAppointmentListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by cdkj on 2018/2/7.
 */

public class FirstPageFragment extends BaseLazyFragment {

    private FragmentFirstPageBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private FirstPageBrandAdapter mAdapter;


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
                mBinding.refreshLayout.setEnableRefresh(false);
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                initAdapter();
                return mAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {

            }
        });

        mRefreshHelper.init(10);
    }

    /**
     * 初始化数据适配器
     */
    private void initAdapter() {
        mAdapter = new FirstPageBrandAdapter(new ArrayList<>());
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
