package com.cdkj.myxb.module.common;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;

import java.util.List;

/**
 * 公用刷新
 * Created by cdkj on 2018/2/28.
 */

public abstract class AbsRefreshListActivity<T> extends AbsBaseLoadActivity {

    protected LayoutRecyclerRefreshBinding mRefreshBinding;

    protected RefreshHelper mRefreshHelper;

    @Override
    public View addMainView() {
        mRefreshBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);
        return mRefreshBinding.getRoot();
    }


    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack<T>(this) {
            @Override
            public View getRefreshLayout() {
                return mRefreshBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mRefreshBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List<T> listData) {
                return getListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getListRequest(pageindex, limit, isShowDialog);
            }
        });
        mRefreshHelper.init(limit);

    }


    abstract public RecyclerView.Adapter getListAdapter(List<T> listData);

    abstract public void getListRequest(int pageindex, int limit, boolean isShowDialog);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
