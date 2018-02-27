package com.cdkj.myxb.weight.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.views.ScrollGridLayoutManager;
import com.cdkj.myxb.R;
import com.cdkj.myxb.adapters.TripDateAdapter;
import com.cdkj.myxb.databinding.LayoutShopperTripDateBinding;
import com.cdkj.myxb.models.MouthAppointmentModel;

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

    private OnClickListener itemClickListener;

    private Date mStartDate = new Date();
    private Date mNowDate;//当前月份


    public void setItemClickListener(OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TripDateView(Context context) {
        this(context, null);
    }

    public TripDateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TripDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_shopper_trip_date, this, true);


        ObjectAnimator animStart = ObjectAnimator.ofFloat(mBinding.imgPrevious, "rotation", 0f, 180f);
        animStart.start();


        mSubscription = new CompositeDisposable();

        initListener();

        initDateAdapter(context);
    }

    private void initListener() {

        mBinding.imgNext.setOnClickListener(view -> {
            mBinding.imgPrevious.setVisibility(VISIBLE);
            setMouthChange(1);

            if (itemClickListener != null) {
                itemClickListener.OnNextClick(mNowDate);
            }

        });

        mBinding.imgPrevious.setOnClickListener(view -> {
            setMouthChange(-1);
            if (!DateUtil.isNewer(mNowDate, mStartDate)) {
                mBinding.imgPrevious.setVisibility(INVISIBLE);
            }
            if (itemClickListener != null) {
                itemClickListener.OnPreviousClick(mNowDate);
            }

        });


    }

    /**
     * 改变当前日期
     *
     * @param change
     */
    private void setMouthChange(int change) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mNowDate);
        calendar.add(Calendar.MONTH, change);
        mNowDate = calendar.getTime();
        setDate(mNowDate, null);
    }

    private void initDateAdapter(Context context) {
        mBinding.recyclerDate.setLayoutManager(new ScrollGridLayoutManager(context, 7));
        tripDateAdapter = new TripDateAdapter(new ArrayList<>());
        mBinding.recyclerDate.setAdapter(tripDateAdapter);

        tripDateAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (itemClickListener != null) {
                itemClickListener.OnItmeClick(tripDateAdapter.getData(), position);
            }
        });
    }


    public void setmStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    /**
     * @param date
     */
    public void setDate(Date date, List<MouthAppointmentModel> compareDates) {
        if (date == null) return;

        mNowDate = date;
        mBinding.tvYear.setText(DateUtil.format(date, DateUtil.DATE_YEAR));
        mSubscription.add(Observable.just(DateUtil.getMonthDataList(date))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(new Function<List<Date>, List<MouthAppointmentModel>>() {
                    @Override
                    public List<MouthAppointmentModel> apply(List<Date> dates) throws Exception {
                        List<MouthAppointmentModel> dateList = new ArrayList<>();
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
                        int nowYear = now.get(Calendar.YEAR);//获取年份
                        int nowMonth = now.get(Calendar.MONTH);//获取月份

                        for (int i = 0; i < DateUtil.getWeekOfDateIndex(DateUtil.getSupportBeginDayofMonth(nowYear, nowMonth)); i++) {
                            dateList.add(null);
                        }

                        for (Date date1 : dates) {
                            MouthAppointmentModel appointmentDateViewModel = new MouthAppointmentModel();
                            appointmentDateViewModel.setDate(date1);
                            dateList.add(appointmentDateViewModel);
                        }

                        return dateList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MouthAppointmentModel>>() {
                    @Override
                    public void accept(List<MouthAppointmentModel> datas) throws Exception {

                        if (compareDates == null || compareDates.isEmpty()) {
                            tripDateAdapter.replaceData(datas);
                            return;
                        }
                        tripDateAdapter.replaceData(filterCompareData(datas, compareDates));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.E(throwable.toString() + "ddd");
                    }
                }));
    }

    /**
     * 过滤对比数据 如果日期相同这说明有行程安排
     *
     * @param datas
     * @param das
     * @return
     */
    @NonNull
    private List<MouthAppointmentModel> filterCompareData(List<MouthAppointmentModel> datas, List<MouthAppointmentModel> das) {
        List<MouthAppointmentModel> newDatas = new ArrayList<>();

        for (MouthAppointmentModel mouthAppointmentModel : datas) {

            if (mouthAppointmentModel != null) {

                List<MouthAppointmentModel> appointmentModels2 = new ArrayList<>();

                for (MouthAppointmentModel datetimeModel : das) {

                    if (DateUtil.inSameDay(mouthAppointmentModel.getDate(), new Date(datetimeModel.getStartDatetime()))) {
                        mouthAppointmentModel.setSame(true);
                        appointmentModels2.add(datetimeModel);
                    }
                }

                mouthAppointmentModel.setOneDayDateTime(appointmentModels2); //设置同一天的行程数据
            }

            newDatas.add(mouthAppointmentModel);
        }
        return newDatas;
    }

    public void destroyView() {
        if (mSubscription != null) {
            mSubscription.dispose();
        }
    }

    public interface OnClickListener {
        void OnItmeClick(List<MouthAppointmentModel> dateModels, int position);

        void OnPreviousClick(Date pDate); //上一步点击

        void OnNextClick(Date nDate);     //下一步点击
    }

}
