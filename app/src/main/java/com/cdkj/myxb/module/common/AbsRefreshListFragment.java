package com.cdkj.myxb.module.common;

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
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;

import java.util.List;

/**
 * 公用刷新
 * Created by cdkj on 2018/2/28.
 */

public abstract class AbsRefreshListFragment<T> extends BaseLazyFragment {

    protected LayoutRecyclerRefreshBinding mRefreshBinding;

    protected RefreshHelper mRefreshHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRefreshBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);
        afterCreate(inflater, container, savedInstanceState);
        return mRefreshBinding.getRoot();
    }

    protected abstract void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack<T>(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mRefreshBinding.refreshLayout;
            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                super.onRefresh(pageindex, limit);
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

    abstract public void getListRequest(int pageIndex, int limit, boolean isShowDialog);


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
