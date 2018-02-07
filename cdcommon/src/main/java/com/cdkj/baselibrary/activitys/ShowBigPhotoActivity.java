package com.cdkj.baselibrary.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.databinding.ActivityPhotoViewpagerBinding;
import com.cdkj.baselibrary.databinding.ActivityShowBigphotoBinding;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示大图
 * Created by cdkj on 2017/10/24.
 */

public class ShowBigPhotoActivity extends AbsBaseLoadActivity {

    private ActivityShowBigphotoBinding mbinding;

    private String url;

    public static void open(Context context, String url) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShowBigPhotoActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mbinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_show_bigphoto, null, false);
        return mbinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("查看大图");

        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
        }

        ImgUtils.loadImg(this, url, mbinding.photoview);

    }


}
