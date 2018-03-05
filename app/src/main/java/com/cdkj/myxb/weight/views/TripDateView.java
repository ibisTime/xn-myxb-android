package com.cdkj.myxb.weight.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import java.util.Collections;
import java.util.Comparator;
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
    private List<MouthAppointmentModel> mCopareDatas;
    private Date endData;
    private Date mCompareEndData; //比对开始时间
    private Date mCompareStartData;//比对结束


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
        mCopareDatas = new ArrayList<>();
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
        setDate(mNowDate);
    }

    /**
     * 初始化适配器
     *
     * @param context
     */
    private void initDateAdapter(Context context) {
        mBinding.recyclerDate.setLayoutManager(new ScrollGridLayoutManager(context, 7));
        tripDateAdapter = new TripDateAdapter(new ArrayList<>());
        mBinding.recyclerDate.setAdapter(tripDateAdapter);

        tripDateAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (itemClickListener != null) {

                if (tripDateAdapter.isHaveSameDay(tripDateAdapter.getItem(position))) {  //有预约时才可以点击
                    itemClickListener.OnItmeClick(getSameData(tripDateAdapter.getItem(position)), position);
                }

            }
        });
    }


    /**
     * 设置开始时间
     *
     * @param mStartDate
     */
    public void setmStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        if (date == null) return;

        mNowDate = date;
        mBinding.tvYear.setText(DateUtil.format(date, DateUtil.DATE_YEAR));
        mSubscription.add(Observable.just(DateUtil.getMonthDataList(date))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(new Function<List<Date>, List<Date>>() {
                    @Override
                    public List<Date> apply(List<Date> dates) throws Exception {
                        List<Date> dateList = new ArrayList<>();
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
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
                    public void accept(List<Date> datas) throws Exception {
                        tripDateAdapter.replaceData(datas);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.E(throwable.toString() + "ddd");
                    }
                }));
    }


    /**
     * 设置比对数据 找出开始时间和结束时间 获取之前的data 传入比对
     *
     * @param datas
     */
    public void setCompareData(List<MouthAppointmentModel> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }

        mCopareDatas.clear();
        mCopareDatas.addAll(datas);

        mCompareStartData = getCompareStarDate(datas);

        mCompareEndData = getCompreEndDate(datas);

        if (mCompareStartData == null || mCompareEndData == null) {
            return;
        }

        if (tripDateAdapter == null) {
            return;
        }

        tripDateAdapter.setCompareData(DateUtil.getDatesBetweenTwoDate(mCompareStartData, mCompareEndData));
    }


    /**
     * 获取结束时间
     *
     * @param modelList
     * @return
     */
    private Date getCompreEndDate(List<MouthAppointmentModel> modelList) {

        List<MouthAppointmentModel> datas2 = new ArrayList<>();

        datas2.addAll(modelList);

        Collections.sort(datas2, new Comparator() {       //按照getEndDatetime 顺序排序
            @Override
            public int compare(Object o1, Object o2) {
                MouthAppointmentModel stu1 = (MouthAppointmentModel) o1;
                MouthAppointmentModel stu2 = (MouthAppointmentModel) o2;

                if (stu1 == null || stu2 == null || TextUtils.isEmpty(stu1.getEndDatetime()) || TextUtils.isEmpty(stu2.getEndDatetime())) {
                    return 1;
                }

                if (DateUtil.isNewer(new Date(stu1.getEndDatetime()), new Date(stu2.getEndDatetime()))) {
                    return 1;
                } else if (DateUtil.isNewer2(new Date(stu1.getEndDatetime()), new Date(stu2.getEndDatetime()))) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        if (datas2.isEmpty()) return null;

        MouthAppointmentModel appointmentModel = datas2.get(datas2.size() - 1);

        if (appointmentModel == null || TextUtils.isEmpty(appointmentModel.getEndDatetime()))
            return null;

        return new Date(appointmentModel.getEndDatetime());
    }


    /**
     * 根据date获取相同的日期 用于dialog显示
     *
     * @return
     */
    private List<MouthAppointmentModel> getSameData(Date date) {

        if (date == null) return null;

        List<MouthAppointmentModel> filterData = new ArrayList<>();

        for (MouthAppointmentModel mouthAppointmentModel : mCopareDatas) {

            if (mouthAppointmentModel == null || TextUtils.isEmpty(mouthAppointmentModel.getStartDatetime())) {
                continue;
            }

            if (DateUtil.isEffectiveDate(mCompareStartData, new Date(mouthAppointmentModel.getStartDatetime()), mCompareEndData)) {
                filterData.add(mouthAppointmentModel);
            }

        }
        return filterData;

    }


    /**
     * 获取开始时间
     *
     * @param modelList
     * @return
     */
    private Date getCompareStarDate(List<MouthAppointmentModel> modelList) {

        List<MouthAppointmentModel> datas1 = new ArrayList<>();

        datas1.addAll(modelList);

        Collections.sort(datas1, new Comparator() {        //按照getStartDatetime 顺序排序
            @Override
            public int compare(Object o1, Object o2) {
                MouthAppointmentModel stu1 = (MouthAppointmentModel) o1;
                MouthAppointmentModel stu2 = (MouthAppointmentModel) o2;

                if (stu1 == null || stu2 == null || TextUtils.isEmpty(stu1.getStartDatetime()) || TextUtils.isEmpty(stu2.getStartDatetime())) {
                    return 1;
                }

                if (DateUtil.isNewer(new Date(stu1.getStartDatetime()), new Date(stu2.getStartDatetime()))) {
                    return 1;
                } else if (DateUtil.isNewer2(new Date(stu1.getStartDatetime()), new Date(stu2.getStartDatetime()))) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        if (datas1.isEmpty()) return null;

        MouthAppointmentModel appointmentModel = datas1.get(0);

        if (appointmentModel == null || TextUtils.isEmpty(appointmentModel.getStartDatetime()))
            return null;

        return new Date(appointmentModel.getStartDatetime());
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
