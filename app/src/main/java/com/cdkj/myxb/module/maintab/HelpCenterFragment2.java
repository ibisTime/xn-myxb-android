package com.cdkj.myxb.module.maintab;

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
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.QuestionAdapter;
import com.cdkj.myxb.databinding.FragmentHelpCenter3Binding;
import com.cdkj.myxb.models.QuestionModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AdviceActivity;
import com.cdkj.myxb.module.common.CallPhoneActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/1.
 */

public class HelpCenterFragment2 extends BaseLazyFragment {

    private FragmentHelpCenter3Binding mBinding;

    private RefreshHelper mRefreshHelper;

    public static HelpCenterFragment2 getInstance() {
        HelpCenterFragment2 fragment = new HelpCenterFragment2();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_center_3, null, false);

        initListener();
        initRefreshHelper();

        mRefreshHelper.onDefaluteMRefresh(true);

        return mBinding.getRoot();
    }

    private void initListener() {

        mBinding.tvTopRight.setOnClickListener(view -> {
            getPhone("telephone");
        });

        mBinding.llFeedBack.setOnClickListener(view -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {

                return;
            }

            AdviceActivity.open(mActivity);
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 初始化刷新相关
     */
    private void initRefreshHelper() {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack(mActivity) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {

                QuestionAdapter questionAdapter = new QuestionAdapter(listData);

//                addressListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        if (isSelect) { //选择地址
//                            AddressModel addr = (AddressModel) addressListAdapter.getItem(position);
//                            EventBus.getDefault().post(addr);
//                            finish();
//                        }
//                    }
//                });

                return questionAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getQuestionRequest(true, pageindex, limit);
            }
        });
        mRefreshHelper.init(10);

    }

    /**
     * 获取地址请求
     *
     * @param canShowDialog
     */
    public void getQuestionRequest(boolean canShowDialog, int pageIndex, int limit) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", "1");
        map.put("limit", limit+"");
        map.put("start", pageIndex+"");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getQuestion("805445", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<QuestionModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<QuestionModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), getString(R.string.no_question), 0);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                mRefreshHelper.loadError(errorMessage, 0);
            }

            @Override
            protected void onFinish() {
                if (canShowDialog) disMissLoading();
            }
        });
    }

    public void getPhone(String key) {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("ckey", key);
        map.put("systemCode", MyCdConfig.SYSTEMCODE);
        map.put("companyCode", MyCdConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    UITipDialog.showInfo(mActivity, "暂无客服信息");
                    return;
                }
                CallPhoneActivity.open(mActivity, data.getCvalue());
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
