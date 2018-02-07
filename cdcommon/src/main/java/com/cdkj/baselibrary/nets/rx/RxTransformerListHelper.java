package com.cdkj.baselibrary.nets.rx;

import android.content.Context;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.nets.ErrorVerify;
import com.cdkj.baselibrary.nets.NetHelper;
import com.cdkj.baselibrary.nets.NetUtils;
import com.cdkj.baselibrary.nets.SimpleErrorVerify;
import com.cdkj.baselibrary.utils.LogUtil;

import java.util.List;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.cdkj.baselibrary.nets.NetHelper.NET_ERROR;
import static com.cdkj.baselibrary.nets.NetHelper.REQUESTFECODE4;
import static com.cdkj.baselibrary.nets.NetHelper.REQUESTOK;


/**
 * Retrofit帮助类
 */
public class RxTransformerListHelper {


    /**
     * 简单的请求业务错误过滤器（弹出toast）
     */
    private static Predicate<BaseResponseListModel> verifySimpleBusiness(Context context) {
        return verifyBusiness(new SimpleErrorVerify(context));
    }

    /**
     * 业务错误过滤器（自定义）
     */
    private static <T> Predicate<T> verifyBusiness(ErrorVerify errorVerify) {
        return response -> {
            if (response instanceof BaseResponseListModel) {
                BaseResponseListModel baseResponse = (BaseResponseListModel) response;
                String responseCode = baseResponse.getErrorCode();
                boolean isSuccess = REQUESTOK.equals(responseCode);
                if (!isSuccess) {
                    if (errorVerify != null) {
                        errorVerify.call(baseResponse.getErrorCode(), baseResponse.getErrorInfo());
                    }

                }
                LogUtil.E("网络请求" + isSuccess + responseCode);
                return isSuccess;
            } else {
                return false;
            }
        };
    }

    /**
     * 非空过滤器（自定义）
     */
    private static <T> Predicate<T> verifyNotEmpty() {
        return response -> response != null;
    }

    /**
     * 错误提醒处理
     *
     * @param context
     * @param errorVerify
     * @param <T>
     * @return
     */
    private static <T> Function<Throwable, T> doError(Context context, ErrorVerify errorVerify) {
        return throwable -> {
            if (context instanceof BaseActivity)
                ((BaseActivity) context).disMissLoading();
            throwable.printStackTrace();
            errorVerify.call("0", throwable.toString());
            if (errorVerify != null) {
                if (!NetUtils.isNetworkConnected(context)) {
                    errorVerify.call(NET_ERROR, "暂无网络");

                } else {
                    if (LogUtil.isDeBug) {
                        errorVerify.call("0", throwable.toString());
                    } else {
                        errorVerify.call("0", "未知错误");
                    }
                }
            }
            return null;
        };
    }

    /**
     * sessionId 过期的过滤器
     */
    private static <T> Predicate<T> verifyResultCode(Context context) {
        return response -> {
            if (response instanceof BaseResponseListModel) {
                BaseResponseListModel baseResponse = (BaseResponseListModel) response;
                String state = baseResponse.getErrorCode();
                if (REQUESTFECODE4.equals(state)) {
                    NetHelper.onLoginFailure(context, baseResponse.getErrorInfo());
                    return false;
                }
                return true;
            } else {
                return false;
            }
        };
    }

    /**
     * 优先使用这个，可以继续使用操作符
     */
    private static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 聚合了session过滤器,业务过滤器及合并操作 自定义错误回调
     */
    public static <T> ObservableTransformer<T, T>
    applySchedulersAndAllFilter(Context context, ErrorVerify errorVerify) {
        return observable -> observable
                .compose(applySchedulers())
                .doOnSubscribe(disposable -> {
                    if (context instanceof BaseActivity)
                        ((BaseActivity) context).showLoadingDialog();
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //指定 doOnSubscribe工作线程
                .filter(verifyNotEmpty())
                .filter(verifyResultCode(context))
                .filter(verifyBusiness(errorVerify))
                .onErrorReturn(doError(context, errorVerify))
                .doOnError(throwable -> {
                    if (context instanceof BaseActivity)
                        ((BaseActivity) context).disMissLoading();
                })
                .doOnComplete(() -> {
                    if (context instanceof BaseActivity)
                        ((BaseActivity) context).disMissLoading();
                });
    }
    /**
     * 聚合了session过滤器,简单业务过滤器及合并操作及自定义的错误返回
     */
    public static <T> ObservableTransformer<BaseResponseListModel<T>, List<T>>
    applySchedulersResult(Context context, ErrorVerify errorVerify) {
        return observable -> observable
                .compose(applySchedulersAndAllFilter(context, errorVerify))
                .map(t -> t.getData());
    }

    /**
     * 聚合了session过滤器,简单业务过滤器及合并操作
     * 结果直接返回包含BaseModelNew<T>里定义的 T 对象
     */
    public static <T> ObservableTransformer<BaseResponseListModel<T>, List<T>> applySchedulerResult(Context context) {
        return applySchedulersResult(context, new SimpleErrorVerify(context));
    }

    /**
     * 聚合了session过滤器,简单业务过滤器及合并操作
     * 结果返回包含BaseModelNew的对象
     */
    public static <T> ObservableTransformer<T, T> applySchedulerAndAllFilter(Context context) {

        return applySchedulersAndAllFilter(context, new SimpleErrorVerify(context));
    }
}

