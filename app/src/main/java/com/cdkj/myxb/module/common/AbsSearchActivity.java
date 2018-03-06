package com.cdkj.myxb.module.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivitySearchBinding;
import com.cdkj.myxb.weight.SearchSaveUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.cdkj.myxb.module.appointment.CommonAppointmentListActivity.INTENTTYPE;

/**
 * 搜索 重写 getSaveKey() 设置保存名 重写 getSaveSize（） 设置保存大小
 * Created by cdkj on 2018/2/25.
 */

public abstract class AbsSearchActivity<T> extends AbsBaseLoadActivity {

    protected ActivitySearchBinding mSearchinding;

    protected List<String> searStrings = new ArrayList<>();

    protected RefreshHelper mRefreshHelper;


    /**
     * @param context
     * @param type
     */
    public static void open(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AbsSearchActivity.class);
        intent.putExtra(INTENTTYPE, type);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mSearchinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_search, null, false);
        mSearchinding.layoutRefresh.refreshLayout.setVisibility(View.GONE);
        return mSearchinding.getRoot();
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        initListener();

        initEditKeyPoard();

        initSearchInfo();

        initRefreshHelper();

    }

    abstract public RecyclerView.Adapter getListAdapter(List<T> listData);

    abstract public void searchRequest(String searchStr, int pageindex, int limit, boolean isShowDialog);


    /**
     * 获取搜索记录保存的key
     *
     * @return
     */
    public String getSaveKey() {
        return "AllSearch";
    }


    /**
     * 获取搜索记录保存大小
     *
     * @return
     */
    public int getSaveSize() {
        return 10;
    }


    /**
     * 刷新
     */
    private void initRefreshHelper() {

        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mSearchinding.layoutRefresh.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mSearchinding.layoutRefresh.recyclerView;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List listData) {
                return getListAdapter(listData);
            }

            @Override
            public void getListDataRequest(int pageindex, int limit, boolean isShowDialog) {
                searchRequest(mSearchinding.editSerchView.getText().toString(), pageindex, limit, isShowDialog);
            }
        });

        mRefreshHelper.init(10);

    }

    private void initListener() {
        mSearchinding.tvSearchCancel.setOnClickListener(view -> finish());
        mSearchinding.tvClear.setOnClickListener(view -> {
            clearSearchInfo();
        });
    }

    /**
     * 清除搜索信息
     */
    private void clearSearchInfo() {
        SearchSaveUtils.clearSearchInfo(getSaveKey());
        searStrings.clear();
        mSearchinding.linInfo.setVisibility(View.GONE);
        mSearchinding.flexboxLayoutSearch.removeAllViews();
    }


    /**
     * 设置输入键盘
     */
    private void initEditKeyPoard() {

        mSearchinding.editSerchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String str = v.getText().toString();

                if (TextUtils.isEmpty(str)) {
                    UITipDialog.showInfo(AbsSearchActivity.this, "请输入搜索内容");
                    return false;
                }

                startSearch(str);

                addSearchInfo(str);

                mSearchinding.linInfo.setVisibility(View.VISIBLE);

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
                        return JSON.parseArray(SearchSaveUtils.getSaveSearchInfo(getSaveKey()), String.class);
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
                        if (!searStrings.isEmpty()) {
                            mSearchinding.linInfo.setVisibility(View.VISIBLE);
                        }
                        initFlexBoxLayou();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    /**
     * 最多保存getSaveSize个搜索记录
     *
     * @param str
     */
    private void addSearchInfo(String str) {
        if (searStrings == null || searStrings.contains(str)) {
            return;
        }
        if (searStrings.size() >= getSaveSize()) {
            searStrings.remove(searStrings.size() - 1);
        }
        searStrings.add(0, str);
        SearchSaveUtils.saveSearchInfo(getSaveKey(), StringUtils.getJsonToString(searStrings));
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
            mSearchinding.flexboxLayoutSearch.addView(createNewFlexItemTextView(searString));
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

        mSearchinding.editSerchView.setText(search);
        mSearchinding.editSerchView.setSelection(search.length());

        mRefreshHelper.onDefaluteMRefresh(true);

        if (mSearchinding.linSearchHistory.getVisibility() == View.VISIBLE) {
            mSearchinding.linSearchHistory.setVisibility(View.GONE);
            mSearchinding.layoutRefresh.refreshLayout.setVisibility(View.VISIBLE);
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
