package com.cdkj.myxb.module.appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.adapters.CommonAppointmentListAdapter;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsSearchActivity;
import com.cdkj.myxb.module.user.UserHelper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.appointment.CommonAppointmentListActivity.INTENTTYPE;

/**
 * 预约搜索
 * Created by cdkj on 2018/2/25.
 */

public class AppointmentSearchActivity extends AbsSearchActivity {

    private String mType; //预约类型

    /**
     * @param context
     * @param type
     */
    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AppointmentSearchActivity.class);
        intent.putExtra(INTENTTYPE, type);
        context.startActivity(intent);
    }


    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        CommonAppointmentListAdapter shopperListAdapter = new CommonAppointmentListAdapter(listData);
        shopperListAdapter.setOnItemClickListener((adapter, view, position) -> {

            if (shopperListAdapter.getItem(position) == null) return;

            CommonAppointmentUserDetailActivity.open(AppointmentSearchActivity.this, shopperListAdapter.getItem(position).getUserId(), mType);
        });
        return shopperListAdapter;
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra(INTENTTYPE);
        }
        super.afterCreate(savedInstanceState);
    }

    @Override
    public void searchRequest(String str, int pageindex, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mType)) return;

        Map map = RetrofitUtils.getRequestMap();
        map.put("kind", mType);
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("status", "0");//上架
        map.put("userName", str);
        map.put("orderColumn", "order_no");
        map.put("orderDir", "asc");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserList("805120", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<UserModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<UserModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无搜索" + UserHelper.getUserTypeByKind(mType), 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

}
