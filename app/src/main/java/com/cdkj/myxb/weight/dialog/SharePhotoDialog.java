package com.cdkj.myxb.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DisplayHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.DialogIntegralChangeBinding;
import com.cdkj.myxb.databinding.DialogSharePhotoBinding;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.module.common.address.AddressListActivity;
import com.cdkj.myxb.module.integral.order.MyIntegralOrderActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * 分享图片
 * Created by cdkj on 2018/2/10.
 */

public class SharePhotoDialog extends Dialog {

    private DialogSharePhotoBinding mBinding;

    private String mPhotoUrl;

    protected CompositeDisposable mSubscription;

    public SharePhotoDialog(@NonNull Context context, String photourl) {
        super(context, R.style.TipsDialog);
        mPhotoUrl = photourl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_share_photo, null, false);
//        int screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int screenWidth = DisplayHelper.getScreenWidth(getContext());
        setContentView(mBinding.getRoot());
        getWindow().setLayout((int) (screenWidth * 0.9f), ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mSubscription = new CompositeDisposable();
        initListener();

        mSubscription.add(Observable.just(mPhotoUrl).map(s -> CodeUtils.createImage(s, 300, 300, null))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(bitmap -> bitmap != null)
                .map(bitmap -> {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    return bytes;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {
                    Glide.with(getContext())
                            .load(bytes)
                            .error(R.drawable.default_pic)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mBinding.imgSharePhoto);

                }, Throwable::printStackTrace));

    }

    private void initListener() {

        //取消
        mBinding.btnCancel.setOnClickListener(view -> {
            dismiss();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscription != null) {
            mSubscription.dispose();
        }
    }
}
