package com.cdkj.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.utils.glidetransforms.GlideCircleBorderTransform;
import com.cdkj.baselibrary.utils.glidetransforms.GlideCircleTransform;

/**
 * 图片加载工具类
 * Created by Administrator on 2016-09-14.
 */
public class ImgUtils {

    public static void loadQiniuImg(Object obj, String imgid, ImageView img) {
        loadImg(obj, MyCdConfig.QINIUURL + imgid, img);
    }

    public static void loadQiniuLogo(Object obj, String imgid, ImageView img) {
        loadLogo(obj, MyCdConfig.QINIUURL + imgid, img);
    }

    public static void loadImg(Object obj, Object imgid, ImageView img) {

        LogUtil.E("图片" + imgid);

        if (imgid instanceof Integer || imgid instanceof String) {

            if (obj instanceof Activity) {

                if (!AppUtils.isActivityExist((Activity) obj)) {

                    LogUtil.E("图片加载界面销毁");
                    return;
                }
                if (obj == null || img == null) {
                    return;
                }
                try {
                    Glide.with((Activity) obj).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }

            } else if (obj instanceof Fragment) {
                try {
                    Glide.with((Fragment) obj).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            } else if (obj instanceof Context) {
                try {
                    Glide.with((Context) obj).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            }
        }
    }

    public static void loadMainTabImg(Object obj, Object imgid, ImageView img) {

        LogUtil.E("图片" + imgid);

        if (imgid instanceof Integer || imgid instanceof String) {

            if (obj instanceof Activity) {

                if (!AppUtils.isActivityExist((Activity) obj)) {

                    LogUtil.E("图片加载界面销毁");
                    return;
                }
                if (obj == null || img == null) {
                    return;
                }
                try {
                    Glide.with((Activity) obj).load(MyCdConfig.QINIUURL + imgid).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }

            } else if (obj instanceof Fragment) {
                try {
                    Glide.with((Fragment) obj).load(MyCdConfig.QINIUURL + imgid).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            } else if (obj instanceof Context) {
                try {
                    Glide.with((Context) obj).load(MyCdConfig.QINIUURL + imgid).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            }
        }
    }


    public static void loadImgNoPlaceholder(Object obj, Object imgid, ImageView img) {

        if (imgid instanceof Integer || imgid instanceof String) {

            if (obj instanceof Activity) {

                if (!AppUtils.isActivityExist((Activity) obj)) {

                    LogUtil.E("图片加载界面销毁");
                    return;
                }
                if (obj == null || img == null) {
                    return;
                }
                try {
                    Glide.with((Activity) obj).load(imgid).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }

            } else if (obj instanceof Fragment) {
                try {
                    Glide.with((Fragment) obj).load(imgid).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            } else if (obj instanceof Context) {
                try {
                    Glide.with((Context) obj).load(imgid).error(R.drawable.default_pic).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            }
        }
    }


    public static void loadLogo(Object obj, Object imgid, ImageView img) {

        if (imgid instanceof Integer || imgid instanceof String) {

            if (obj instanceof Context) {
                try {
                    Glide.with((Context) obj).load(imgid).placeholder(R.drawable.photo_default).error(R.drawable.photo_default).transform(new GlideCircleTransform(((Context) obj))).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            } else if (obj instanceof Activity) {

                if (!AppUtils.isActivityExist((Activity) obj)) {

                    LogUtil.E("图片加载界面销毁");
                    return;
                }
                if (obj == null || img == null) {
                    return;
                }
                try {
                    Glide.with((Activity) obj).load(imgid).placeholder(R.drawable.photo_default).error(R.drawable.photo_default).transform(new GlideCircleTransform(((Activity) obj))).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }

            } else if (obj instanceof Fragment) {
                try {
                    Glide.with((Fragment) obj).load(imgid).placeholder(R.drawable.photo_default).error(R.drawable.photo_default).transform(new GlideCircleTransform(((Fragment) obj).getContext())).into(img);
                } catch (Exception e) {
                    LogUtil.E("图片加载错误");
                }
            }
        }

    }


    public static void loadActImgListener(Activity context, String imgid, ImageView img, RequestListener<String, GlideDrawable> listener) {

        if (!AppUtils.isActivityExist(context)) {

            LogUtil.E("图片加载界面销毁");
            return;
        }

        if (context == null || img == null) {
            return;
        }

        LogUtil.E("图片" + imgid);

        try {
            Glide.with(context).load(imgid).error(R.drawable.default_pic).listener(listener).into(img);
        } catch (Exception e) {
            LogUtil.E("图片加载错误");
        }

    }


    public static void loadBankBg(Context context, int imgid, ImageView img) {

        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.back_default).error(R.drawable.back_default).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadBankLogo(Context context, int imgid, ImageView img) {

        if (context == null || img == null || imgid == 0) {
            return;
        }
        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.back_logo_defalut).error(R.drawable.back_logo_defalut).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadQiNiuBorderLogo(Context context, String url, ImageView imageView, @ColorRes int borderColor) {
        try {
/*.skipMemoryCache(true)   .diskCacheStrategy(DiskCacheStrategy.NONE)*/
            Glide.with(context).load(MyCdConfig.QINIUURL + url).error(R.drawable.photo_default).transform(new GlideCircleBorderTransform(context, 2, ContextCompat.getColor(context, borderColor))).into(imageView);

        } catch (Exception e) {

        }
    }


    /**
     * 用于判断链接是否添加了七牛
     *
     * @param url
     * @return
     */
    public static boolean isHaveHttp(String url) {
        if (TextUtils.isEmpty(url)) return false;
        return url.indexOf("http:") != -1;
    }


}
