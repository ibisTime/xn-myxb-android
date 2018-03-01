package com.cdkj.myxb.weight;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.myxb.models.FirstPageBanner;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by cdkj on 2017/6/10.
 */

public class FirstBannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        FirstPageBanner firstPageBanner = (FirstPageBanner) path;
        if (firstPageBanner == null) return;

        //Glide 加载图片简单用法
        Glide.with(context).load(MyCdConfig.QINIUURL + firstPageBanner.getPic()).error(com.cdkj.baselibrary.R.drawable.default_pic).into(imageView);
    }
}