package com.cdkj.myxb.module.user;

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
import com.cdkj.myxb.adapters.MyCommentListAdapter;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 我的评论列表
 * Created by cdkj on 2018/2/28.
 */

public class MyCommentsListFragment extends AbsRefreshListFragment {

    private String mType;
    private static final String TYPE = "producttype";

    /**
     * @param type 查看的评论类型
     * @return
     */
    public static MyCommentsListFragment getInstanse(String type) {
        MyCommentsListFragment fragment = new MyCommentsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
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
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        return new MyCommentListAdapter(listData);
    }

    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {
        if (TextUtils.isEmpty(mType)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", "AB"); //审核通过
        map.put("type", mType);
        map.put("commenter", SPUtilHelpr.getUserId());


        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentList("805425", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CommentListMode>>(mActivity) {

            @Override
            protected void onSuccess(ResponseInListModel<CommentListMode> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无评论", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
