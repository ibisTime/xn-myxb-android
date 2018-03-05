package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.appmanager.RouteHelper;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.LoginInterface;
import com.cdkj.baselibrary.interfaces.LoginPresenter;
import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.MainActivity;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityLoginBinding;
import com.cdkj.myxb.models.LoginTypeModel;
import com.cdkj.myxb.models.UserModel;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 登录
 * Created by cdkj on 2017/8/8.
 */
@Route(path = RouteHelper.APPLOGIN)
public class LoginActivity extends AbsBaseLoadActivity implements LoginInterface {

    private LoginPresenter mPresenter;
    private ActivityLoginBinding mBinding;
    private boolean canOpenMain;

    private String mLoginKind;//用户的登录类型

    private OptionsPickerView mLoginTypePicker;//登录类型选择

    private List<LoginTypeModel> mLoginTypes;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, boolean canOpenMain) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("canOpenMain", canOpenMain);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_login, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {
        super.topTitleViewRightClick();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("登录");

        mPresenter = new LoginPresenter(this);

        if (getIntent() != null) {
            canOpenMain = getIntent().getBooleanExtra("canOpenMain", false);
        }
        initPickerView();
        initListener();
    }

    private void initListener() {
        //登录
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(mBinding.editUsername.getText().toString(), mBinding.editUserpass.getText().toString(), mLoginKind, LoginActivity.this);
            }
        });
        //注册
        mBinding.tvStartRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RegisterActivity.open(LoginActivity.this);
            }
        });

        //找回密码
        mBinding.tvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPwdActivity.open(LoginActivity.this, "");
            }
        });

        //登录类型
        mBinding.linLoginType.setOnClickListener(view -> {
            if (mLoginTypePicker == null) {
                initPickerView();
            }
            mLoginTypePicker.setPicker(mLoginTypes);
            mLoginTypePicker.show();
        });

    }


    public void initPickerView() {

        initPickerData();

        mLoginTypePicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {

            LoginTypeModel loginTypeModel = mLoginTypes.get(options1);

            mLoginKind = loginTypeModel.getType();

            mBinding.tvLogintype.setText(loginTypeModel.getTypeString());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();


    }

    private void initPickerData() {

        mLoginTypes = new ArrayList<>();

        LoginTypeModel loginTypeModel = new LoginTypeModel();
        loginTypeModel.setType(UserHelper.C);
        loginTypeModel.setTypeString(getString(R.string.mry));

        mLoginTypes.add(loginTypeModel);

        LoginTypeModel loginTypeModel2 = new LoginTypeModel();
        loginTypeModel2.setType(UserHelper.L);
        loginTypeModel2.setTypeString(getString(R.string.teacher));

        mLoginTypes.add(loginTypeModel2);

        LoginTypeModel loginTypeModel3 = new LoginTypeModel();
        loginTypeModel3.setType(UserHelper.S);
        loginTypeModel3.setTypeString(getString(R.string.experts));

        mLoginTypes.add(loginTypeModel3);

        LoginTypeModel loginTypeMode4 = new LoginTypeModel();
        loginTypeMode4.setType(UserHelper.T);
        loginTypeMode4.setTypeString(getString(R.string.shopper));

        mLoginTypes.add(loginTypeMode4);
    }

    @Override
    public void LoginSuccess(UserLoginModel user, String msg) {
        SPUtilHelpr.saveUserId(user.getUserId());
        SPUtilHelpr.saveUserToken(user.getToken());
        SPUtilHelpr.saveUserPhoneNum(mBinding.editUsername.getText().toString());
        getUserInfoRequest();
    }


    /**
     * 获取用户信息
     */
    public void getUserInfoRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("805121", StringUtils.getJsonToString(map));

        addCall(call);



        call.enqueue(new BaseResponseModelCallBack<UserModel>(this) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {
                saveUserInfo(data);
                startNext();
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
    /**
     * 保存用户相关信息
     *
     * @param data
     */
    private void saveUserInfo(UserModel data) {
        SPUtilHelpr.saveisTradepwdFlag(data.isTradepwdFlag());
        SPUtilHelpr.saveUserPhoneNum(data.getMobile());
        SPUtilHelpr.saveUserName(data.getRealName());
        SPUtilHelpr.saveUserNickName(data.getNickname());
        SPUtilHelpr.saveUserPhoto(data.getPhoto());
        SPUtilHelpr.saveUserLevel(data.getLevel() + "");
        SPUtilHelpr.saveUserType(data.getKind());
    }


    @Override
    public void LoginFailed(String code, String msg) {
        disMissLoading();
        UITipDialog.showFall(LoginActivity.this, msg);
    }

    @Override
    public void StartLogin() {
        showLoadingDialog();
    }

    @Override
    public void EndLogin() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
    }

    @Override
    protected boolean canFinish() {
        return false;
    }

    @Override
    public void topTitleViewleftClick() {
        backClick();
    }

    @Override
    public void onBackPressed() {
        backClick();
    }

    private void backClick() {
        if (canOpenMain) {
            MainActivity.open(this);
            finish();
        } else {
            super.onBackPressed();
        }
    }


    /**
     */
    private void startNext() {
        if (canOpenMain) {
            MainActivity.open(this);
        } else {
        }
        finish();
    }

}
