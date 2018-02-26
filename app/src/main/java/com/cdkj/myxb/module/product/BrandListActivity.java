package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 并排列表
 * Created by 李先俊 on 2018/2/24.
 */

public class BrandListActivity extends CommonTablayoutActivity {

    private List<Fragment> fras;
    private List<String> titles;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BrandListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("品牌下单");
        mBaseBinding.titleView.setRightImg(R.drawable.search_waite);
        getBrandListBrand();
    }

    @Override
    public void topTitleViewRightClick() {
        BrandProductSearchActivity.open(this);
    }

    @Override
    public List<Fragment> getFragments() {
        return fras;
    }

    @Override
    public List<String> getFragmentTitles() {
        return titles;
    }

    /**
     * 获取品牌列表 TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
     */
    public void getBrandListBrand() {

        Map<String, String> map = new HashMap<>();

        map.put("status", "2");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandList("805258", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BrandListModel>(this) {
            @Override
            protected void onSuccess(List<BrandListModel> data, String SucMessage) {
                if (data.size() > 4) {
                    mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                } else {
                    mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
                }

                initData(data);

                initViewPager();

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void initData(List<BrandListModel> brandListModels) {
        fras = new ArrayList<>();
        titles = new ArrayList<>();

        for (int i = 0; i < brandListModels.size(); i++) {
            BrandListModel brandListModel =brandListModels.get(i);
            if (brandListModel == null) continue;
            fras.add(BrandProductListFragment.getInstanse(brandListModel.getCode(), i == 0));
            titles.add(brandListModel.getName());
        }
    }


}
