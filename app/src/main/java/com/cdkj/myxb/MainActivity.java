package com.cdkj.myxb;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.IntDef;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.eventmodels.EventFinishAll;
import com.cdkj.myxb.databinding.ActivityMainBinding;
import com.cdkj.myxb.module.maintab.AdviceFragment;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.HelpCenterFragment;
import com.cdkj.myxb.module.maintab.InvitationFriendFragment;
import com.cdkj.myxb.module.maintab.MyFragment;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class MainActivity extends AbsBaseLoadActivity {


    public static final int SHOWFIRST = 0;//显示首页
    public static final int SHOWHELP = 1;//显示帮助中心
    public static final int SHOWINVITATION = 2;//显示邀请好友
    public static final int SHOWADVICE = 3;//显示平台建议
    public static final int SHOWMY = 4;//显示我的界面


    @IntDef({SHOWFIRST, SHOWHELP, SHOWINVITATION, SHOWADVICE, SHOWMY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface showType {
    }

    private
    @MainActivity.showType
    int mShowIndex = SHOWFIRST;//显示相应页面 默认首页

    private ActivityMainBinding mBinding;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setShowTitle(false);
        mBinding.layoutTab.radioMainTab1.setOnClickListener(view -> {
            ImageSelectActivity.launch(this, 0, false);
        });
        initViewPager();
        initListener();
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {

        mBinding.layoutTab.radioMainTab1.setOnClickListener(view -> {
            setShowIndex(SHOWFIRST);
        });
        mBinding.layoutTab.radioMainTab2.setOnClickListener(view -> {
            setShowIndex(SHOWHELP);
        });
        mBinding.layoutTab.llayoutTab3.setOnClickListener(view -> {
            setShowIndex(SHOWINVITATION);

        });
        mBinding.layoutTab.radioMainTab4.setOnClickListener(view -> {
            setShowIndex(SHOWADVICE);
        });
        mBinding.layoutTab.radioMainTab5.setOnClickListener(view -> {
            setShowIndex(SHOWMY);
        });

    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        //设置fragment数据
        ArrayList fragments = new ArrayList<>();

        fragments.add(FirstPageFragment.getInstanse());//首页
        fragments.add(HelpCenterFragment.getInstanse());//帮助中心
        fragments.add(InvitationFriendFragment.getInstanse());//邀请好友
        fragments.add(AdviceFragment.getInstanse());//建议评价
        fragments.add(MyFragment.getInstanse());//我的

        mBinding.pagerMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.pagerMain.setOffscreenPageLimit(fragments.size());
    }

    /**
     * 设置要显示的界面
     *
     * @param index
     */
    private void setShowIndex(@showType int index) {
        mShowIndex = index;
        setShowButIndex();
        mBinding.pagerMain.setCurrentItem(index, false);
    }


    /**
     * 设置要显示的界面按钮
     *
     * @param
     */
    private void setShowButIndex() {
        mBinding.layoutTab.radioMainTab1.setChecked(false);
        mBinding.layoutTab.radioMainTab2.setChecked(false);
        mBinding.layoutTab.radioMainTab5.setChecked(false);
        mBinding.layoutTab.radioMainTab4.setChecked(false);
        mBinding.layoutTab.tvInvitation.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        switch (mShowIndex) {
            case SHOWFIRST:
                mBinding.layoutTab.radioMainTab1.setChecked(true);
                break;
            case SHOWHELP:
                mBinding.layoutTab.radioMainTab2.setChecked(true);
                break;
            case SHOWINVITATION:
                mBinding.layoutTab.tvInvitation.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                break;
            case SHOWADVICE:
                mBinding.layoutTab.radioMainTab4.setChecked(true);
                break;
            case SHOWMY:
                mBinding.layoutTab.radioMainTab5.setChecked(true);
                break;
        }

    }


    @Override
    public void onBackPressed() {
        showDoubleWarnListen(getString(R.string.sure_app_exit), view -> {
            EventBus.getDefault().post(new EventFinishAll()); //结束所有界面
            finish();
        });
    }


}
