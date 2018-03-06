package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.CommentListAdapter;
import com.cdkj.myxb.databinding.LayoutFlexboxBinding;
import com.cdkj.myxb.databinding.LayoutRecyclerRefreshBinding;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.models.CommentTagModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 产品评价列表
 * Created by cdkj on 2018/2/24.
 */

public class ProductCommentListActivity extends AbsBaseLoadActivity {


    private RefreshHelper mRefreshHelper;

    private static final String PRODUCTCODE = "productcode";
    private static final String TYPE = "producttype";

    private String mProductCode;//产品编号
    private String mType;//评价类型

    private LayoutRecyclerRefreshBinding mBinding;
    private CommentListAdapter commentListAdapter;
    private LayoutFlexboxBinding mTagLayout;
    private List<TextView> textViews = new ArrayList<>();

    private String mTagString;//点击的标签文字

    private boolean isShowTagComment;//是否是标签评价


    /**
     * @param context
     * @param productCode 产品编号
     */
    public static void open(Context context, String productCode, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductCommentListActivity.class);
        intent.putExtra(PRODUCTCODE, productCode);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("评价");

        if (getIntent() != null) {
            mProductCode = getIntent().getStringExtra(PRODUCTCODE);
            mType = getIntent().getStringExtra(TYPE);
        }

        initRefreshHelper();

        getTagRequest();

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
                initCommentListAdapter(listData);
                return commentListAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                if (isShowTagComment) {
                    getCommentListByTag(mTagString, pageindex, limit, isShowDialog);
                } else {
                    getCommentList(pageindex, limit, isShowDialog);
                }
            }
        });

        mRefreshHelper.init(10);

    }

    /**
     * 获评价适配器
     *
     * @param
     * @return
     */
    @NonNull
    private void initCommentListAdapter(List listData) {
        commentListAdapter = new CommentListAdapter(listData);
        commentListAdapter.setHeaderAndEmpty(true);
        commentListAdapter.addHeaderView(getHeaderView());
    }

    /**
     * 获取评价列表
     */
    public void getCommentList(int start, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mProductCode) || TextUtils.isEmpty(mType)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("status", "AB"); //审核通过
        map.put("type", mType);
        map.put("entityCode", mProductCode);


        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentList("805425", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CommentListMode>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<CommentListMode> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无评价", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    public View getHeaderView() {
        mTagLayout = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_flexbox, null, false);

        return mTagLayout.getRoot();
    }


    /**
     * 获取评价tag
     */
    public void getTagRequest() {

        if (TextUtils.isEmpty(mProductCode) || TextUtils.isEmpty(mType)) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("entityCode", mProductCode);
        map.put("kind", mType);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentTag("805427", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseListCallBack<CommentTagModel>(this) {
            @Override
            protected void onSuccess(List<CommentTagModel> data, String SucMessage) {
                initTagViews(data);
            }

            @Override
            protected void onFinish() {

            }
        });


    }


    /**
     * 新增tag
     *
     * @param data
     */
    private void initTagViews(List<CommentTagModel> data) {
        for (CommentTagModel tagModel : data) {
            TextView tv = createNewFlexItemTextView(tagModel.getWord() + " (" + tagModel.getCount() + ")");
            textViews.add(tv);
            mTagLayout.flexboxLayout.addView(tv);
        }
    }

    /**
     * 动态创建TextView
     *
     * @param
     * @return
     */
    private TextView createNewFlexItemTextView(String str) {
        final TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(str);
        textView.setTextSize(12);
        textView.setTextColor(ContextCompat.getColor(this, R.color.comment_tag));
        textView.setBackgroundResource(R.drawable.comment_tag_bg);
        textView.setTag(str);

        textView.setOnClickListener(view -> {
            isShowTagComment = true;
            mTagString = textView.getText().toString();
            mRefreshHelper.onDefaluteMRefresh(true);

            for (TextView textView1 : textViews) {
                textView1.setTextColor(ContextCompat.getColor(this, R.color.comment_tag));
                textView1.setBackgroundResource(R.drawable.comment_tag_bg);
            }
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            textView.setBackgroundResource(R.drawable.comment_tag_bg_click);
        });

        int padding = DisplayHelper.dip2px(this, 3);
        int paddingLeftAndRight = DisplayHelper.dip2px(this, 12);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DisplayHelper.dip2px(this, 5);
        int marginTop = DisplayHelper.dip2px(this, 5);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }


    private void getCommentListByTag(String tag, int start, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mProductCode) || TextUtils.isEmpty(mType) || TextUtils.isEmpty(tag)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", start + "");
        map.put("status", "AB"); //审核通过
        map.put("type", mType);
        map.put("entityCode", mProductCode);
        map.put("keyWord", tag);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCommentList("805428", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CommentListMode>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<CommentListMode> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无评价", 0);
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
