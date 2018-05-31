package com.cdkj.myxb;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.eventmodels.EventFinishAll;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.databinding.ActivityMainBinding;
import com.cdkj.myxb.models.SystemParameter;
import com.cdkj.myxb.module.api.MyApiServer;
import com.cdkj.myxb.module.maintab.FirstPageFragment;
import com.cdkj.myxb.module.maintab.HelpCenterFragment2;
import com.cdkj.myxb.module.maintab.InvitationFriendFragment;
import com.cdkj.myxb.module.maintab.MyFragment;
import com.cdkj.myxb.module.maintab.SchoolFragment;
import com.cdkj.myxb.module.user.LoginActivity;
import com.cdkj.myxb.module.user.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AbsBaseLoadActivity {

    public static final int SHOWFIRST = 0;//显示首页
    public static final int SHOWHELP = 1;//显示帮助中心
    public static final int SHOWINVITATION = 2;//显示邀请好友
    public static final int SHOWADVICE = 3;//显示平台建议
    public static final int SHOWMY = 4;//显示我的界面

    public static final String TITLE = "title";

    private List<SystemParameter> mParameters;

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
        initListener();

        getSystemParameterRequest();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showTabStatus();
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }


    public void getSystemParameterRequest() {

        Map<String, String> map = RetrofitUtils.getRequestMap();
        map.put("type", "1");

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getSystemParameter("805806", StringUtils.getJsonToString(map));

        addCall(call);


        call.enqueue(new BaseResponseListCallBack<SystemParameter>(this) {

            @Override
            protected void onSuccess(List<SystemParameter> data, String SucMessage) {
                if (data != null){
                    mParameters = data;

                    initViewPager();
                    setShowIndex(SHOWFIRST);
                }

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {

            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {

        mBinding.layoutTab.llTab1.setOnClickListener(view -> {
            setShowIndex(SHOWFIRST);
        });
        mBinding.layoutTab.llTab2.setOnClickListener(view -> {
            setShowIndex(SHOWHELP);
        });
        mBinding.layoutTab.llTab3.setOnClickListener(view -> {
            if (!SPUtilHelpr.isLoginNoStart()) {
                setShowButIndex();
                LoginActivity.open(this, false);
                return;
            }
            setShowIndex(SHOWINVITATION);

        });
        mBinding.layoutTab.llTab4.setOnClickListener(view -> {
            setShowIndex(SHOWADVICE);
        });
        mBinding.layoutTab.llTab5.setOnClickListener(view -> {

            if (!SPUtilHelpr.isLoginNoStart()) {
                setShowButIndex();
                LoginActivity.open(this, false);
                return;
            }
            setShowIndex(SHOWMY);
        });

    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        //设置fragment数据
        ArrayList fragments = new ArrayList<>();

        fragments.add(FirstPageFragment.getInstance());//首页
        fragments.add(SchoolFragment.getInstance(mParameters.get(1).getName()));//建议评价
        fragments.add(InvitationFriendFragment.getInstance());//邀请好友
        fragments.add(HelpCenterFragment2.getInstance());//帮助中心
        fragments.add(MyFragment.getInstance());//我的

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
        if (mParameters == null || mParameters.size() != 5){
            return;
        }

        setViewDark();

        switch (mShowIndex) {
            case SHOWFIRST:
                mBinding.layoutTab.tvTab1.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                ImgUtils.loadMainTabImg(this, mParameters.get(0).getUrl(), mBinding.layoutTab.ivTab1);
                break;
            case SHOWHELP:
                mBinding.layoutTab.tvTab2.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                ImgUtils.loadMainTabImg(this, mParameters.get(1).getUrl(), mBinding.layoutTab.ivTab2);
                break;
            case SHOWINVITATION:
                mBinding.layoutTab.tvTab3.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                ImgUtils.loadMainTabImg(this, mParameters.get(2).getUrl(), mBinding.layoutTab.ivTab3);
                break;
            case SHOWADVICE:
                mBinding.layoutTab.tvTab4.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                ImgUtils.loadMainTabImg(this, mParameters.get(3).getUrl(), mBinding.layoutTab.ivTab4);
                break;
            case SHOWMY:
                mBinding.layoutTab.tvTab5.setTextColor(ContextCompat.getColor(this, R.color.app_txt_red));
                ImgUtils.loadMainTabImg(this, mParameters.get(4).getUrl(), mBinding.layoutTab.ivTab5);
                break;
        }


    }

    private void setViewDark() {
        mBinding.layoutTab.tvTab1.setText(mParameters.get(0).getName());
        mBinding.layoutTab.tvTab2.setText(mParameters.get(1).getName());
        mBinding.layoutTab.tvTab3.setText(mParameters.get(2).getName());
        mBinding.layoutTab.tvTab4.setText(mParameters.get(3).getName());
        mBinding.layoutTab.tvTab5.setText(mParameters.get(4).getName());

        mBinding.layoutTab.tvTab1.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        mBinding.layoutTab.tvTab2.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        mBinding.layoutTab.tvTab3.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        mBinding.layoutTab.tvTab4.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));
        mBinding.layoutTab.tvTab5.setTextColor(ContextCompat.getColor(this, R.color.app_txt_black));

        ImgUtils.loadMainTabImg(this, mParameters.get(0).getPic(), mBinding.layoutTab.ivTab1);
        ImgUtils.loadMainTabImg(this, mParameters.get(1).getPic(), mBinding.layoutTab.ivTab2);
        ImgUtils.loadMainTabImg(this, mParameters.get(2).getPic(), mBinding.layoutTab.ivTab3);
        ImgUtils.loadMainTabImg(this, mParameters.get(3).getPic(), mBinding.layoutTab.ivTab4);
        ImgUtils.loadMainTabImg(this, mParameters.get(4).getPic(), mBinding.layoutTab.ivTab5);
    }


    @Override
    public void onBackPressed() {
        showDoubleWarnListen(getString(R.string.sure_app_exit), view -> {
            EventBus.getDefault().post(new EventFinishAll()); //结束所有界面
            finish();
        });
    }

    @Subscribe
    public void showTabStatus(){
        if (TextUtils.equals(SPUtilHelpr.getUserType(), UserHelper.S)){
            mBinding.layoutTab.llTab3.setVisibility(View.GONE);
        }else {
            mBinding.layoutTab.llTab3.setVisibility(View.VISIBLE);
        }
    }


}
