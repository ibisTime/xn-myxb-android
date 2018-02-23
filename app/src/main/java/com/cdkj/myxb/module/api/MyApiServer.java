package com.cdkj.myxb.module.api;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.myxb.models.IntegraProductDetailsModel;
import com.cdkj.myxb.models.AccountDetailsModel;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.models.IntegralListModel;
import com.cdkj.myxb.models.IntegralModel;
import com.cdkj.myxb.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 李先俊 on 2018/2/22.
 */

public interface MyApiServer {

    /**
     * 获取积分流水
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<IntegralListModel>>> getIntegralList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取积分
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<AccountDetailsModel>> getAccountDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 获取账户列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<AccountListModel>> getAccountList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取积分商品列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<IntegralModel>>> getIntegralProductList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取积分商品详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<IntegraProductDetailsModel>> getIntegralProduct(@Field("code") String code, @Field("json") String json);


    /**
     * 获取用户信息详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UserModel>> getUserInfoDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 添加收货地址
     * AddAddress
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CodeModel>> AddAddress(@Field("code") String code, @Field("json") String json);

    /**
     * 获取地址列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<AddressModel>> getAddress(@Field("code") String code, @Field("json") String json);

    /**
     * 设置默认地址
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<Boolean>> setDefultAddress(@Field("code") String code, @Field("json") String json);


}
