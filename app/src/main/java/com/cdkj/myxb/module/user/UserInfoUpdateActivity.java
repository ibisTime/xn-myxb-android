package com.cdkj.myxb.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ActivityUserInfoUpdateBinding;
import com.cdkj.myxb.models.ClassStyleModel;
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
    private List<String> mSelectStyleId;

    private UpdateUserInfo mUserInfo;
    private final String SEP1 = ",";

    /**
     * @param context
     * @param updateUserInfo 用户的信息
     */
    public static void open(Context context, UpdateUserInfo updateUserInfo) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserInfoUpdateActivity.class);
        intent.putExtra("updateUserInfo", updateUserInfo);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_info_update, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void topTitleViewRightClick() {

        if (checkInput()) return;

        if (mUserInfo != null && isWaitePass(mUserInfo.getStatus())) {
            showSureDialog("您的资料正在审核中,无法进行修改", view -> {

            });

            return;
        }
        updateRequest();

    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mBinding.editName.getText().toString())) {

            UITipDialog.showFall(this, "请填写姓名");

            return true;
        }
        if (TextUtils.isEmpty(mBinding.editSpeciality.getText().toString())) {

            UITipDialog.showFall(this, "请填写擅长领域");

            return true;
        }
        if (TextUtils.isEmpty(mBinding.tvStyle.getText().toString()) || mSelectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请选择授课风格");

            return true;
        }
        if (TextUtils.isEmpty(mBinding.editSlogan.getText().toString()) || mSelectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请填写个性签名");

            return true;
        }

        if (TextUtils.isEmpty(mBinding.editUserInfo.getText().toString()) || mSelectStyleId.isEmpty()) {

            UITipDialog.showFall(this, "请填写个人简介");

            return true;
        }
        return false;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {


        if (getIntent() != null) {
            mUserInfo = getIntent().getParcelableExtra("updateUserInfo");
        }

        mBaseBinding.titleView.setMidTitle("我的资料");
        mBaseBinding.titleView.setRightTitle("保存");

        mSelectStyle = new ArrayList<>();
        mSelectStyleId = new ArrayList<>();
        mStyles = new ArrayList<>();

        initPickerView();

        mBinding.linStyle.setOnClickListener(view -> {
            if (mStyles.isEmpty()) {
                getClassStyle(true);
                return;
            }
            mStylePicker.setPicker(mStyles);
            mStylePicker.show();
        });

        setShowData(mUserInfo);

        getUpdateUserInfo();

    }

    /**
     * 更新请求
     */
    public void updateRequest() {


        Map<String, String> map = new HashMap<>();
//
        map.put("realName", mBinding.editName.getText().toString());
        map.put("speciality", mBinding.editSpeciality.getText().toString());
        map.put("description", mBinding.editUserInfo.getText().toString());
        map.put("style", StringUtils.listToString(mSelectStyleId, SEP1));
        map.put("slogan", mBinding.editSlogan.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("805530", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {
            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getCode())) {

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

                setStyleData(data);

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

    /**
     * 设置显示的滚轮数据
     *
     * @param data
     */
    private void setStyleData(List<ClassStyleModel> data) {
        ClassStyleModel classStyleModel = new ClassStyleModel();
        classStyleModel.setClear(true);
        classStyleModel.setDvalue("清空选择");            //第一项是清空操作

        mStyles.clear();
        mStyles.add(classStyleModel);
        mStyles.addAll(data);
    }

    public void initPickerView() {


        mStylePicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {

            if (mStyles == null || mStyles.size() < options1) {
                return;
            }

            ClassStyleModel classStyleModel = mStyles.get(options1);

            if (classStyleModel == null) return;

            if (classStyleModel.isClear()) { //如果用户选择的是清除操作
                mSelectStyle.clear();
                mSelectStyleId.clear();
                mBinding.tvStyle.setText("");
                return;
            }

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
        mBinding.tvStyle.setText("");
        StringBuffer selectStyleName = new StringBuffer();

        for (ClassStyleModel styleModel : mSelectStyle) {
            selectStyleName.append(styleModel.getDvalue() + " ");
            mSelectStyleId.add(styleModel.getId() + SEP1);
        }

        mBinding.tvStyle.setText(selectStyleName.toString());
    }

    /**
     * 通过获取的样式ide设置文本
     */
    private void setStyleTextById() {
        mBinding.tvStyle.setText("");
        StringBuffer selectStyleName = new StringBuffer();

        for (String id : mSelectStyleId) {

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
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void setShowData(UpdateUserInfo data) {

        if (data == null) return;

        mSelectStyleId.clear();
        mSelectStyleId.addAll(StringUtils.splitAsList(data.getStyle(), SEP1));

        getClassStyle(false);

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
