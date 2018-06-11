package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.UserAtlasManagerAdapter;
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

public class UserAtlasManagerActivity extends AbsRefreshListActivity {

    private String userId;

    public static void open(Context context, String userId) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserAtlasManagerActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        UserAtlasManagerAdapter userAtlasAdapter = new UserAtlasManagerAdapter(listData);

        userAtlasAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            showDoubleWarnListen("您确定要将该经销商分配给此服务团队吗?",view1 -> {
                AtlasListModel model = userAtlasAdapter.getItem(position);
                divide(model.getUserId());
            });



        });

        return userAtlasAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAtlasManagerList("805145", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<AtlasListModel>(this) {


            @Override
            protected void onSuccess(List<AtlasListModel> data, String SucMessage) {
                mRefreshHelper.setData(data, "暂无同级服务团队", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("经销商管理");

        initRefreshHelper(10);

        if (getIntent() == null)
            return;

        userId = getIntent().getStringExtra("userId");
        mRefreshHelper.onDefaluteMRefresh(true);

        mRefreshBinding.refreshLayout.setEnableLoadmore(false);
    }

    private void divide(String mdUserId){
        if (TextUtils.isEmpty(mdUserId))
            return;

        Map<String, String> map = new HashMap<>();

        map.put("mdUserId", mdUserId);
        map.put("userId", userId);
        map.put("operaterId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805140", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {


            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()){
                    UITipDialog.showSuccess(UserAtlasManagerActivity.this, getString(R.string.do_success), dialogInterface -> {

                        finish();
                    });
                }else {

                    UITipDialog.showFall(UserAtlasManagerActivity.this, getString(R.string.do_fail));
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
