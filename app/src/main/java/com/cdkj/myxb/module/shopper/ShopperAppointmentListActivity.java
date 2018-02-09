package com.cdkj.myxb.module.shopper;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.LayoutCommonRecyclerRefreshBinding;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.ShopperListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 美导预约 列表
 * Created by cdkj on 2018/2/9.
 */

public class ShopperAppointmentListActivity extends AbsBaseLoadActivity {


    private RefreshHelper mRefreshHelper;

    private LayoutCommonRecyclerRefreshBinding mBinding;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopperAppointmentListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_common_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle(getString(R.string.shopper_appoint));
        mBaseBinding.titleView.setRightImg(R.drawable.search_waite);

        initRefresh();

    }

    private void initRefresh() {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.rv;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                List<String> list = new ArrayList();
                list.add("dd");
                list.add("dd");
                list.add("dd");
                ShopperListAdapter shopperListAdapter = new ShopperListAdapter(list);
                shopperListAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ShopperAppointmentActivity.open(ShopperAppointmentListActivity.this);
                });
                return shopperListAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {

            }
        });

        mRefreshHelper.init(10);
    }
}
