package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.UserAtlasLowerCAdapter;
import com.cdkj.myxb.databinding.HeaderUserAtlasTypeCBinding;
import com.cdkj.myxb.models.AtlasListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/12.
 */

public class UserAtlasTypeCActivity extends AbsRefreshListActivity {

    private HeaderUserAtlasTypeCBinding mHeaderBinding;

    private String maxNumber;

    public static void open(Context context, String maxNumber) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserAtlasTypeCActivity.class);
        intent.putExtra("maxNumber", maxNumber);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        UserAtlasLowerCAdapter mJxsAdapter = new UserAtlasLowerCAdapter(listData);

        mJxsAdapter.addHeaderView(mHeaderBinding.getRoot());
        mJxsAdapter.setHeaderAndEmpty(true);

        return mJxsAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAtlasTypeCList("805146", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AtlasListModel>(this) {


            @Override
            protected void onSuccess(List<AtlasListModel> data, String SucMessage) {
                mRefreshHelper.setData(data, "暂无图谱", 0);
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

        if (getIntent() == null)
            return;

        maxNumber = getIntent().getStringExtra("maxNumber");

        mHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_user_atlas_type_c, null, false);
        mHeaderBinding.tvMaxNumber.setText(maxNumber);

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);
        mRefreshBinding.refreshLayout.setEnableLoadmore(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRefreshHelper.onDefaluteMRefresh(true);
    }
}
