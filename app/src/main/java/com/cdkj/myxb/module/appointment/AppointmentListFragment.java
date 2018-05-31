package com.cdkj.myxb.module.appointment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AppointmentListAdapter;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.AppointmentCommentsSucc;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.models.AppointmentServiceDoneSucc;
import com.cdkj.myxb.models.AppointmentServiceSucc;
import com.cdkj.myxb.models.event.EventDoChangeCountModel;
import com.cdkj.myxb.models.event.EventInputCommentsSuc;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.order.OrderHelper;
import com.cdkj.myxb.module.user.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.myxb.module.appointment.AppointmentTabLayoutFragment.INTENTTYPE;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENTALL;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DJD;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DLR;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_DSH;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_FWZT;
import static com.cdkj.myxb.module.order.OrderHelper.APPOINTMENT_YWC;

/**
 * Created by cdkj on 2018/2/26.
 */

public class AppointmentListFragment extends BaseLazyFragment {

    private static final String ORDERSTATE = "state";
    private static final String ISFIRSTREQUEST = "isFirstRequest";

    private String mOrderState; //要查看的订单状态

    private boolean mIsResumeRefresh;//在Resume时能不能刷新当前界面

    private String mType;//用户类型

    protected LayoutRecyclerRefreshBinding mRefreshBinding;

    protected RefreshHelper mRefreshHelper;

    /**
     * @param state          订单状态
     * @param isFirstRequest 创建时是否进行请求
     * @return
     */
    public static AppointmentListFragment getInstance(String state, String type, boolean isFirstRequest) {
        AppointmentListFragment fragment = new AppointmentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERSTATE, state);
        bundle.putBoolean(ISFIRSTREQUEST, isFirstRequest);
        bundle.putString(INTENTTYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRefreshBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);

        if (getArguments() != null) {
            mOrderState = getArguments().getString(ORDERSTATE);
            mType = getArguments().getString(INTENTTYPE);

        }

        initRefreshHelper(10);

        if (getArguments() != null && getArguments().getBoolean(ISFIRSTREQUEST)) {
            mRefreshHelper.onDefaluteMRefresh(true);
        }

        return mRefreshBinding.getRoot();
    }


    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mRefreshBinding.refreshLayout;
            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                super.onRefresh(pageindex, limit);

                EventDoChangeCountModel model = new EventDoChangeCountModel();
                model.setOrder(true);
                EventBus.getDefault().post(model);
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mRefreshBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getListRequest(pageindex, limit, isShowDialog);
            }
        });
        mRefreshHelper.init(limit);

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

    /**
     * 评价成功
     *
     * @param
     */
    @Subscribe
    public void inputSucc(EventInputCommentsSuc da) {
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
            mRefreshHelper.onDefaluteMRefresh(true);
        }
    }

    @Override
    protected void onInvisible() {

    }


    public RecyclerView.Adapter getListAdapter(List listData) {
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(listData);
        appointmentListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AppointmentListModel listModel = appointmentListAdapter.getItem(position);

            if (listModel == null) return;

            switch (view.getId()) {
                case R.id.tv_to_comment: //评价
                    AppointmentCommentActivity.open(mActivity, listModel.getCode(), listModel.getType(), listModel.getOwner());
                    break;
                case R.id.tv_state_do: //上门  下课操作

                    AppointmentDoActivity.open(mActivity, listModel.getCode(), listModel.getStatus());
                    break;
            }

        });

        appointmentListAdapter.setOnItemClickListener((adapter, view, position) -> { //评价
            AppointmentListModel listModel = appointmentListAdapter.getItem(position);

            if (listModel == null) return;

            AppointmentDetailActivity.open(mActivity, listModel.getCode(), listModel.getType());
        });

        return appointmentListAdapter;
    }

    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        if (TextUtils.isEmpty(mType)) return;

        Map<String, Object> map = new HashMap<>();

        if (SPUtilHelpr.getUserType().equals(UserHelper.C)){
            map.put("applyUser", SPUtilHelpr.getUserId());
            map.put("type", mType);
        }else {
            map.put("owner", SPUtilHelpr.getUserId());
        }
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("statusList", getStatusList());

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


    private List<String> getStatusList(){
        List<String> statusList = new ArrayList<>();

        if (mOrderState == null)
            return statusList;


        switch (mOrderState){

            case APPOINTMENTALL:

                statusList.add("1");
                statusList.add("2");
                statusList.add("4");
                statusList.add("5");
                statusList.add("6");
                statusList.add("7");
                statusList.add("8");
                statusList.add("9");
                statusList.add("10");
                statusList.add("11");

                return statusList;

            case APPOINTMENT_FWZT:

                statusList.add("1");
                statusList.add("2");
                statusList.add("4");
                statusList.add("5");
                statusList.add("8");

                return statusList;

            case APPOINTMENT_DJD:

                statusList.add("1");

                return statusList;

            case APPOINTMENT_DLR:

                statusList.add("5");
                statusList.add("8");

                return statusList;

            case APPOINTMENT_DSH:

                statusList.add("6");
                statusList.add("7");
                statusList.add("9");

                return statusList;

            case APPOINTMENT_YWC:

                statusList.add("10");
                statusList.add("11");

                return statusList;

            default:

                return statusList;

        }

    }




}
