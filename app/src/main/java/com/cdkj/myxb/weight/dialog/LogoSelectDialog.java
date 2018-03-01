package com.cdkj.myxb.weight.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.LogoSelectAdapter;
import com.cdkj.myxb.databinding.ActivityLogoSelectBinding;

import java.util.List;

/**
 * 行程显示时间
 * Created by cdkj on 2018/2/10.
 */

public class LogoSelectDialog extends Dialog {

    private ActivityLogoSelectBinding mBinding;


    private RefreshHelper mRefreshHelper;

    private Activity mActivity;

    private LogoSelectDialogListener listener;


    private String mSelectUrl;

    public RefreshHelper getmRefreshHelper() {
        return mRefreshHelper;
    }


    public LogoSelectDialog(@NonNull Activity context) {
        super(context, R.style.TipsDialog);
        this.mActivity = context;
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_logo_select, null, false);
        initRefreshHelper();
    }


    public void setListener(LogoSelectDialogListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int screenWidth = DisplayHelper.getScreenWidth(mActivity);
        int screenHeight = DisplayHelper.getScreenHeight(mActivity);
        setContentView(mBinding.getRoot());
        getWindow().setLayout((int) (screenWidth * 0.9f), (int) (screenHeight * 0.7));
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        mBinding.btnIKnow.setOnClickListener(view -> {

            if (listener != null) {
                listener.onSureClick(mSelectUrl);
            }
            dismiss();
        });

    }

    private void initRefreshHelper() {
        mRefreshHelper = new RefreshHelper(this.mActivity, new BaseRefreshCallBack(mActivity) {
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
                return getaImageAdapter(listData);
            }

            @Override
            public void onLoadMore(int pageindex, int limit) {
                if (listener != null) {
                    listener.onLoadMore(pageindex, limit);
                }

            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                if (listener != null) {
                    listener.onRefresh(pageindex, limit);
                }
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {

            }
        });

        mRefreshHelper.init(15);

        mRefreshHelper.setLayoutManager(new GridLayoutManager(mActivity, 3));
    }

    @Nullable
    private RecyclerView.Adapter getaImageAdapter(List listData) {
        LogoSelectAdapter logoSelectAdapter = new LogoSelectAdapter(listData);
        logoSelectAdapter.setOnItemClickListener((adapter, view, position) -> {

            logoSelectAdapter.setSelectPosition(position);

            mSelectUrl = logoSelectAdapter.getSelectUrl();

        });
        return logoSelectAdapter;
    }


    public interface LogoSelectDialogListener {

        void onRefresh(int pageindex, int limit);

        void onLoadMore(int pageindex, int limit);

        void onSureClick(String url);

    }


}
