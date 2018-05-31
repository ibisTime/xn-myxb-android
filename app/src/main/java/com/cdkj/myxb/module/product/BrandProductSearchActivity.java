package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.MyDividerItemDecoration;
import com.cdkj.myxb.adapters.BrandProductListAdapter;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsSearchActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 品牌搜索
 * Created by cdkj on 2018/2/25.
 */

public class BrandProductSearchActivity extends AbsSearchActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BrandProductSearchActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);

        mSearchinding.layoutRefresh.recyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        BrandProductListAdapter brandProductListAdapter = new BrandProductListAdapter(listData);

        brandProductListAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandProductModel brandProductModel = brandProductListAdapter.getItem(position);

            if (brandProductListAdapter == null) return;

            ProductDetailsActivity.open(BrandProductSearchActivity.this, brandProductModel.getCode());

        });

        return brandProductListAdapter;
    }

    @Override
    public void searchRequest(String searchStr, int pageindex, int limit, boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("name", searchStr);
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("status", "2");//TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandProductList("805266", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<BrandProductModel>>(this) {


            @Override
            protected void onSuccess(ResponseInListModel<BrandProductModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无搜索产品", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
