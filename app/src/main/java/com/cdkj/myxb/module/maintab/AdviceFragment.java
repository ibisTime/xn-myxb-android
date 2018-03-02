package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.databinding.EmptyViewBinding;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.baselibrary.views.MyDividerItemDecoration;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.AdviceListAdapter;
import com.cdkj.myxb.databinding.FragmentAdviceBinding;
import com.cdkj.myxb.models.AdviceListModel;
import com.cdkj.myxb.models.AdviceScoreModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AdviceActivity;
import com.cdkj.myxb.module.common.AdviceListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/2/8.
 */

public class AdviceFragment extends BaseLazyFragment {


    private FragmentAdviceBinding mBinding;
    private AdviceListAdapter adviceListAdapter;


    public static AdviceFragment getInstanse() {
        AdviceFragment fragment = new AdviceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_advice, null, false);

        mBinding.headerLayout.tvShowAll.setOnClickListener(view -> {

        });

        mBinding.tvToComment.setOnClickListener(view -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {

                return;
            }

            AdviceActivity.open(mActivity);

        });

        mBinding.ratingbar.setOnRatingChangeListener(rat -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                mBinding.ratingbar.setStar(0);
                ToastUtil.show(mActivity, "登录之后才能评分");
                return;
            }

            releaseRequest((int) rat);
        });

        mBinding.headerLayout.tvShowAll.setOnClickListener(view -> AdviceListActivity.open(mActivity));

        initAdviceAdapter();

        return mBinding.getRoot();
    }

    /**
     * 初始化建议适配器
     */
    private void initAdviceAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        adviceListAdapter = new AdviceListAdapter(new ArrayList<>());
        EmptyViewBinding emptyViewBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.empty_view, null, false);
        emptyViewBinding.tv.setText("暂无建议");
        adviceListAdapter.setEmptyView(emptyViewBinding.getRoot());
        mBinding.recyclerView.addItemDecoration(new MyDividerItemDecoration(mActivity, MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.recyclerView.setAdapter(adviceListAdapter);
    }


    @Override
    protected void lazyLoad() {

        if (mBinding != null) {
            getScoreRequest();
            getAdviceList(1, 10, false);
        }

    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取分数信息
     */
    public void getScoreRequest() {

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAdviceScore("805702", StringUtils.getJsonToString(new HashMap<>()));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<AdviceScoreModel>(mActivity) {
            @Override
            protected void onSuccess(AdviceScoreModel data, String SucMessage) {
                mBinding.scrollView.setVisibility(View.VISIBLE);
                sheShowScoreData(data);
            }

            @Override
            protected void onFinish() {

            }
        });


    }

    public void releaseRequest(int score) {

        Map<String, String> map = new HashMap<>();

        map.put("commenter", SPUtilHelpr.getUserId());
        map.put("score", score + "");

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("805400", StringUtils.getJsonToString(map));

        addCall(call);


        call.enqueue(new BaseResponseModelCallBack<CodeModel>(mActivity) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }

                if (!TextUtils.isEmpty(data.getCode())) {
                    UITipDialog.showSuccess(mActivity, "评分成功");
                }
            }

            @Override
            protected void onFinish() {
            }
        });

    }


    public void getAdviceList(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("isAccept", "2"); //0待采纳，1未采纳，2已采纳
        map.put("status", "AB");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getAdviceList("805405", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<AdviceListModel>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<AdviceListModel> data, String SucMessage) {
                adviceListAdapter.replaceData(data.getList());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 设置分数信息
     *
     * @param data
     */
    private void sheShowScoreData(AdviceScoreModel data) {
        mBinding.headerLayout.tvAverage.setText(data.getAverage() + "");
        if (data.getTotalCount() > 999) {
            mBinding.headerLayout.tvCount.setText("999+条建议");
        } else {
            mBinding.headerLayout.tvCount.setText(data.getTotalCount() + "条建议");
        }

        mBinding.headerLayout.progressBar1.setMax(data.getTotalCount());
        mBinding.headerLayout.progressBar2.setMax(data.getTotalCount());
        mBinding.headerLayout.progressBar3.setMax(data.getTotalCount());
        mBinding.headerLayout.progressBar4.setMax(data.getTotalCount());
        mBinding.headerLayout.progressBar5.setMax(data.getTotalCount());

        mBinding.headerLayout.progressBar1.setProgress(data.getYfCount());
        mBinding.headerLayout.progressBar2.setProgress(data.getLfCount());
        mBinding.headerLayout.progressBar3.setProgress(data.getSfCount());
        mBinding.headerLayout.progressBar4.setProgress(data.getSiCount());
        mBinding.headerLayout.progressBar5.setProgress(data.getWfCount());

    }

}
