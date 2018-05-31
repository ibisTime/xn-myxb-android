package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.ShopCartListAdapter;
import com.cdkj.myxb.databinding.ActivityShopCartListBinding;
import com.cdkj.myxb.models.ShopCartListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/4.
 */

public class ShopCartListActivity extends AbsBaseLoadActivity {

    private static boolean isAllChoice = false;

    private ActivityShopCartListBinding mBinding;

    private RefreshHelper mRefreshHelper;

    private ShopCartListAdapter shopCartListAdapter;


    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShopCartListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_cart_list, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("购物车");

        /*防止局部刷新闪烁*/
        ((DefaultItemAnimator) mBinding.rvProduct.getItemAnimator()).setSupportsChangeAnimations(false);

        initListener();
        initRefreshHelper(10);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBinding != null){
            mRefreshHelper.onDefaluteMRefresh(true);
        }
    }

    private void initListener() {
        mBinding.llAll.setOnClickListener(view -> {
            if (isAllChoice){
                mBinding.ivChoice.setBackgroundResource(R.drawable.pay_unchoose);
            }else {
                mBinding.ivChoice.setBackgroundResource(R.drawable.pay_choose);
            }

            isAllChoice = !isAllChoice;
            setIsAllChoice();
        });

        mBinding.tvConfirm.setOnClickListener(view -> {

            List<ShopCartListModel> mShopCartList = new ArrayList<>();

            for (ShopCartListModel cartListModel : shopCartListAdapter.getData()){

                if (cartListModel.isChoice()){
                    mShopCartList.add(cartListModel);
                }

            }

            if (mShopCartList.size() == 0){
                ToastUtil.show(this,"请选择商品");
                return;
            }

            ShopCartOrderActivity.open(this, mShopCartList, mBinding.tvAmount.getText().toString());

        });
    }

    /**
     * 初始化刷新相关
     */
    protected void initRefreshHelper(int limit) {
        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack(this) {
            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public void onRefresh(int pageindex, int limit) {
                super.onRefresh(pageindex, limit);
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.rvProduct;
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

    public ShopCartListAdapter getListAdapter(List listData) {
        shopCartListAdapter = new ShopCartListAdapter(listData,false);

        shopCartListAdapter.setOnItemClickListener((adapter, view, position) -> {

        });

        shopCartListAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            ShopCartListModel model = shopCartListAdapter.getItem(position);
            int quantity = model.getQuantity();

            switch (view.getId()){
                case R.id.iv_choice:

                    model.setChoice(!model.isChoice());
                    shopCartListAdapter.notifyItemChanged(position);

                    calculateTotalAmount();

                    break;

                case R.id.iv_delete:

                    showDoubleWarnListen("您确定要删除该商品吗？", view1 -> {
                        delete(model,position);
                    });

                    break;

                case R.id.ll_sub:

                    if (quantity == 1)
                        return;

                    modify(position, quantity-1);
                    break;

                case R.id.ll_add:

                    modify(position, quantity+1);

                    break;

            }

        });

        return shopCartListAdapter;
    }

    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        Map<String, String> map = RetrofitUtils.getRequestMap();
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("orderColumn", "");
        map.put("orderDir", "");
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getShopCartList("805295", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<ShopCartListModel>>(this) {


            @Override
            protected void onSuccess(ResponseInListModel<ShopCartListModel> data, String SucMessage) {

                mRefreshHelper.setData(data.getList(), "暂无购物车商品", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void delete(ShopCartListModel model, int position) {
        List<String> cartCodeList = new ArrayList<>();
        cartCodeList.add(model.getCode());

        Map<String, Object> object = RetrofitUtils.getRequestMap();
        object.put("cartCodeList", cartCodeList);
        object.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805291", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data != null && data.isSuccess()) {
                    UITipDialog.showSuccess(ShopCartListActivity.this, getString(R.string.do_success));

                    shopCartListAdapter.getData().remove(position);
                    shopCartListAdapter.notifyDataSetChanged();
                    calculateTotalAmount();
                } else {
                    UITipDialog.showFall(ShopCartListActivity.this, getString(R.string.do_fail));
                }

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ShopCartListActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void modify(int position, int quantity) {

        ShopCartListModel model = shopCartListAdapter.getItem(position);

        Map<String, Object> object = RetrofitUtils.getRequestMap();
        object.put("code", model.getCode());
        object.put("quantity", quantity);
        object.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805292", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data != null && data.isSuccess()) {

                    model.setQuantity(quantity);
                    shopCartListAdapter.notifyItemChanged(position);

                    calculateTotalAmount();

                } else {
                    UITipDialog.showFall(ShopCartListActivity.this, getString(R.string.do_fail));
                }

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFall(ShopCartListActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void calculateTotalAmount(){

        BigDecimal amount = new BigDecimal(0);

        for (ShopCartListModel model : shopCartListAdapter.getData()){

            if (model.isChoice()){
                if (model.getProduct().getPrice() != null)
                    amount = amount.add(model.getProduct().getPrice().multiply(new BigDecimal(model.getQuantity())));
            }

        }

        mBinding.tvAmount.setText(MoneyUtils.getShowPriceSign(amount));

    }

    private void setIsAllChoice(){
        Log.e("isAllChoice",isAllChoice+"");

        for (ShopCartListModel model : shopCartListAdapter.getData()){
            model.setChoice(isAllChoice);
        }

        calculateTotalAmount();

        shopCartListAdapter.notifyDataSetChanged();
    }
}
