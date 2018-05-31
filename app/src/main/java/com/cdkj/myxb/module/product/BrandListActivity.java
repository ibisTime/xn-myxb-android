package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.FirstPageBrandAdapter;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/12.
 */

public class BrandListActivity extends AbsRefreshListActivity {

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BrandListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        FirstPageBrandAdapter firstPageBrandAdapter = new FirstPageBrandAdapter(listData);

        firstPageBrandAdapter.setOnItemClickListener((adapter, view, position) -> {

            BrandListModel brandListModel = firstPageBrandAdapter.getItem(position);

            if (brandListModel == null) return;

            EventBus.getDefault().post(brandListModel); //结束所有界面
            finish();

        });

        return firstPageBrandAdapter;
    }

    /**
     * 获取品牌列表 TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
     * <p>
     * 0
     */
    @Override
    public void getListRequest(int pageindex, int limit, boolean isShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("status", "2");
        map.put("limit", limit + "");
        map.put("start", pageindex + "");
        map.put("orderColumn", "order_no");
        map.put("orderDir", "asc");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getSpeBrandList("805256", StringUtils.getJsonToString(map));


        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<BrandListModel>>(this) {

            @Override
            protected void onSuccess(ResponseInListModel<BrandListModel> data, String SucMessage) {

                mRefreshHelper.setData(data.getList(), getString(R.string.no_recommended_brand), 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("选择品牌");

        initRefreshHelper(10);

        mRefreshHelper.onDefaluteMRefresh(true);
        mRefreshHelper.setLayoutManager(new ScrollGridLayoutManager(BrandListActivity.this, 2));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRefreshHelper.onDefaluteMRefresh(true);
    }
}
