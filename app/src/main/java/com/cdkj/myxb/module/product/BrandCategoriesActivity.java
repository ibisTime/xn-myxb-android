package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.adapters.TablayoutAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityBrandCategoriesListBinding;
import com.cdkj.myxb.models.BrandCategoriesListModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 并排列表
 * Created by cdkj on 2018/2/24.
 */

public class BrandCategoriesActivity extends AbsBaseLoadActivity {

    private ActivityBrandCategoriesListBinding mBinding;

    private List<Fragment> fras;
    private List<String> titles;

    /*Tablayout 适配器*/
    protected TablayoutAdapter tablayoutAdapter;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BrandCategoriesActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_brand_categories_list, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("品牌下单");
        mBaseBinding.titleView.setRightImg(R.drawable.search_waite);

        initListener();
        getBrandListBrand();
    }

    private void initListener() {
        mBinding.btnCart.setOnClickListener(view -> {
            ShopCartListActivity.open(this);
        });
    }

    @Override
    public void topTitleViewRightClick() {
        BrandProductSearchActivity.open(this);
    }

    /**
     * 获取品牌列表 TO_Shelf("1", "未上架"), Shelf_YES("2", "已上架"), Shelf_NO("3", "已下架");
     */
    public void getBrandListBrand() {

        Map<String, String> map = new HashMap<>();

        map.put("status", "1"); // 0 待上架 1 已上架 2 已下架
        map.put("orderColumn", "order_no");
        map.put("orderDir", "asc");
        map.put("start", "1");
        map.put("limit", "10");


        Call call = RetrofitUtils.createApi(MyApiServer.class).getBrandCategoriesList("805247", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BrandCategoriesListModel>(this) {
            @Override
            protected void onSuccess(List<BrandCategoriesListModel> data, String SucMessage) {
//                if (data.size() > 4) {
//                    mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//                } else {
//                    mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
//                }

                initData(data);

                initViewPager();

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void initData(List<BrandCategoriesListModel> brandListModels) {
        fras = new ArrayList<>();
        titles = new ArrayList<>();

        for (int i = 0; i < brandListModels.size(); i++) {
            BrandCategoriesListModel brandListModel = brandListModels.get(i);

            if (brandListModel == null)
                continue;
            fras.add(BrandProductListFragment.getInstance(brandListModel.getCode(), brandListModel.getDescription(), i == 0, true));
            titles.add(brandListModel.getName());
        }
    }

    protected void initViewPager() {

        tablayoutAdapter = new TablayoutAdapter(getSupportFragmentManager());

        List<Fragment> mFragments = fras;
        List<String> mTitles = titles;

        tablayoutAdapter.addFrag(mFragments, mTitles);

        mBinding.viewpager.setAdapter(tablayoutAdapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpager);        //viewpager和tablayout关联
        mBinding.viewpager.setOffscreenPageLimit(tablayoutAdapter.getCount());
        mBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mBinding.viewpager.setOffscreenPageLimit(3);
    }


}
