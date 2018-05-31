package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.UserAtlasLowestAdapter;
import com.cdkj.myxb.adapters.UserAtlasTypeTAdapter2;
import com.cdkj.myxb.databinding.HeaderUserAtlasTypeTBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.models.AtlasOuterModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasTypeTActivity extends AbsRefreshListActivity {

    private List<AtlasListModel> mgJxsList = new ArrayList<>();

    private HeaderUserAtlasTypeTBinding mHeaderBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserAtlasTypeTActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        UserAtlasTypeTAdapter2 mJxsAdapter = new UserAtlasTypeTAdapter2(listData);
        mJxsAdapter.setHeaderAndEmpty(true);
        mJxsAdapter.addHeaderView(mHeaderBinding.getRoot());

        return mJxsAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAtlasModel("805146", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<AtlasOuterModel>(this) {

            @Override
            protected void onSuccess(AtlasOuterModel data, String SucMessage) {
                mgJxsList.clear();
                mgJxsList.addAll(data.getMgJxsList());

                initHeader();
                if (mgJxsList.size() == 0){
                    mRefreshHelper.setData(data.getLineList(), "暂无图谱", 0);
                }else {
                    mRefreshHelper.setData(data.getLineList(),"", 0);
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("网络图谱");

        mHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_user_atlas_type_t, null, false);

        initRefreshHelper(10);
        mRefreshHelper.onDefaluteMRefresh(true);
    }

    private void initHeader() {

        if (mgJxsList == null || mgJxsList.size()==0){
            mHeaderBinding.llMgJxs.setVisibility(View.GONE);
        }else {
            mHeaderBinding.llMgJxs.setVisibility(View.VISIBLE);
        }

        UserAtlasLowestAdapter mMgJxsAdapter = new UserAtlasLowestAdapter(mgJxsList);
        mMgJxsAdapter.setOnItemClickListener((adapter, view, position) -> {

            AtlasListModel model = mMgJxsAdapter.getItem(position);
            model.setShow(!model.isShow());
            mMgJxsAdapter.notifyItemChanged(position);
        });
        mHeaderBinding.rvMgJxs.setLayoutManager(getLinearLayoutManager());
        mHeaderBinding.rvMgJxs.setAdapter(mMgJxsAdapter);

        mHeaderBinding.llMgJxs.setOnClickListener(view -> {
            if (mHeaderBinding.rvMgJxs.getVisibility() == View.GONE){
                mHeaderBinding.rvMgJxs.setVisibility(View.VISIBLE);
                mHeaderBinding.ivMgJxs.setBackgroundResource(R.drawable.user_atlas_up);
            }else {
                mHeaderBinding.rvMgJxs.setVisibility(View.GONE);
                mHeaderBinding.ivMgJxs.setBackgroundResource(R.drawable.user_atlas_down);
            }
        });

        ((DefaultItemAnimator) mHeaderBinding.rvMgJxs.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRefreshHelper.onDefaluteMRefresh(true);
    }

    /**
     * 获取布局管理器
     *
     * @return
     */
    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {  //禁止自滚动
                return false;
            }
        };
    }
}
