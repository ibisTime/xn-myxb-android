package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.models.BrandListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体品牌列表
 * Created by cdkj on 2018/2/24.
 */

public class SpecificBrandListActivity extends CommonTablayoutActivity {

    private List<Fragment> fras;
    private List<String> titles;

    private BrandListModel mBrandListModel;

    private static final String BRANDLISTMODEL="brmodel";


    /**
     * @param context
     * @param brandListModel
     */
    public static void open(Context context, BrandListModel brandListModel) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SpecificBrandListActivity.class);
        intent.putExtra(BRANDLISTMODEL, brandListModel);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mTabLayoutBinding.tablayout.setVisibility(View.GONE);
        mBaseBinding.titleView.setRightImg(R.drawable.search_waite);

        if(getIntent()!=null){
            mBrandListModel=getIntent().getParcelableExtra(BRANDLISTMODEL);
        }

        mBaseBinding.titleView.setMidTitle(mBrandListModel.getName());

        initData();

        initViewPager();
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


    private void initData() {
        fras = new ArrayList<>();
        titles = new ArrayList<>();

        if (mBrandListModel == null)
            return;

        fras.add(BrandProductListFragment.getInstance(mBrandListModel.getCode(), mBrandListModel.getDescription(),true, false));
        titles.add("..");
    }


}
