package com.cdkj.myxb.module.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityResultBinding;

/**
 * Created by cdkj on 2018/5/3.
 */

public class ResultsActivity extends AbsBaseLoadActivity {

    private ActivityResultBinding mBinding;

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_result, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
