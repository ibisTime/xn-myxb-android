package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommentListAdapter;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 产品评论列表
 * Created by cdkj on 2018/2/24.
 */

public class ProductCommentListActivity extends AbsBaseLoadActivity {

    private RefreshHelper mRefreshHelper;

    private static final String PRODUCTCODE = "productcode";

    private String mProductCode;//产品编号

    private LayoutRecyclerRefreshBinding mBinding;


    /**
     * @param context
     * @param productCode 产品编号
     */
    public static void open(Context context, String productCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductCommentListActivity.class);
        intent.putExtra(PRODUCTCODE, productCode);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("商品评论");

        if (getIntent() != null) {
            mProductCode = getIntent().getStringExtra(PRODUCTCODE);
        }

        initRefreshHelper();

        mRefreshHelper.onDefaluteMRefresh(true);

    }

    private void initRefreshHelper() {

        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
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
                return new CommentListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getCommentList(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);

    }

    /**
     * 获取评论列表
     */
    public void getCommentList(int start, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mProductCode)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("status", "AB"); //审核通过
        map.put("type", "P");
        map.put("entityCode", mProductCode);


        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentList("805425", StringUtils.getJsonToString(map));

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CommentListMode>>(this) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
