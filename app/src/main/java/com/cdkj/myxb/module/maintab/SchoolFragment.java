package com.cdkj.myxb.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.FragmentSchoolBinding;

import static com.cdkj.myxb.MainActivity.TITLE;

/**
 * Created by cdkj on 2018/5/3.
 */

public class SchoolFragment extends BaseLazyFragment {

    private FragmentSchoolBinding mBinding;

    private String title;

    public static SchoolFragment getInstance(String title) {
        SchoolFragment fragment = new SchoolFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_school, null, false);

        init();

        return mBinding.getRoot();

    }

    private void init() {
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);

            mBinding.tvTitle.setText(title);
        }


    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }
}
