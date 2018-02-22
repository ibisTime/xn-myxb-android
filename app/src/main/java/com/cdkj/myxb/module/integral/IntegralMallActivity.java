package com.cdkj.myxb.module.integral;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityIntegralMallBinding;

/**
 * 积分商城
 * Created by 李先俊 on 2018/2/22.
 */

public class IntegralMallActivity extends AbsBaseLoadActivity {

    private ActivityIntegralMallBinding mBinding;

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_integral_mall, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.integral_mall));
    }
}
