package com.cdkj.myxb.module.appointment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AppointmentListAdapter;
import com.cdkj.myxb.models.AppointmentCommentsSucc;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.models.AppointmentServiceDoneSucc;
import com.cdkj.myxb.models.AppointmentServiceSucc;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListFragment;
import com.cdkj.myxb.module.order.OrderHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.appointment.AppointmentTabLayoutFragment.INTENTTYPE;

/**
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentListFragment extends AbsRefreshListFragment {

    private static final String ORDERSTATE = "state";
    private static final String ISFIRSTREQUEST = "isFirstRequest";


    private String mOrderState; //要查看的订单状态

    private boolean mIsResumeRefresh;//在Resume时能不能刷新当前界面

    private String mType;//用户类型

    /**
     * @param state          订单状态
     * @param isFirstRequest 创建时是否进行请求
     * @return
     */
    public static AppointmentListFragment getInstanse(String state, String type, boolean isFirstRequest) {
        AppointmentListFragment fragment = new AppointmentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERSTATE, state);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        bundle.putString(INTENTTYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mOrderState = getArguments().getString(ORDERSTATE);
            mType = getArguments().getString(INTENTTYPE);
        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }
    }

    /**
     * 上门成功
     *
     * @param serviceSucc
     */
    @Subscribe
    public void sureServiceSucc(AppointmentServiceSucc serviceSucc) {
        if (TextUtils.equals(mOrderState, OrderHelper.APPOINTMENT_2) || TextUtils.isEmpty(mOrderState)) { //评价成功 如果是待评价页面则刷新
            if (mRefreshHelper != null) {
                mRefreshHelper.onDefaluteMRefresh(false);
            }
        }
    }

    /**
     * 下课成功
     *
     * @param
     */
    @Subscribe
    public void sureServiceDoneSucc(AppointmentServiceDoneSucc da) {
        if (TextUtils.equals(mOrderState, OrderHelper.APPOINTMENT_4) || TextUtils.isEmpty(mOrderState)) { //收货成功
            if (mRefreshHelper != null) {
                mRefreshHelper.onDefaluteMRefresh(false);
            }
        }
    }

    /**
     * 评价成功
     *
     * @param
     */
    @Subscribe
    public void commentSucc(AppointmentCommentsSucc da) {
        mIsResumeRefresh = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRefreshHelper != null && mIsResumeRefresh) {
            mIsResumeRefresh = false;
            mRefreshHelper.onDefaluteMRefresh(false);
        }
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
    public RecyclerView.Adapter getListAdapter(List listData) {
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(listData);
        appointmentListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AppointmentListModel listModel = appointmentListAdapter.getItem(position);

            if (listModel == null) return;

            switch (view.getId()) {
                case R.id.tv_to_comment: //评价
                    AppointmentCommentActivity.open(mActivity, listModel.getCode(), mType);
                    break;
                case R.id.tv_state_do://上门  下课操作
                    AppointmentDoActivity.open(mActivity, listModel.getCode(), listModel.getStatus());
                    break;

            }

        });


        appointmentListAdapter.setOnItemClickListener((adapter, view, position) -> { //评价
            AppointmentListModel listModel = appointmentListAdapter.getItem(position);

            if (listModel == null) return;

            AppointmentDetailActivity.open(mActivity, listModel.getCode(), mType);
        });

        return appointmentListAdapter;
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        if (TextUtils.isEmpty(mType)) return;

        Map<String, String> map = new HashMap<>();

        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", mOrderState);
        map.put("type", mType);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAppointmentList("805520", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<AppointmentListModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<AppointmentListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无预约", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
