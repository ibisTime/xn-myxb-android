package com.cdkj.myxb.weight.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.TripDateAdapter;
import com.cdkj.myxb.databinding.LayoutShopperTripDateBinding;
import com.cdkj.myxb.weight.dialog.TripTimeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 行程日历
 * Created by cdkj on 2018/2/9.
 */

public class TripDateView extends LinearLayout {

    private LayoutShopperTripDateBinding mBinding;

    protected CompositeDisposable mSubscription;

    private TripDateAdapter tripDateAdapter;

    public TripDateView(Context context) {
        this(context, null);
    }

    public TripDateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TripDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_shopper_trip_date, this, true);
        mSubscription = new CompositeDisposable();

        mBinding.recyclerDate.setLayoutManager(new ScrollGridLayoutManager(context, 7));
        tripDateAdapter = new TripDateAdapter(new ArrayList<>());
        mBinding.recyclerDate.setAdapter(tripDateAdapter);

        tripDateAdapter.setOnItemClickListener((adapter, view, position) -> {
            new TripTimeDialog(context).show();
        });

    }

    public void initData() {
        mBinding.tvYear.setText(DateUtil.format(new Date(), DateUtil.DATE_YEAR));
        mSubscription.add(Observable.just(DateUtil.getNowMonthDataList())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(new Function<List<Date>, List<Date>>() {
                    @Override
                    public List<Date> apply(List<Date> dates) throws Exception {
                        List<Date> dateList = new ArrayList<>();
                        Calendar now = Calendar.getInstance();
                        int nowYear = now.get(Calendar.YEAR);//获取年份
                        int nowMonth = now.get(Calendar.MONTH);//获取月份
                        for (int i = 0; i < DateUtil.getWeekOfDateIndex(DateUtil.getSupportBeginDayofMonth(nowYear, nowMonth)); i++) {
                            dateList.add(null);
                        }
                        dateList.addAll(dates);
                        return dateList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Date>>() {
                    @Override
                    public void accept(List<Date> dates) throws Exception {
                        tripDateAdapter.replaceData(dates);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    public void destroyView() {
        if (mSubscription != null) {
            mSubscription.dispose();
        }
    }


}
