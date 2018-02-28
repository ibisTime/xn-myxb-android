package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserInfoUpdateBinding;
import com.cdkj.myxb.models.ClassStyleModel;
import com.cdkj.myxb.models.LoginTypeModel;
import com.cdkj.myxb.models.UpdateUserInfo;
import com.cdkj.myxb.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 用户资料修改
 * Created by cdkj on 2018/2/27.
 */

public class UserInfoUpdateActivity extends AbsBaseLoadActivity {

    private ActivityUserInfoUpdateBinding mBinding;

    private OptionsPickerView mStylePicker;//授课风格选择
    private List<ClassStyleModel> mStyles;
    private List<ClassStyleModel> mSelectStyle;//用户选择的授课风格
    private List<String> selectStyleId;

    private UpdateUserInfo mUserInfo;
    private final String SEP1 = ",";
    ;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserInfoUpdateActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_info_update, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {
        if (mUserInfo != null && isWaitePass(mUserInfo.getStatus())) {
            showSureDialog("您的资料正在审核中,无法进行修改", view -> {

            });

            return;
        }
        updateRequest();

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("我的资料");
        mBaseBinding.titleView.setRightTitle("保存");

        mSelectStyle = new ArrayList<>();
        selectStyleId = new ArrayList<>();
        initPickerView();

        mBinding.linStyle.setOnClickListener(view -> {
            if (mStyles == null) {
                getClassStyle(true);
                return;
            }
            mStylePicker.setPicker(mStyles);
            mStylePicker.show();
        });

        getUpdateUserInfo();

    }

    /**
     * 更新请求
     */
    public void updateRequest() {


        if (TextUtils.isEmpty(mBinding.editName.getText().toString())) {

            UITipDialog.showFall(this, "请填写姓名");

            return;
        }
        if (TextUtils.isEmpty(mBinding.editSpeciality.getText().toString())) {

            UITipDialog.showFall(this, "请填写擅长领域");

            return;
        }
        if (TextUtils.isEmpty(mBinding.tvStyle.getText().toString()) || selectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请选择授课风格");

            return;
        }
        if (TextUtils.isEmpty(mBinding.editSlogan.getText().toString()) || selectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请填写广告语");

            return;
        }

        if (TextUtils.isEmpty(mBinding.editUserInfo.getText().toString()) || selectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请填写个人简介");

            return;
        }


        Map<String, String> map = new HashMap<>();
//
        map.put("realName", mBinding.editName.getText().toString());
        map.put("speciality", mBinding.editSpeciality.getText().toString());
        map.put("description", mBinding.editUserInfo.getText().toString());
        map.put("style", StringUtils.listToString(selectStyleId, SEP1));
        map.put("slogan", mBinding.editSlogan.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805530", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {

                    if (mUserInfo != null) {
                        mUserInfo.setStatus("5");
                    }
                    mBaseBinding.titleView.setMidTitle("我的资料(审核中)");
                    UITipDialog.showSuccess(UserInfoUpdateActivity.this, "信息修改成功,正在审核中");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


    /**
     * 获取授课风格
     *
     * @param isShowPicker 请求成功后是否显示picker
     */
    public void getClassStyle(boolean isShowPicker) {

        Map map = RetrofitUtils.getRequestMap();

        map.put("parentKey", "style");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getClassStyleList("805906", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<ClassStyleModel>(this) {

            @Override
            protected void onSuccess(List<ClassStyleModel> data, String SucMessage) {
                mStyles = data;
                if (isShowPicker) {
                    mStylePicker.setPicker(mStyles);
                    mStylePicker.show();
                } else {
                    setStyleTextById();
                }
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void initPickerView() {


        mStylePicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {

            if (mStyles == null || mStyles.size() < options1) {
                return;
            }

            ClassStyleModel classStyleModel = mStyles.get(options1);

            if (mSelectStyle.contains(classStyleModel)) {
                return;
            }
            mSelectStyle.add(classStyleModel);

            setStyleText();

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();
    }

    /**
     * 设置风格文本
     */
    private void setStyleText() {
        StringBuffer selectStyleName = new StringBuffer();

        for (ClassStyleModel styleModel : mSelectStyle) {
            selectStyleName.append(styleModel.getDvalue() + " ");
            selectStyleId.add(styleModel.getId() + SEP1);
        }

        mBinding.tvStyle.setText(selectStyleName.toString());
    }

    /**
     * 通过获取的样式ide设置文本
     */
    private void setStyleTextById() {
        StringBuffer selectStyleName = new StringBuffer();

        for (String id : selectStyleId) {

            for (ClassStyleModel classStyleModel : mStyles) {

                if (TextUtils.equals(id, String.valueOf(classStyleModel.getId()))) { //比对选择的style Id
                    selectStyleName.append(classStyleModel.getDvalue() + " ");
                }
            }
        }

        mBinding.tvStyle.setText(selectStyleName.toString());
    }


    /**
     * 805537
     */
    public void getUpdateUserInfo() {

        Map map = RetrofitUtils.getRequestMap();

        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUpdateUserInfo("805537", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UpdateUserInfo>(this) {
            @Override
            protected void onSuccess(UpdateUserInfo data, String SucMessage) {
                mUserInfo = data;
                setShowData(data);
                getClassStyle(false);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void setShowData(UpdateUserInfo data) {

        if (data == null) return;

        selectStyleId.clear();
        selectStyleId.addAll(StringUtils.splitAsList(data.getStyle(), SEP1));


        mBinding.editName.setText(data.getRealName());
        mBinding.editSlogan.setText(data.getSlogan());
        mBinding.editSpeciality.setText(data.getSpeciality());
        mBinding.editUserInfo.setText(data.getDescription());


        if (isWaitePass(data.getStatus())) {
            mBaseBinding.titleView.setMidTitle("我的资料(审核中)");
        }

    }

    /**
     * 是否待审核状态      /*TO_APPROVE("1", "待审核"), APPROVE_NO("2", "审核不通过"),
     */

    private boolean isWaitePass(String state) {
        return TextUtils.equals("1", state);
    }


}
