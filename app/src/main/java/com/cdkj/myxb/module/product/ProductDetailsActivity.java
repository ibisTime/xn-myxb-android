package com.cdkj.myxb.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.DeviceHelper;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityProductDetailsBinding;
import com.cdkj.myxb.weight.views.MyScrollView;

/**
 * 产品详情
 * Created by cdkj on 2018/2/10.
 */

public class ProductDetailsActivity extends AbsBaseLoadActivity {

    private ActivityProductDetailsBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_product_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        initTopTitleAlphaChange();

        initListener();

    }

    private void initListener() {

        mBinding.fraLayoutTitleLeft.setOnClickListener(view -> finish());
        mBinding.fraLayoutTitleLeftImg.setOnClickListener(view -> finish());



    }

    /**
     * 顶部title 透明度渐变
     */
    private void initTopTitleAlphaChange() {

        mBinding.fraLayoutTitle.setAlpha(0);
        mBinding.fraLayoutTitleLeftImg.setAlpha(1);

        mBinding.scrollViewProduct.setOnScrollListener(new MyScrollView.MyOnScrollListener() {
            @Override
            public void onScroll(int y) {
            }

            @Override
            public void onCurrentY(int y) {
                //设置默认透明度
                float titleLayoutAlpha = 0;
                float titleImageAlpha = 0;
                //渐变滑动距离 banner高度 - title高度
                int baseHeight = getResources().getDimensionPixelOffset(R.dimen.product_details_banner_height) - getResources().getDimensionPixelOffset(R.dimen.title_height);
                if (y <= 0) {   //到顶部时
                    titleLayoutAlpha = 0;
                    titleImageAlpha = 1;
                } else {
                    titleLayoutAlpha = y / (baseHeight * 1.0f);
                    titleImageAlpha = 1 - titleLayoutAlpha;
                }
                mBinding.fraLayoutTitle.setAlpha(titleLayoutAlpha);
                mBinding.fraLayoutTitleLeftImg.setAlpha(titleImageAlpha);
            }
        });
    }
}
