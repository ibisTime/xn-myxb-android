package com.cdkj.myxb.module.appointment;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.BrandListAdapter;
import com.cdkj.myxb.adapters.CommonAppointmentListAdapter;
import com.cdkj.myxb.databinding.ActivitySearchBinding;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.product.ProductDetailsActivity;
import com.cdkj.myxb.module.user.UserHelper;
import com.cdkj.myxb.weight.SearchSaveUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

import static com.cdkj.myxb.module.appointment.CommonAppointmentListActivity.INTENTTYPE;

/**
 * 预约搜索
 * Created by cdkj on 2018/2/25.
 */

public class AppointmentSearchActivity extends AbsBaseLoadActivity {

    private ActivitySearchBinding mBinding;

    private List<String> searStrings = new ArrayList<>();

    private RefreshHelper mRefreshHelper;

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
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_search, null, false);
        mBinding.layoutRefresh.refreshLayout.setVisibility(View.GONE);
        return mBinding.getRoot();
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mType = getIntent().getStringExtra(INTENTTYPE);
        }

        initListener();

        initEditKeyPoard();

        initSearchInfo();

        initRefreshHelper();

    }

    private void initRefreshHelper() {

        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.layoutRefresh.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.layoutRefresh.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                CommonAppointmentListAdapter shopperListAdapter = new CommonAppointmentListAdapter(listData);
                shopperListAdapter.setOnItemClickListener((adapter, view, position) -> {

                    if (shopperListAdapter.getItem(position) == null) return;

                    CommonAppointmentUserDetailActivity.open(AppointmentSearchActivity.this, shopperListAdapter.getItem(position).getUserId(), mType);
                });
                return shopperListAdapter;
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                getSearchListRequest(pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);

    }

    private void initListener() {
        mBinding.tvSearchCancel.setOnClickListener(view -> finish());
    }


    public void getSearchListRequest(int pageindex, int limit, boolean isShowDialog) {

        if (TextUtils.isEmpty(mType)) return;

        Map map = RetrofitUtils.getRequestMap();
        map.put("kind", mType);
        map.put("start", pageindex + "");
        map.put("limit", limit + "");
        map.put("status", "0");//上架
        map.put("userName", mBinding.editSerchView.getText().toString());//上架
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


    /**
     * 获取数据适配器
     *
     * @param listData
     * @return
     */
    @NonNull
    private BrandListAdapter getBrandListAdapter(List listData) {

        BrandListAdapter brandListAdapter = new BrandListAdapter(listData);

        brandListAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandProductModel brandProductModel = brandListAdapter.getItem(position);

            if (brandListAdapter == null) return;

            ProductDetailsActivity.open(AppointmentSearchActivity.this, brandProductModel.getCode());

        });

        return brandListAdapter;
    }

    /**
     * 设置输入键盘
     */
    private void initEditKeyPoard() {

        mBinding.editSerchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String str = v.getText().toString();

                if (TextUtils.isEmpty(str)) {
                    UITipDialog.showInfo(AppointmentSearchActivity.this, "请输入搜索内容");
                    return false;
                }

                startSearch(str);

                addSearchInfo(str);

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);//隐藏键盘
                    return true;
                }
                return false;
            }
        });
    }


    private void initSearchInfo() {
        //解析搜索信息
        mSubscription.add(Observable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                        return JSON.parseArray(SearchSaveUtils.getSaveSearchInfo(), String.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mBaseBinding.contentView.showContent(true);
                    }
                })
                .filter(new Predicate<List<String>>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull List<String> strings) throws Exception {
                        return strings != null;
                    }
                })
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        searStrings = strings;
                        initFlexBoxLayou();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    /**
     * 最多保存10个搜索记录
     *
     * @param str
     */
    private void addSearchInfo(String str) {
        if (searStrings == null || searStrings.contains(str)) {
            return;
        }
        if (searStrings.size() >= 10) {
            searStrings.remove(searStrings.size() - 1);
        }
        searStrings.add(0, str);
        SearchSaveUtils.saveSearchInfo(StringUtils.getJsonToString(searStrings));
    }

    /**
     * 动态添加ziVIew
     */
    private void initFlexBoxLayou() {
        if (searStrings == null || searStrings.isEmpty()) {
            return;
        }
        for (String searString : searStrings) {
            if (TextUtils.isEmpty(searString)) return;
            mBinding.flexboxLayoutSearch.addView(createNewFlexItemTextView(searString));
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
        textView.setTextSize(14);
        textView.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        textView.setBackgroundResource(R.drawable.search_tx_gray);
        textView.setTag(str);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearch(str);
            }
        });
        int padding = DisplayHelper.dip2px(this, 5);
        int paddingLeftAndRight = DisplayHelper.dip2px(this, 15);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DisplayHelper.dip2px(this, 5);
        int marginTop = DisplayHelper.dip2px(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }


    /**
     * @param search 内容
     */
    private void startSearch(String search) {

        if (TextUtils.isEmpty(search)) {
            UITipDialog.showInfo(this, "请输入搜索内容");
            return;
        }

        mBinding.editSerchView.setText(search);
        mBinding.editSerchView.setSelection(search.length());

        mRefreshHelper.onDefaluteMRefresh(true);

        if (mBinding.linSearchHistory.getVisibility() == View.VISIBLE) {
            mBinding.linSearchHistory.setVisibility(View.GONE);
            mBinding.layoutRefresh.refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHelper != null) {
            mRefreshHelper.onDestroy();
        }
    }
}
